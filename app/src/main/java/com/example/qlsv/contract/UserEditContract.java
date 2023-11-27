package com.example.qlsv.contract;

import com.example.qlsv.dto.User;

public interface UserEditContract {
    interface View {
        void displayUserEditSuccess();
        void displayUserEditError(String message);
    }

    interface Presenter {
        void editUser(User user);
    }

    interface Model {
        void editUser(User user, OnUserEditListener listener);

        interface OnUserEditListener {
            void onUserEditSuccess();
            void onUserEditFailure(Throwable t);
        }
    }
}
