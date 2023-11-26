package com.example.qlsv.contract;

import com.example.qlsv.dto.User;

import java.util.List;

public interface UserManagementContract {
    interface View {
        void display(List<User> students);
        void displayError(String message);
    }

    interface Presenter {
        void loadUsers();
    }

    interface Model {
        void getUsers(OnFinishedListener listener);

        interface OnFinishedListener {
            void onFinished(List<User> students);
            void onFailure(Throwable t);
        }
    }
}

