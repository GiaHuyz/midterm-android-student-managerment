package com.example.qlsv.presenter;

import com.example.qlsv.contract.ListCertificatesContract;
import com.example.qlsv.dto.Certificates;
import com.example.qlsv.model.ListCertificatesModel;

import java.util.List;

public class ListCertificatesPresenter implements ListCertificatesContract.Presenter {

    private ListCertificatesContract.View view;
    private ListCertificatesContract.Model model;

    public ListCertificatesPresenter(ListCertificatesContract.View view) {
        this.view = view;
        this.model = new ListCertificatesModel();
    }

    @Override
    public void loadCertificates(String currentUserId) {
        model.getCertificates(currentUserId, new ListCertificatesContract.Model.OnFinishedListener() {
            @Override
            public void onFinished(List<Certificates> certificates) {
                view.display(certificates);
            }

            @Override
            public void onFailure(Throwable t) {
                view.displayError(t.getMessage());
            }
        });
    }
}
