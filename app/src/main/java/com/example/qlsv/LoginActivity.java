package com.example.qlsv;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.text.SpannableString;
import android.text.Spanned;
import android.text.TextPaint;
import android.text.method.LinkMovementMethod;
import android.text.style.ClickableSpan;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.example.qlsv.contract.LoginContract;
import com.example.qlsv.databinding.ActivityLoginBinding;
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
    public void loginSuccess() {
        startActivity(new Intent(LoginActivity.this, UserManagementActivity.class));
    }

    @Override
    public void loginFailure(String errorMessage) {
        Toast.makeText(this, "Login Failed: " + errorMessage, Toast.LENGTH_SHORT).show();
    }

}