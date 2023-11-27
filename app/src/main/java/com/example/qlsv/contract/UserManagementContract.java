package com.example.qlsv.contract;

import com.example.qlsv.dto.User;

import java.util.List;

public interface UserManagementContract {
    interface View {
        void display(List<User> users);
        void displayError(String message);
    }

    interface Presenter {
        void loadUsers(String currentUserId);
    }

    interface Model {
        void getUsers(String currentUserId, OnFinishedListener listener);

        interface OnFinishedListener {
            void onFinished(List<User> users);
            void onFailure(Throwable t);
        }
    }
}

