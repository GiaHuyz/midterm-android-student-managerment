package com.example.qlsv.model;

import com.example.qlsv.contract.LoginContract;
import com.example.qlsv.dto.User;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

public class LoginModel implements LoginContract.Model {
    private FirebaseAuth firebaseAuth;
    private FirebaseFirestore firestore;

    public LoginModel() {
        firebaseAuth = FirebaseAuth.getInstance();
        firestore = FirebaseFirestore.getInstance();
    }

    @Override
    public void performLogin(String email, String password, OnLoginListener listener) {
        FirebaseAuth firebaseAuth = FirebaseAuth.getInstance();
        firebaseAuth.signInWithEmailAndPassword(email, password)
                .addOnCompleteListener(task -> {
                    if (task.isSuccessful()) {
                        logLoginHistory(email);
                        FirebaseFirestore db = FirebaseFirestore.getInstance();
                        String userId = firebaseAuth.getCurrentUser().getUid();

                        db.collection("users").document(userId).get()
                                .addOnSuccessListener(documentSnapshot -> {
                                    if (documentSnapshot.exists()) {
                                        User user = documentSnapshot.toObject(User.class);
                                        if (user != null) {
                                            listener.onSuccess(user);
                                        } else {
                                            listener.onFailure("Failed to parse user data");
                                        }
                                    } else {
                                        listener.onFailure("User not found in Firestore");
                                    }
                                })
                                .addOnFailureListener(e -> listener.onFailure(e.getMessage()));
                    } else {
                        listener.onFailure(task.getException().getMessage());
                    }
                });
    }

    private void logLoginHistory(String email) {
        FirebaseFirestore db = FirebaseFirestore.getInstance();
        Map<String, Object> loginRecord = new HashMap<>();
        loginRecord.put("email", email);
        loginRecord.put("timestamp", new Date());

        db.collection("login_history").add(loginRecord);
    }
}

