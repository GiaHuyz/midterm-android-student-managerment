package com.example.qlsv;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import com.example.qlsv.contract.LoginContract;
import com.example.qlsv.databinding.ActivityLoginBinding;
import com.example.qlsv.dto.User;
import com.example.qlsv.presenter.LoginPresenter;

public class LoginActivity extends AppCompatActivity implements LoginContract.View{

    private ActivityLoginBinding binding;
    private LoginContract.Presenter presenter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityLoginBinding.inflate(getLayoutInflater());
        View view = binding.getRoot();
        setContentView(view);

        presenter = new LoginPresenter(this);
        binding.btnLogin.setOnClickListener(v ->
                presenter.attemptLogin(binding.emailLogin.getText().toString(), binding.passwordLogin.getText().toString())
        );
    }

    @Override
    public void loginSuccess(User user) {
        Intent intent;
        switch (user.getRole()) {
            case "admin":
                intent = new Intent(LoginActivity.this, UserManagementActivity.class);
                intent.putExtra("PROFILE", user.getId());
                break;
            case "manager":
                intent = new Intent(LoginActivity.this, StudentManagementActivity.class);
                intent.putExtra("PROFILE", user);
                break;
            case "student":
                intent = new Intent(LoginActivity.this, UserDetailActivity.class);
                intent.putExtra("IS_STUDENT", "TRUE");
                break;
            default:
                Toast.makeText(this, "Invalid role", Toast.LENGTH_SHORT).show();
                return;
        }
        startActivity(intent);
        finish();
    }

    @Override
    public void loginFailure(String errorMessage) {
        Toast.makeText(this, "Login Failed: " + errorMessage, Toast.LENGTH_SHORT).show();
    }
}