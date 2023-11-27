package com.example.qlsv;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.RadioButton;
import android.widget.Toast;

import com.example.qlsv.contract.UserEditContract;
import com.example.qlsv.databinding.ActivityUserDetailBinding;
import com.example.qlsv.dto.User;
import com.example.qlsv.presenter.UserEditPresenter;

public class UserDetailActivity extends AppCompatActivity implements UserEditContract.View {
    private ActivityUserDetailBinding binding;
    private RadioButton rdRole;
    private RadioButton rdStatus;
    private UserEditContract.Presenter presenter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityUserDetailBinding.inflate(getLayoutInflater());
        View view = binding.getRoot();
        setContentView(view);

        presenter = new UserEditPresenter(UserDetailActivity.this);
        User user = (User) getIntent().getSerializableExtra("USER");
        loadInfoUser(user);
        binding.btnEdit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                User newUser = new User();
                newUser.setId(user.getId());
                newUser.setName(binding.edtName.getText().toString());
                newUser.setEmail(binding.edtEmail.getText().toString());
                newUser.setAge(Integer.parseInt(binding.edtAge.getText().toString()));

                int selectedRoleId = binding.rdRole.getCheckedRadioButtonId();
                rdRole = binding.getRoot().findViewById(selectedRoleId);
                newUser.setRole(rdRole.getText().toString().toLowerCase());

                int selectedStatusId = binding.rdStatus.getCheckedRadioButtonId();
                rdStatus = binding.getRoot().findViewById(selectedStatusId);
                newUser.setStatus(rdStatus.getText().toString().toLowerCase());

                newUser.setPhone(binding.edtPhone.getText().toString());

                presenter.editUser(newUser);
                binding.tvnameUS.setText(newUser.getName());
                binding.tvMail.setText(newUser.getEmail());
            }
        });
    }

    private void loadInfoUser(User user) {
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

    @Override
    public void displayUserEditSuccess() {
        Toast.makeText(this, "User updated successfully", Toast.LENGTH_SHORT).show();
    }

    @Override
    public void displayUserEditError(String message) {
        Toast.makeText(this, "Failed to update user: " + message, Toast.LENGTH_SHORT).show();
    }
}