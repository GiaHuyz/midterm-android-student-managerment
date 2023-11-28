package com.example.qlsv.presenter;

import android.net.Uri;

import com.example.qlsv.contract.UserEditContract;
import com.example.qlsv.contract.UserManagementContract;
import com.example.qlsv.dto.User;
import com.example.qlsv.model.UserEditModel;
import com.example.qlsv.model.UserManagementModel;

public class UserEditPresenter implements UserEditContract.Presenter {

    private UserEditContract.View view;
    private UserEditContract.Model model;

    public UserEditPresenter(UserEditContract.View view) {
        this.view = view;
        this.model = new UserEditModel();
    }

    @Override
    public void editUser(User user) {
        model.editUser(user, new UserEditContract.Model.OnUserEditListener() {
            @Override
            public void onUserEditSuccess() {
                view.displayUserEditSuccess();
            }

            @Override
            public void onUserEditFailure(Throwable t) {
                view.displayUserEditError(t.getMessage());
            }
        });
    }

    @Override
    public void deleteUser(String userId) {
        model.deleteUser(userId, new UserEditContract.Model.OnUserDeleteListener() {
            @Override
            public void onUserDeleteSuccess(String userId) {
                view.displayUserDeletionSuccess(userId);
            }

            @Override
            public void onUserDeleteFailure(Throwable t) {
                view.displayUserDeletionError(t.getMessage());
            }
        });
    }

    @Override
    public void uploadImage(String userId, Uri imageUri) {
        model.uploadImage(userId, imageUri, new UserEditContract.Model.OnImageUploadListener() {
            @Override
            public void onUploadSuccess(String imageUrl) {
                view.onUploadSuccess(imageUrl);
            }

            @Override
            public void onUploadFailure(Exception e) {
                view.onUploadFailure(e.getMessage());
            }
        });
    }
}
