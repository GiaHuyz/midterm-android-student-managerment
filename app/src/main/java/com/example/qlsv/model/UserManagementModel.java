package com.example.qlsv.model;

import androidx.annotation.NonNull;

import com.example.qlsv.contract.UserManagementContract;
import com.example.qlsv.dto.User;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
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
        firestore.collection("users").addSnapshotListener(new EventListener<QuerySnapshot>() {
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
                        users.add(user);
                    }
                }
                listener.onFinished(users);
            }
        });
    }

    @Override
    public void getProfile(String currentUserId, OnFinishedProfile listener) {
        firestore.collection("users")
                .document(currentUserId)
                .get()
                .addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                        if (task.isSuccessful()) {
                            DocumentSnapshot document = task.getResult();
                            if (document != null && document.exists()) {
                                User user = document.toObject(User.class);
                                listener.onFinished(user);
                            } else {
                                listener.onFailure(new Exception("User not found"));
                            }
                        } else {
                            listener.onFailure(task.getException());
                        }
                    }
                });
    }
}

