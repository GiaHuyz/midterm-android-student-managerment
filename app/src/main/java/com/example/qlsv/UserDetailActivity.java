package com.example.qlsv;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;

import com.example.qlsv.databinding.ActivitySignUpBinding;
import com.example.qlsv.databinding.ActivityUserDetailBinding;
import com.example.qlsv.dto.User;

public class UserDetailActivity extends AppCompatActivity {
    private ActivityUserDetailBinding binding;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityUserDetailBinding.inflate(getLayoutInflater());
        View view = binding.getRoot();
        setContentView(view);

        loadInfoUser();
    }

    private void loadInfoUser() {
        User user = (User) getIntent().getSerializableExtra("USER");
        binding.tvnameUS.setText(user.getName());
        binding.tvMail.setText(user.getEmail());
        binding.edtName.setText(user.getName());
        binding.edtPhone.setText(user.getPhone());
        binding.edtEmail.setText(user.getEmail());
        binding.edtAge.setText(String.valueOf(user.getAge()));
        switch (user.getRole()) {
            case "admin":
                binding.rdRoleAdmin.setChecked(true);
                break;
            case "manager":
                binding.rdRoleManager.setChecked(true);
                break;
            case "student":
                binding.rdRoleStudent.setChecked(true);
                break;
            default:
                break;
        }
        switch (user.getStatus()) {
            case "normal":
                binding.rdStatusNormal.setChecked(true);
                break;
            case "locked":
                binding.rdStatusLocked.setChecked(true);
                break;
            default:
                break;
        }
    }
}