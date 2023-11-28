package com.example.qlsv.model;

import com.example.qlsv.contract.UserAddContact;
import com.example.qlsv.dto.User;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.FirebaseFirestore;

public class UserAddModel implements UserAddContact.Model {

    private FirebaseFirestore firestore;
    private FirebaseAuth firebaseAuth;

    public UserAddModel() {
        firestore = FirebaseFirestore.getInstance();
        firebaseAuth = FirebaseAuth.getInstance();
    }

    @Override
    public void addUser(User user, OnUserAddListener listener) {
        String email = user.getEmail();
        String password = email.substring(0, email.indexOf('@'));

        firebaseAuth.createUserWithEmailAndPassword(email, password)
                .addOnSuccessListener(authResult -> {
                    String userId = authResult.getUser().getUid();
                    user.setId(userId);

                    firestore.collection("users")
                            .document(userId)
                            .set(user)
                            .addOnSuccessListener(aVoid -> listener.onUserAddSuccess())
                            .addOnFailureListener(e -> listener.onUserAddFailure(e));
                })
                .addOnFailureListener(e -> listener.onUserAddFailure(e));
    }
}
