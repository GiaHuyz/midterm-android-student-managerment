package com.example.qlsv;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import com.example.qlsv.contract.SignUpContract;
import com.example.qlsv.databinding.ActivitySignUpBinding;
import com.example.qlsv.model.SignUpModel;
import com.example.qlsv.presenter.SignUpPresenter;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

public class SignUpActivity extends AppCompatActivity implements SignUpContract.View {

    private ActivitySignUpBinding binding;
    private SignUpContract.Presenter presenter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivitySignUpBinding.inflate(getLayoutInflater());
        View view = binding.getRoot();
        setContentView(view);

        presenter = new SignUpPresenter(this, new SignUpModel());
        binding.btnSignup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                presenter.handleSignUp(
                        binding.nameSignup.getText().toString(),
                        Integer.parseInt(binding.ageSignup.getText().toString()),
                        binding.phoneSignup.getText().toString(),
                        binding.emailSignup.getText().toString().trim(),
                        binding.passwordSignup.getText().toString().trim(),
                        binding.confirmPasswordSignup.getText().toString()
                );
            }
        });

    }



    @Override
    public void signUpSuccess() {
        startActivity(new Intent(getApplicationContext(), LoginActivity.class));
    }

    @Override
    public void signUpError(String error) {
        Toast.makeText(this, "Lỗi đăng ký" + error, Toast.LENGTH_SHORT).show();
    }
}