package com.example.qlsv.model;

import com.example.qlsv.contract.EditCertificateContract;
import com.example.qlsv.dto.Certificates;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.SetOptions;

public class EditCertificateModel implements EditCertificateContract.Model {
    private FirebaseFirestore firestore;

    public EditCertificateModel() {
        firestore = FirebaseFirestore.getInstance();
    }

    @Override
    public void editCertificate(String studentId, String certificateId, Certificates updatedCertificate, OnCertificateEditListener listener) {
        firestore.collection("users").document(studentId)
                .collection("certificates").document(certificateId)
                .set(updatedCertificate, SetOptions.merge())
                .addOnSuccessListener(aVoid -> listener.onCertificateEditSuccess())
                .addOnFailureListener(e -> listener.onCertificateEditFailure(e));
    }

    @Override
    public void deleteCertificate(String studentId, String certificateId, OnCertificateDeleteListener listener) {
        firestore.collection("users").document(studentId)
                .collection("certificates").document(certificateId)
                .delete()
                .addOnSuccessListener(aVoid -> listener.onCertificateDeleteSuccess())
                .addOnFailureListener(e -> listener.onCertificateDeleteFailure(e));
    }
}
