package com.example.qlsv.presenter;

import com.example.qlsv.contract.EditCertificateContract;
import com.example.qlsv.contract.UserEditContract;
import com.example.qlsv.dto.Certificates;
import com.example.qlsv.model.EditCertificateModel;
import com.example.qlsv.model.UserEditModel;

public class EditCertificatePresenter implements EditCertificateContract.Presenter {
    private EditCertificateContract.View view;
    private EditCertificateContract.Model model;

    public EditCertificatePresenter(EditCertificateContract.View view) {
        this.view = view;
        this.model = new EditCertificateModel();
    }
    @Override
    public void editCertificate(String studentId, String certificateId, Certificates updatedCertificate) {
        model.editCertificate(studentId, certificateId, updatedCertificate, new EditCertificateContract.Model.OnCertificateEditListener() {
            @Override
            public void onCertificateEditSuccess() {
                view.displayCertificateEditSuccess();
            }

            @Override
            public void onCertificateEditFailure(Throwable t) {
                view.displayCertificateEditError(t.getMessage());
            }
        });
    }

    @Override
    public void deleteCertificate(String studentId, String certificateId) {
        model.deleteCertificate(studentId, certificateId, new EditCertificateContract.Model.OnCertificateDeleteListener() {
            @Override
            public void onCertificateDeleteSuccess() {
                view.displayCertificateDeletionSuccess();
            }

            @Override
            public void onCertificateDeleteFailure(Throwable t) {
                view.displayCertificateDeletionError(t.getMessage());
            }
        });
    }
}
