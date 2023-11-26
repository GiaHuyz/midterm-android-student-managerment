package com.example.qlsv.presenter;

import com.example.qlsv.contract.LoginContract;
import com.example.qlsv.model.LoginModel;

public class LoginPresenter implements LoginContract.Presenter {
    private LoginContract.View view;
    private LoginContract.Model model;

    public LoginPresenter(LoginContract.View view) {
        this.view = view;
        this.model = new LoginModel();
    }

    @Override
    public void attemptLogin(String email, String password) {
        model.performLogin(email, password, new LoginContract.Model.OnLoginListener() {
            @Override
            public void onSuccess() {
                view.loginSuccess();
            }

            @Override
            public void onFailure(String errorMessage) {
                view.loginFailure(errorMessage);
            }
        });
    }
}

