package com.example.qlsv;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;

import com.example.qlsv.adapter.CertificatesAdapter;
import com.example.qlsv.adapter.LoginHistoryAdapter;
import com.example.qlsv.adapter.UsersAdapter;
import com.example.qlsv.contract.AddCertificatesContract;
import com.example.qlsv.contract.ListCertificatesContract;
import com.example.qlsv.contract.UserManagementContract;
import com.example.qlsv.databinding.ActivityListCertificatesBinding;
import com.example.qlsv.databinding.ActivityUserManagementBinding;
import com.example.qlsv.dto.Certificates;
import com.example.qlsv.dto.User;
import com.example.qlsv.presenter.AddCertificatePresenter;
import com.example.qlsv.presenter.ListCertificatesPresenter;
import com.example.qlsv.presenter.LoginHistoryPresenter;
import com.opencsv.CSVWriter;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Locale;

import javax.annotation.Nullable;

public class ListCertificatesActivity extends AppCompatActivity implements ListCertificatesContract.View, AddCertificatesContract.View {
    private ActivityListCertificatesBinding binding;
    private ListCertificatesPresenter presenter;
    private AddCertificatePresenter addCertificatePresenter;
    private CertificatesAdapter adapter;
    private String studentId;
    private String mssv;
    private List<Certificates> allCertificates;
    private static final int REQUEST_CODE_CSV_PICKER = 2;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityListCertificatesBinding.inflate(getLayoutInflater());
        View view = binding.getRoot();
        setContentView(view);
        setSupportActionBar(binding.toolbar);

        studentId = getIntent().getStringExtra("STUDENT_ID");
        mssv = getIntent().getStringExtra("MSSV");

        binding.rvCertificates.setLayoutManager(new LinearLayoutManager(this));
        adapter = new CertificatesAdapter(new ArrayList<>(), new CertificatesAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(Certificates certificate, int position) {
                Intent intent = new Intent(ListCertificatesActivity.this, CertificateDetailActivity.class);
                intent.putExtra("CERTIFICATE", certificate);
                intent.putExtra("STUDENT_ID", studentId);
                startActivity(intent);
            }
        });
        binding.rvCertificates.setAdapter(adapter);

        presenter = new ListCertificatesPresenter(this);
        addCertificatePresenter = new AddCertificatePresenter(this);
        presenter.loadCertificates(studentId);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu_certificate,menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        Intent intent;
        if (item.getItemId() == R.id.add_certificate) {
            intent = new Intent(this, AddCertificateActivity.class);
            intent.putExtra("STUDENT_ID", studentId);
            startActivity(intent);
            return true;
        } else if (item.getItemId() == R.id.import_file) {
            selectCSVFileForImport();
            return true;
        } else if (item.getItemId() == R.id.export_file) {
            exportCertificatesToCSV(mssv, allCertificates);
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    private void selectCSVFileForImport() {
        Intent intent = new Intent(Intent.ACTION_GET_CONTENT);
        intent.addCategory(Intent.CATEGORY_OPENABLE);
        intent.setType("*/*");
        startActivityForResult(intent, REQUEST_CODE_CSV_PICKER);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == REQUEST_CODE_CSV_PICKER && resultCode == RESULT_OK && data != null) {
            Uri uri = data.getData();
            importCertificatesFromCSV(uri);
        }
    }

    private void importCertificatesFromCSV(Uri uri) {
        try {
            InputStream inputStream = getContentResolver().openInputStream(uri);
            BufferedReader reader = new BufferedReader(new InputStreamReader(inputStream));

            String line;

            while ((line = reader.readLine()) != null) {
                String[] values = line.split(",");
                if (values.length == 3) {
                    Certificates certificate = createCertificateFromCSV(values);
                    addCertificatePresenter.addCertificates(studentId, certificate);
                }
            }

            reader.close();
        } catch (IOException e) {
            e.printStackTrace();
            Toast.makeText(this, "Failed to import certificates", Toast.LENGTH_SHORT).show();
        }
    }

    private void exportCertificatesToCSV(String studentId, List<Certificates> certificates) {
        if (certificates.isEmpty()) {
            Toast.makeText(this, "No certificates to export", Toast.LENGTH_SHORT).show();
            return;
        }

        try {
            File dir = Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOWNLOADS);
            String fileName = studentId + "_certificates.csv";
            File file = new File(dir, fileName);

            FileWriter writer = new FileWriter(file);
            writer.append("\uFEFF");
            CSVWriter csvWriter = new CSVWriter(writer);

            for (Certificates certificate : certificates) {
                String[] data = {
                        certificate.getTitle(),
                        certificate.getIssueDate(),
                        certificate.getExpiryDate()
                };

                csvWriter.writeNext(data);
            }

            csvWriter.close();
            Toast.makeText(this, "Certificates exported to " + file.getAbsolutePath(), Toast.LENGTH_LONG).show();
        } catch (IOException e) {
            e.printStackTrace();
            Toast.makeText(this, "Failed to export certificates", Toast.LENGTH_SHORT).show();
        }
    }


    private Certificates createCertificateFromCSV(String[] values) {
        Certificates certificate = new Certificates();
        certificate.setTitle(values[0]);
        String issueDateStr = values[1];
        String expiryDateStr = values[2];

        SimpleDateFormat inputDateFormat = new SimpleDateFormat("dd/MM/yyyy", Locale.US);
        SimpleDateFormat outputDateFormat = new SimpleDateFormat("yyyy-MM-dd", Locale.US);

        try {
            Date issueDate = inputDateFormat.parse(issueDateStr);
            Date expiryDate = inputDateFormat.parse(expiryDateStr);
            certificate.setIssueDate(outputDateFormat.format(issueDate));
            certificate.setExpiryDate(outputDateFormat.format(expiryDate));
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return certificate;
    }

    @Override
    public void display(List<Certificates> certificates) {
        allCertificates = certificates;
        adapter.setCertificates(certificates);
        adapter.notifyDataSetChanged();
    }

    @Override
    public void displayError(String message) {
        Toast.makeText(this, message, Toast.LENGTH_LONG).show();
    }

    @Override
    public void displayCertificatesAddSuccess() {
        Toast.makeText(this, "Certificate added successfully", Toast.LENGTH_SHORT).show();
    }

    @Override
    public void displayCertificatesAddError(String message) {
        Toast.makeText(this, "Failed to add Certificate: " + message, Toast.LENGTH_SHORT).show();
    }
}