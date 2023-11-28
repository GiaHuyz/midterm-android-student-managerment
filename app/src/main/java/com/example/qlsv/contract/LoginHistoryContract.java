package com.example.qlsv.contract;

import com.example.qlsv.dto.LoginHistory;
import com.example.qlsv.dto.User;

import java.util.List;

public interface LoginHistoryContract {
    interface View {
        void display(List<LoginHistory> loginHistories);
        void displayError(String message);
    }

    interface Presenter {
        void loadLoginHistories();
    }

    interface Model {
        void getLoginHistories(LoginHistoryContract.Model.OnFinishedListener listener);

        interface OnFinishedListener {
            void onFinished(List<LoginHistory> loginHistories);
            void onFailure(Throwable t);
        }
    }
}
