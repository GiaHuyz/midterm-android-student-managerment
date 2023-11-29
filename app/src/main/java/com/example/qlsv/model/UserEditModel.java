package com.example.qlsv.model;

import android.net.Uri;

import com.example.qlsv.contract.UserEditContract;
import com.example.qlsv.dto.User;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;

import java.util.UUID;

public class UserEditModel implements UserEditContract.Model {

    private FirebaseFirestore firestore;
    private StorageReference storageRef;

    public UserEditModel() {
        firestore = FirebaseFirestore.getInstance();
        storageRef = FirebaseStorage.getInstance().getReference();
    }

    @Override
    public void editUser(User user, OnUserEditListener listener) {
        firestore.collection("users").document(user.getId())
                .set(user)
                .addOnSuccessListener(aVoid -> listener.onUserEditSuccess())
                .addOnFailureListener(e -> listener.onUserEditFailure(e));
    }

    @Override
    public void deleteUser(String userId, OnUserDeleteListener listener) {
        firestore.collection("users").document(userId)
                .delete()
                .addOnSuccessListener(aVoid -> listener.onUserDeleteSuccess(userId))
                .addOnFailureListener(e -> listener.onUserDeleteFailure(e));
    }

    @Override
    public void uploadImage(String userId, Uri imageUri, OnImageUploadListener listener) {
        final StorageReference ref = storageRef.child("images/" + UUID.randomUUID().toString());
        ref.putFile(imageUri)
                .addOnSuccessListener(taskSnapshot -> ref.getDownloadUrl().addOnSuccessListener(uri -> {
                    listener.onUploadSuccess(uri.toString());
                    FirebaseFirestore db = FirebaseFirestore.getInstance();
                    db.collection("users").document(userId)
                            .update("avatarURL", uri.toString())
                            .addOnSuccessListener(aVoid -> listener.onUploadSuccess(uri.toString()))
                            .addOnFailureListener(listener::onUploadFailure);
                }))
                .addOnFailureListener(listener::onUploadFailure);
    }
}
