package com.example.qlsv.model;

import androidx.annotation.NonNull;

import com.example.qlsv.contract.ListStudentContract;
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

public class ListStudentModel implements ListStudentContract.Model {
    private FirebaseFirestore firestore;

    public ListStudentModel() {
        firestore = FirebaseFirestore.getInstance();
    }

    @Override
    public void getStudents(OnFinishedListener listener) {
        firestore.collection("users")
                .whereEqualTo("role", "student")
                .addSnapshotListener(new EventListener<QuerySnapshot>() {
                    @Override
                    public void onEvent(@Nullable QuerySnapshot queryDocumentSnapshots, @Nullable FirebaseFirestoreException e) {
                        if (e != null) {
                            listener.onFailure(e);
                            return;
                        }

                        List<User> students = new ArrayList<>();
                        for (DocumentSnapshot snapshot : queryDocumentSnapshots.getDocuments()) {
                            User user = snapshot.toObject(User.class);
                            if (user != null) {
                                students.add(user);
                            }
                        }
                        listener.onFinished(students);
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
