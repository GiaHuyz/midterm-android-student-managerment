package com.example.qlsv.presenter;

import com.example.qlsv.contract.ListCertificatesContract;
import com.example.qlsv.contract.ListStudentContract;
import com.example.qlsv.contract.UserManagementContract;
import com.example.qlsv.dto.Certificates;
import com.example.qlsv.dto.User;
import com.example.qlsv.model.ListCertificatesModel;
import com.example.qlsv.model.ListStudentModel;

import java.util.List;

public class ListStudentPresenter implements ListStudentContract.Presenter {
    private ListStudentContract.View view;
    private ListStudentContract.Model model;

    public ListStudentPresenter(ListStudentContract.View view) {
        this.view = view;
        this.model = new ListStudentModel();
    }
    @Override
    public void loadStudents() {
        model.getStudents(new ListStudentContract.Model.OnFinishedListener() {
            @Override
            public void onFinished(List<User> students) {
                view.display(students);
            }

            @Override
            public void onFailure(Throwable t) {
                view.displayError(t.getMessage());
            }
        });
    }

    @Override
    public void getProfile(String currentUserId) {
        model.getProfile(currentUserId, new ListStudentContract.Model.OnFinishedProfile() {
            @Override
            public void onFinished(User user) {
                view.display(user);
            }

            @Override
            public void onFailure(Throwable t) {
                view.displayError(t.getMessage());
            }
        });
    }

}
