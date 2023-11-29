package com.example.qlsv.model;

import com.example.qlsv.contract.AddCertificatesContract;
import com.example.qlsv.dto.Certificates;
import com.google.firebase.firestore.FirebaseFirestore;

public class AddCertificateModel implements AddCertificatesContract.Model {
    private FirebaseFirestore firestore;

    public AddCertificateModel() {
        firestore = FirebaseFirestore.getInstance();
    }
    @Override
    public void addCertificates(String studentId, Certificates certificate, OnCertificatesAddListener listener) {
        String newCertificateId = firestore.collection("users").document(studentId)
                .collection("certificates").document().getId();

        certificate.setId(newCertificateId);

        firestore.collection("users").document(studentId)
                .collection("certificates").document(newCertificateId)
                .set(certificate)
                .addOnSuccessListener(aVoid -> listener.onCertificatesAddSuccess())
                .addOnFailureListener(e -> listener.onCertificatesAddFailure(e));
    }
}
