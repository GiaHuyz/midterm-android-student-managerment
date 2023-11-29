package com.example.qlsv.model;

import android.util.Log;

import com.example.qlsv.contract.UserAddContact;
import com.example.qlsv.dto.User;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.WriteBatch;

import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;

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
                            .addOnSuccessListener(aVoid -> {
                                listener.onUserAddSuccess();
                            })
                            .addOnFailureListener(e -> listener.onUserAddFailure(e));

                })
                .addOnFailureListener(e -> listener.onUserAddFailure(e));
    }


    @Override
    public void addListUser(List<User> users, OnUserAddListener listener) {
        addUsers(users, 0, listener);
    }

    private void addUsers(List<User> users, int index, OnUserAddListener listener) {
        if (index < users.size()) {
            User user = users.get(index);
            String email = user.getEmail();
            String password = email.substring(0, email.indexOf('@'));

            firebaseAuth.createUserWithEmailAndPassword(email, password)
                    .addOnSuccessListener(authResult -> {
                        String userId = authResult.getUser().getUid();
                        user.setId(userId);

                        DocumentReference userRef = firestore.collection("users").document(userId);
                        userRef.set(user)
                                .addOnSuccessListener(aVoid -> {
                                    addUsers(users, index + 1, listener);
                                })
                                .addOnFailureListener(e -> {
                                    listener.onUserAddFailure(e);
                                });
                    })
                    .addOnFailureListener(e -> {
                        listener.onUserAddFailure(e);
                    });
        } else {
            listener.onUserAddSuccess();
        }
    }


}
