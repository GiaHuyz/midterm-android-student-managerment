package com.example.qlsv.contract;

import android.net.Uri;

import com.example.qlsv.dto.Certificates;

public interface EditCertificateContract {
    interface View {
        void displayCertificateEditSuccess();
        void displayCertificateEditError(String message);
        void displayCertificateDeletionSuccess();
        void displayCertificateDeletionError(String message);
    }

    interface Presenter {
        void editCertificate(String studentId, String certificateId, Certificates updatedCertificate);
        void deleteCertificate(String studentId, String certificateId);
    }

    interface Model {
        void editCertificate(String studentId, String certificateId, Certificates updatedCertificate, OnCertificateEditListener listener);

        interface OnCertificateEditListener {
            void onCertificateEditSuccess();
            void onCertificateEditFailure(Throwable t);
        }

        void deleteCertificate(String studentId, String certificateId, OnCertificateDeleteListener listener);

        interface OnCertificateDeleteListener {
            void onCertificateDeleteSuccess();
            void onCertificateDeleteFailure(Throwable t);
        }
    }
}
