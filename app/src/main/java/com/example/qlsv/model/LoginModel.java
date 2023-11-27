package com.example.qlsv.model;

import com.example.qlsv.contract.LoginContract;
import com.example.qlsv.dto.User;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.FirebaseFirestore;

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
                        FirebaseFirestore db = FirebaseFirestore.getInstance();
                        String userId = firebaseAuth.getCurrentUser().getUid();

                        db.collection("users").document(userId).get()
                                .addOnSuccessListener(documentSnapshot -> {
                                    if (documentSnapshot.exists()) {
                                        User user = documentSnapshot.toObject(User.class);
                                        if (user != null) {
                                            user.setId(documentSnapshot.getId());
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
}

