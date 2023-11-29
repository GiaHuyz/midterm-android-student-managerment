package com.example.qlsv;

import androidx.appcompat.app.AppCompatActivity;

import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.View;
import android.widget.DatePicker;
import android.widget.Toast;

import com.example.qlsv.contract.EditCertificateContract;
import com.example.qlsv.databinding.ActivityCertificateDetailBinding;
import com.example.qlsv.dto.Certificates;
import com.example.qlsv.presenter.EditCertificatePresenter;

import java.util.Calendar;

public class CertificateDetailActivity extends AppCompatActivity implements EditCertificateContract.View{
    private ActivityCertificateDetailBinding binding;
    private DatePickerDialog datePickerDialog;
    private String currentDateField;
    private EditCertificatePresenter presenter;
    private Certificates certificate;
    private String studentId;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityCertificateDetailBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());


        certificate = (Certificates) getIntent().getSerializableExtra("CERTIFICATE");
        studentId = getIntent().getStringExtra("STUDENT_ID");
        presenter = new EditCertificatePresenter(this);

        initDatePicker();
        loadCertificate();

        binding.btnEditCertificate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String title = binding.edtNameCertificate.getText().toString();

                if (title.isEmpty()) {
                    Toast.makeText(CertificateDetailActivity.this, "Please fill title", Toast.LENGTH_SHORT).show();
                    return;
                }

                String issueDate = binding.issueDate.getText().toString();
                String expiryDate = binding.expiryDate.getText().toString();

                Certificates newCertificate = new Certificates(title, issueDate, expiryDate);
                newCertificate.setId(certificate.getId());

                presenter.editCertificate(studentId, certificate.getId(), newCertificate);
            }
        });

        binding.btnDeleteCertificate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                presenter.deleteCertificate(studentId, certificate.getId());
            }
        });

        binding.issueDate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                currentDateField = "issue";
                datePickerDialog.show();
            }
        });

        binding.expiryDate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                currentDateField = "expiry";
                datePickerDialog.show();
            }
        });

    }

    private void loadCertificate() {
        binding.edtNameCertificate.setText(certificate.getTitle());
        binding.issueDate.setText(certificate.getIssueDate());
        binding.expiryDate.setText(certificate.getExpiryDate());
    }

    @Override
    public void displayCertificateEditSuccess() {
        Toast.makeText(this, "Certificate updated successfully", Toast.LENGTH_SHORT).show();
    }

    @Override
    public void displayCertificateEditError(String message) {
        Toast.makeText(this, "Failed to update Certificate: " + message, Toast.LENGTH_SHORT).show();
    }

    @Override
    public void displayCertificateDeletionSuccess() {
        Toast.makeText(this, "Certificate deleted successfully", Toast.LENGTH_SHORT).show();
        finish();
    }

    @Override
    public void displayCertificateDeletionError(String message) {
        Toast.makeText(this, "Failed to delete Certificate: " + message, Toast.LENGTH_SHORT).show();
    }

    private void initDatePicker()
    {
        DatePickerDialog.OnDateSetListener dateSetListener = new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker datePicker, int year, int month, int day) {
                month = month + 1;
                String date = makeDateString(day, month, year);
                if ("issue".equals(currentDateField)) {
                    binding.issueDate.setText(date);
                } else if ("expiry".equals(currentDateField)) {
                    binding.expiryDate.setText(date);
                }
            }
        };

        Calendar cal = Calendar.getInstance();
        int year = cal.get(Calendar.YEAR);
        int month = cal.get(Calendar.MONTH);
        int day = cal.get(Calendar.DAY_OF_MONTH);

        datePickerDialog = new DatePickerDialog(this, dateSetListener, year, month, day);

    }

    private String makeDateString(int day, int month, int year)
    {
        return year + "-" + month + "-" + day;
    }
}