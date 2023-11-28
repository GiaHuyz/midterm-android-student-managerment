package com.example.qlsv.contract;

import android.net.Uri;

import com.example.qlsv.dto.User;

public interface UserEditContract {
    interface View {
        void displayUserEditSuccess();
        void displayUserEditError(String message);
        void displayUserDeletionSuccess(String userId);
        void displayUserDeletionError(String message);
        void onUploadSuccess(String imageUrl);
        void onUploadFailure(String message);
    }

    interface Presenter {
        void editUser(User user);
        void deleteUser(String userId);
        void uploadImage(String userId, Uri imageUri);
    }

    interface Model {
        void editUser(User user, OnUserEditListener listener);

        interface OnUserEditListener {
            void onUserEditSuccess();
            void onUserEditFailure(Throwable t);
        }

        void deleteUser(String userId, OnUserDeleteListener listener);

        interface OnUserDeleteListener {
            void onUserDeleteSuccess(String userId);
            void onUserDeleteFailure(Throwable t);
        }

        void uploadImage(String userId, Uri imageUri, OnImageUploadListener listener);

        interface OnImageUploadListener {
            void onUploadSuccess(String imageUrl);
            void onUploadFailure(Exception e);
        }
    }
}
