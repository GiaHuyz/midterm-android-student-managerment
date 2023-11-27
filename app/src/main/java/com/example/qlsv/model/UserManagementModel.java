package com.example.qlsv.model;

import com.example.qlsv.contract.UserManagementContract;
import com.example.qlsv.dto.User;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;
import java.util.List;

import javax.annotation.Nullable;

public class UserManagementModel implements UserManagementContract.Model {
    private FirebaseFirestore firestore;

    public UserManagementModel() {
        firestore = FirebaseFirestore.getInstance();
    }

    @Override
    public void getUsers(String currentUserId, OnFinishedListener listener) {
        FirebaseFirestore db = FirebaseFirestore.getInstance();
        db.collection("users").addSnapshotListener(new EventListener<QuerySnapshot>() {
            @Override
            public void onEvent(@Nullable QuerySnapshot snapshots,
                                @Nullable FirebaseFirestoreException e) {
                if (e != null) {
                    listener.onFailure(e);
                    return;
                }

                List<User> users = new ArrayList<>();
                for (DocumentSnapshot doc : snapshots) {
                    User user = doc.toObject(User.class);
                    if(!doc.getId().equals(currentUserId)){
                        user.setId(doc.getId());
                        users.add(user);
                    }
                }
                listener.onFinished(users);
            }
        });
    }


}

