package com.example.qlsv.model;

import android.util.Log;

import androidx.annotation.NonNull;

import com.example.qlsv.contract.ListCertificatesContract;
import com.example.qlsv.dto.Certificates;
import com.example.qlsv.dto.User;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;
import java.util.List;

import javax.annotation.Nullable;

public class ListCertificatesModel implements ListCertificatesContract.Model {
    private FirebaseFirestore firestore;

    public ListCertificatesModel() {
        firestore = FirebaseFirestore.getInstance();
    }
    @Override
    public void getCertificates(String currentStudentId, OnFinishedListener listener) {
        firestore.collection("users").document(currentStudentId).collection("certificates")
                .addSnapshotListener(new EventListener<QuerySnapshot>() {
                    @Override
                    public void onEvent(@Nullable QuerySnapshot queryDocumentSnapshots, @Nullable FirebaseFirestoreException e) {
                        if (e != null) {
                            listener.onFailure(e);
                            return;
                        }

                        if (queryDocumentSnapshots != null && !queryDocumentSnapshots.isEmpty()) {
                            List<Certificates> certificatesList = queryDocumentSnapshots.toObjects(Certificates.class);
                            listener.onFinished(certificatesList);
                        } else {
                            listener.onFinished(new ArrayList<>());
                        }
                    }

                });
    }
}
