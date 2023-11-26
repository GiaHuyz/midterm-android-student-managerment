package com.example.qlsv.contract;

public interface LoginContract {
    interface View {
        void loginSuccess();
        void loginFailure(String errorMessage);
    }

    interface Presenter {
        void attemptLogin(String email, String password);
    }

    interface Model {
        void performLogin(String email, String password, OnLoginListener listener);

        interface OnLoginListener {
            void onSuccess();
            void onFailure(String errorMessage);
        }
    }
}

