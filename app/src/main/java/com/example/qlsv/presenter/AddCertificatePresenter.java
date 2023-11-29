package com.example.qlsv.presenter;

import com.example.qlsv.contract.AddCertificatesContract;
import com.example.qlsv.contract.UserAddContact;
import com.example.qlsv.dto.Certificates;
import com.example.qlsv.model.AddCertificateModel;
import com.example.qlsv.model.UserAddModel;

public class AddCertificatePresenter implements AddCertificatesContract.Presenter {
    private AddCertificatesContract.View view;
    private AddCertificatesContract.Model model;

    public AddCertificatePresenter(AddCertificatesContract.View view) {
        this.view = view;
        this.model = new AddCertificateModel();
    }

    @Override
    public void addCertificates(String studentId, Certificates certificate) {
        model.addCertificates(studentId, certificate, new AddCertificatesContract.Model.OnCertificatesAddListener() {
            @Override
            public void onCertificatesAddSuccess() {
                view.displayCertificatesAddSuccess();
            }

            @Override
            public void onCertificatesAddFailure(Throwable t) {
                view.displayCertificatesAddError(t.getMessage());
            }
        });
    }
}
