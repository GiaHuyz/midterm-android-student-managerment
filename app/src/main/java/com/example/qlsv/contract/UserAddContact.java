package com.example.qlsv.contract;

import com.example.qlsv.dto.User;

public interface UserAddContact {
    interface View {
        void displayUserAddSuccess();
        void displayUserAddError(String message);
    }

    interface Presenter {
        void addUser(User user);
    }

    interface Model {
        void addUser(User user, OnUserAddListener listener);

        interface OnUserAddListener {
            void onUserAddSuccess();
            void onUserAddFailure(Throwable t);
        }
    }
}
