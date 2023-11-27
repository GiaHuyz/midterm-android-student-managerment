package com.example.qlsv.contract;

import com.example.qlsv.dto.User;

public interface LoginContract {
    interface View {
        void loginSuccess(User user);
        void loginFailure(String errorMessage);
    }

    interface Presenter {
        void attemptLogin(String email, String password);
    }

    interface Model {
        void performLogin(String email, String password, OnLoginListener listener);

        interface OnLoginListener {
            void onSuccess(User user);
            void onFailure(String errorMessage);
        }
    }
}

