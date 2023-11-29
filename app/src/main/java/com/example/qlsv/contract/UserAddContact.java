package com.example.qlsv.contract;

import com.example.qlsv.dto.User;

import java.util.List;

public interface UserAddContact {
    interface View {
        void displayUserAddSuccess();
        void displayUserAddError(String message);
    }

    interface Presenter {
        void addUser(User user);
        void addListUser(List<User> users);
    }

    interface Model {
        void addUser(User user, OnUserAddListener listener);
        void addListUser(List<User> users, Model.OnUserAddListener listener);

        interface OnUserAddListener {
            void onUserAddSuccess();
            void onUserAddFailure(Throwable t);
        }

    }
}
