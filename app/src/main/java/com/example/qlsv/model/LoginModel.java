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
        firebaseAuth.signInWithEmailAndPassword(email, password)
                .addOnCompleteListener(task -> {
                    if (task.isSuccessful()) {
                        logLoginHistory(email);

                        String userId = firebaseAuth.getCurrentUser().getUid();

                        firestore.collection("users").document(userId).get()
                                .addOnSuccessListener(documentSnapshot -> {
                                    if (documentSnapshot.exists()) {
                                        User user = documentSnapshot.toObject(User.class);
                                        if (user != null) {
                                            if ("locked".equals(user.getStatus())) {
                                                listener.onFailure("User is locked");
                                                firebaseAuth.signOut();
                                            } else {
                                                logLoginHistory(email);
                                                listener.onSuccess(user);
                                            }
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
        Map<String, Object> loginRecord = new HashMap<>();
        loginRecord.put("email", email);
        loginRecord.put("timestamp", new Date());

        firestore.collection("login_history").add(loginRecord);
    }
}

