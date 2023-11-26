package com.example.qlsv.contract;

public interface SignUpContract {
    interface View {
        void signUpSuccess();
        void signUpError(String error);
    }

    interface Presenter {
        void handleSignUp(String name, int age, String phone, String email, String password, String confirmPassword);
    }

    interface Model {
        void signUp(String name, int age, String phone, String email, String password, OnFinishedListener onFinishedListener);

        interface OnFinishedListener {
            void onFinished(boolean isSuccess, String message);
        }
    }
}

