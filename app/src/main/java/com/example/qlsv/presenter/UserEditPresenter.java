package com.example.qlsv.presenter;

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
}
