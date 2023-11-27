package com.example.qlsv;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;

import com.example.qlsv.databinding.ActivityStudentDetailBinding;
import com.example.qlsv.databinding.ActivityUserDetailBinding;
import com.example.qlsv.dto.User;

public class StudentDetailActivity extends AppCompatActivity {
    private ActivityStudentDetailBinding binding;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityStudentDetailBinding.inflate(getLayoutInflater());
        View view = binding.getRoot();
        setContentView(view);

        loadInfoUser();
    }

    private void loadInfoUser() {
        User user = (User) getIntent().getSerializableExtra("USER");
        binding.edtMSSV.setText(user.getStudentId());
        binding.edtName.setText(user.getName());
        binding.edtPhone.setText(user.getPhone());
        binding.edtEmail.setText(user.getEmail());
        binding.edtAge.setText(String.valueOf(user.getAge()));
        binding.rdRoleStudent.setChecked(true);
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