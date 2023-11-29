package com.example.qlsv;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Patterns;
import android.view.View;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Toast;

import com.example.qlsv.contract.UserAddContact;
import com.example.qlsv.databinding.ActivityAddUserBinding;
import com.example.qlsv.databinding.ActivityLoginBinding;
import com.example.qlsv.dto.User;
import com.example.qlsv.presenter.UserAddPresenter;

import java.util.HashMap;

public class AddUserActivity extends AppCompatActivity implements UserAddContact.View {
    private ActivityAddUserBinding binding;
    private UserAddPresenter presenter;
    private RadioButton rdRole;
    private RadioButton rdStatus;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityAddUserBinding.inflate(getLayoutInflater());
        View view = binding.getRoot();
        setContentView(view);

        if(getIntent().getStringExtra("MANAGER") != null) {
            binding.layoutRole.setVisibility(View.GONE);
        }

        binding.rdRole.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                if (checkedId == R.id.rdRoleStudent) {
                    binding.edtMSSV.setVisibility(View.VISIBLE);
                } else {
                    binding.edtMSSV.setVisibility(View.GONE);
                    binding.edtMSSV.setText("");
                }
            }
        });

        presenter = new UserAddPresenter(this);
        addUser();
    }

    private void addUser() {
        binding.btnAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (!validateInput()) {
                    return;
                }

                User newUser = new User();
                newUser.setName(binding.edtName.getText().toString());
                newUser.setEmail(binding.edtEmail.getText().toString());
                newUser.setAge(Integer.parseInt(binding.edtAge.getText().toString()));
                newUser.setAvatarURL("");

                int selectedRoleId = binding.rdRole.getCheckedRadioButtonId();
                rdRole = binding.getRoot().findViewById(selectedRoleId);
                newUser.setRole(rdRole.getText().toString().toLowerCase());

                int selectedStatusId = binding.rdStatus.getCheckedRadioButtonId();
                rdStatus = binding.getRoot().findViewById(selectedStatusId);
                newUser.setStatus(rdStatus.getText().toString().toLowerCase());

                newUser.setPhone(binding.edtPhone.getText().toString());

                if(binding.edtMSSV.getVisibility() == View.VISIBLE) {
                    newUser.setStudentId(binding.edtMSSV.getText().toString());
                }

                presenter.addUser(newUser);
            }
        });
    }

    private boolean validateInput() {
        if (binding.edtName.getText().toString().isEmpty() ||
                binding.edtEmail.getText().toString().isEmpty() ||
                binding.edtAge.getText().toString().isEmpty() ||
                binding.edtPhone.getText().toString().isEmpty()) {
            Toast.makeText(getApplicationContext(), "Please fill in all fields", Toast.LENGTH_SHORT).show();
            return false;
        }

        if(binding.edtMSSV.getVisibility() == View.VISIBLE && binding.edtMSSV.getText().toString().isEmpty()) {
            Toast.makeText(getApplicationContext(), "Please fill in all fields", Toast.LENGTH_SHORT).show();
            return false;
        }

        if(!binding.edtPhone.getText().toString().matches("\\d{10}")) {
            Toast.makeText(getApplicationContext(), "Phone number must be 10 digits", Toast.LENGTH_SHORT).show();
            return false;
        }

        if (!Patterns.EMAIL_ADDRESS.matcher(binding.edtEmail.getText().toString()).matches()) {
            Toast.makeText(getApplicationContext(), "Invalid email format", Toast.LENGTH_SHORT).show();
            return false;
        }

        int age = Integer.parseInt(binding.edtAge.getText().toString());
        if (age <= 0 || age >= 100) {
            Toast.makeText(getApplicationContext(), "Invalid age", Toast.LENGTH_SHORT).show();
            return false;
        }

        return true;
    }

    @Override
    public void displayUserAddSuccess() {
        Toast.makeText(this, "User added successfully", Toast.LENGTH_SHORT).show();
    }

    @Override
    public void displayUserAddError(String message) {
        Toast.makeText(this, "Failed to add user: " + message, Toast.LENGTH_SHORT).show();
    }

}