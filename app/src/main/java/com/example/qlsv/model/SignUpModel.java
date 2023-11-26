package com.example.qlsv.model;



import android.util.Log;

import androidx.annotation.NonNull;

import com.example.qlsv.contract.SignUpContract;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

public class SignUpModel implements SignUpContract.Model {
    private FirebaseAuth firebaseAuth;
    private FirebaseFirestore firestore;

    public SignUpModel() {
        firebaseAuth = FirebaseAuth.getInstance();
        firestore = FirebaseFirestore.getInstance();
    }

    @Override
    public void signUp(String name, int age, String phone, String email, String password, OnFinishedListener onFinishedListener) {
        firebaseAuth.createUserWithEmailAndPassword(email, password)
                .addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {

                            String userId = firebaseAuth.getCurrentUser().getUid();
                            DocumentReference documentReference = firestore.collection("users").document(userId);

                            Map<String, Object> user = new HashMap<>();
                            user.put("name", name);
                            user.put("age", age);
                            user.put("phone", phone);

                            documentReference.set(user)
                                    .addOnSuccessListener(new OnSuccessListener<Void>() {
                                        @Override
                                        public void onSuccess(Void unused) {
                                            Log.d("USER CREATED", "User information is added for " + userId);
                                            onFinishedListener.onFinished(true, "Đăng ký và thêm thông tin người dùng thành công.");
                                        }
                                    })
                                    .addOnFailureListener(new OnFailureListener() {
                                        @Override
                                        public void onFailure(@NonNull Exception e) {
                                            Log.e("ERROR", "Error adding user information", e);
                                            onFinishedListener.onFinished(false, "Đăng ký thành công nhưng thêm thông tin người dùng thất bại: " + e.getMessage());
                                        }
                                    });
                        } else {
                            onFinishedListener.onFinished(false, task.getException().getMessage());
                        }
                    }
                });
    }

}
