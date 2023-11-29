package com.example.qlsv.contract;

import com.example.qlsv.dto.User;

import java.util.List;

public interface ListStudentContract {
    interface View {
        void display(List<User> students);
        void displayError(String message);
        void display(User user);
    }

    interface Presenter {
        void loadStudents();
        void getProfile(String currentUserId);
    }

    interface Model {
        void getStudents(Model.OnFinishedListener listener);
        void getProfile(String currentUserId, OnFinishedProfile listener);
        interface OnFinishedListener {
            void onFinished(List<User> students);
            void onFailure(Throwable t);
        }
        interface OnFinishedProfile {
            void onFinished(User user);
            void onFailure(Throwable t);
        }
    }
}
