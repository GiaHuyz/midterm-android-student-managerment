package com.example.qlsv.model;

import com.example.qlsv.contract.UserManagementContract;
import com.example.qlsv.dto.User;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.ArrayList;
import java.util.List;

public class UserManagementModel implements UserManagementContract.Model {
    private FirebaseFirestore firestore;

    public UserManagementModel() {
        firestore = FirebaseFirestore.getInstance();
    }

    @Override
    public void getUsers(String currentUserId, OnFinishedListener listener) {
        FirebaseFirestore db = FirebaseFirestore.getInstance();
        db.collection("users").get().addOnSuccessListener(queryDocumentSnapshots -> {
            List<User> users = new ArrayList<>();
            for (DocumentSnapshot snapshot : queryDocumentSnapshots) {
                User user = snapshot.toObject(User.class);
                if (user != null && !snapshot.getId().equals(currentUserId)) {
                    user.setId(snapshot.getId());
                    users.add(user);
                }
            }
            listener.onFinished(users);
        }).addOnFailureListener(listener::onFailure);
    }
}

