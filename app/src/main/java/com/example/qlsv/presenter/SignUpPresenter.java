package com.example.qlsv.presenter;

import com.example.qlsv.contract.SignUpContract;

public class SignUpPresenter implements SignUpContract.Presenter, SignUpContract.Model.OnFinishedListener {

    private SignUpContract.View view;
    private SignUpContract.Model model;

    public SignUpPresenter(SignUpContract.View view, SignUpContract.Model model) {
        this.view = view;
        this.model = model;
    }

    @Override
    public void handleSignUp(String name, int age, String phone, String email, String password, String confirmPassword) {
        model.signUp(name, age, phone, email, password, this);
    }

    @Override
    public void onFinished(boolean isSuccess, String message) {
        if (view != null) {
            if (isSuccess) {
                view.signUpSuccess();
            } else {
                view.signUpError(message);
            }
        }
    }
}

