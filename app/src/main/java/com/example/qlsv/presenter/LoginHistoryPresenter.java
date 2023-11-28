package com.example.qlsv.presenter;

import com.example.qlsv.contract.LoginHistoryContract;
import com.example.qlsv.dto.LoginHistory;
import com.example.qlsv.model.LoginHistoryModel;

import java.util.List;

public class LoginHistoryPresenter implements LoginHistoryContract.Presenter {
    private LoginHistoryContract.View view;
    private LoginHistoryContract.Model model;

    public LoginHistoryPresenter(LoginHistoryContract.View view) {
        this.view = view;
        this.model = new LoginHistoryModel();
    }

    @Override
    public void loadLoginHistories() {
        model.getLoginHistories(new LoginHistoryContract.Model.OnFinishedListener() {
            @Override
            public void onFinished(List<LoginHistory> loginHistories) {
                view.display(loginHistories);
            }

            @Override
            public void onFailure(Throwable t) {
                view.displayError(t.getMessage());
            }
        });
    }
}
