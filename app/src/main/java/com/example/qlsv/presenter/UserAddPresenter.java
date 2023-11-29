package com.example.qlsv.presenter;

import com.example.qlsv.contract.UserAddContact;
import com.example.qlsv.contract.UserEditContract;
import com.example.qlsv.dto.User;
import com.example.qlsv.model.UserAddModel;
import com.example.qlsv.model.UserEditModel;

import java.util.List;

public class UserAddPresenter implements UserAddContact.Presenter {
    private UserAddContact.View view;
    private UserAddContact.Model model;

    public UserAddPresenter(UserAddContact.View view) {
        this.view = view;
        this.model = new UserAddModel();
    }
    @Override
    public void addUser(User user) {
        model.addUser(user, new UserAddContact.Model.OnUserAddListener() {
            @Override
            public void onUserAddSuccess() {
                view.displayUserAddSuccess();
            }

            @Override
            public void onUserAddFailure(Throwable t) {
                view.displayUserAddError(t.getMessage());
            }
        });
    }

    @Override
    public void addListUser(List<User> users) {
        model.addListUser(users, new UserAddContact.Model.OnUserAddListener() {
            @Override
            public void onUserAddSuccess() {
                view.displayUserAddSuccess();
            }

            @Override
            public void onUserAddFailure(Throwable t) {
                view.displayUserAddError(t.getMessage());
            }
        });
    }
}
