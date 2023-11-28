package com.example.qlsv.model;

import android.util.Log;

import com.example.qlsv.contract.LoginHistoryContract;
import com.example.qlsv.dto.LoginHistory;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.ArrayList;
import java.util.List;

public class LoginHistoryModel implements LoginHistoryContract.Model {
    private FirebaseFirestore firestore;

    public LoginHistoryModel() {
        firestore = FirebaseFirestore.getInstance();
    }

    @Override
    public void getLoginHistories(OnFinishedListener listener) {
        firestore.collection("login_history")
                .get()
                .addOnSuccessListener(queryDocumentSnapshots -> {
                    List<LoginHistory> loginHistories = new ArrayList<>();
                    for (DocumentSnapshot snapshot : queryDocumentSnapshots) {
                        LoginHistory history = snapshot.toObject(LoginHistory.class);
                        loginHistories.add(history);

                    }
                    listener.onFinished(loginHistories);
                })
                .addOnFailureListener(listener::onFailure);
    }
}
