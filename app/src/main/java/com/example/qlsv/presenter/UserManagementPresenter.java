package com.example.qlsv.presenter;

import com.example.qlsv.contract.UserManagementContract;
import com.example.qlsv.dto.User;
import com.example.qlsv.model.UserManagementModel;

import java.util.List;

public class UserManagementPresenter implements UserManagementContract.Presenter {
    private UserManagementContract.View view;
    private UserManagementContract.Model model;

    public UserManagementPresenter(UserManagementContract.View view) {
        this.view = view;
        this.model = new UserManagementModel();
    }

    @Override
    public void loadUsers() {
        model.getUsers(new UserManagementContract.Model.OnFinishedListener() {
            @Override
            public void onFinished(List<User> users) {
                view.display(users);
            }

            @Override
            public void onFailure(Throwable t) {
                view.displayError(t.getMessage());
            }
        });
    }
}

