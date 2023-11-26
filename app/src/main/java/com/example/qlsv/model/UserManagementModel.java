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
    public void getUsers(OnFinishedListener listener) {
        firestore.collection("users")
                .get()
                .addOnSuccessListener(queryDocumentSnapshots -> {
                    List<User> students = new ArrayList<>();
                    for (DocumentSnapshot snapshot : queryDocumentSnapshots) {
                        students.add(snapshot.toObject(User.class));
                    }
                    listener.onFinished(students);
                })
                .addOnFailureListener(listener::onFailure);
    }
}
