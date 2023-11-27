package com.example.qlsv.model;

import com.example.qlsv.contract.UserEditContract;
import com.example.qlsv.dto.User;
import com.google.firebase.firestore.FirebaseFirestore;

public class UserEditModel implements UserEditContract.Model {

    private FirebaseFirestore firestore;

    public UserEditModel() {
        firestore = FirebaseFirestore.getInstance();
    }

    @Override
    public void editUser(User user, OnUserEditListener listener) {
        FirebaseFirestore db = FirebaseFirestore.getInstance();
        db.collection("users").document(user.getId())
                .set(user)
                .addOnSuccessListener(aVoid -> listener.onUserEditSuccess())
                .addOnFailureListener(e -> listener.onUserEditFailure(e));
    }
}
