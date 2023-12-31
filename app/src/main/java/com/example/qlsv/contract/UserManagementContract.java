package com.example.qlsv.contract;

import com.example.qlsv.dto.User;

import java.util.List;

public interface UserManagementContract {
    interface View {
        void display(List<User> users);
        void display(User user);
        void displayError(String message);
    }

    interface Presenter {
        void loadUsers(String currentUserId);
        void getProfile(String currentUserId);
    }

    interface Model {
        void getUsers(String currentUserId, OnFinishedListener listener);
        void getProfile(String currentUserId, OnFinishedProfile listener);

        interface OnFinishedListener {
            void onFinished(List<User> users);
            void onFailure(Throwable t);
        }

        interface OnFinishedProfile {
            void onFinished(User user);
            void onFailure(Throwable t);
        }
    }
}

