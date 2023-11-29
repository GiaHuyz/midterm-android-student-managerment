package com.example.qlsv;

import androidx.appcompat.app.AppCompatActivity;

import android.app.DatePickerDialog;
import android.os.Bundle;
import android.view.View;
import android.widget.DatePicker;
import android.widget.Toast;

import com.example.qlsv.contract.AddCertificatesContract;
import com.example.qlsv.databinding.ActivityAddCertificateBinding;
import com.example.qlsv.dto.Certificates;
import com.example.qlsv.presenter.AddCertificatePresenter;

import java.util.Calendar;

public class AddCertificateActivity extends AppCompatActivity implements AddCertificatesContract.View {
    private ActivityAddCertificateBinding binding;
    private DatePickerDialog datePickerDialog;
    private String currentDateField;
    private AddCertificatePresenter presenter;
    private String studentId;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityAddCertificateBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        studentId = getIntent().getStringExtra("STUDENT_ID");

        presenter = new AddCertificatePresenter(this);

        binding.btnAddCertificate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String title = binding.edtNameCertificate.getText().toString();

                if (title.isEmpty()) {
                    Toast.makeText(AddCertificateActivity.this, "Please fill title", Toast.LENGTH_SHORT).show();
                    return;
                }

                String issueDate = binding.issueDate.getText().toString();
                String expiryDate = binding.expiryDate.getText().toString();

                Certificates certificate = new Certificates(title, issueDate, expiryDate);
                presenter.addCertificates(studentId, certificate);
            }
        });

        initDatePicker();
        binding.issueDate.setText(getTodaysDate());
        binding.expiryDate.setText(getTodaysDate());

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

    @Override
    public void displayCertificatesAddSuccess() {
        Toast.makeText(this, "Certificate added successfully", Toast.LENGTH_SHORT).show();
    }

    @Override
    public void displayCertificatesAddError(String message) {
        Toast.makeText(this, "Failed to add Certificate: " + message, Toast.LENGTH_SHORT).show();
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

    private String getTodaysDate()
    {
        Calendar cal = Calendar.getInstance();
        int year = cal.get(Calendar.YEAR);
        int month = cal.get(Calendar.MONTH);
        month = month + 1;
        int day = cal.get(Calendar.DAY_OF_MONTH);
        return makeDateString(day, month, year);
    }


    private String makeDateString(int day, int month, int year)
    {
        return year + "-" + month + "-" + day;
    }
}