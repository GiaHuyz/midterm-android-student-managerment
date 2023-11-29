package com.example.qlsv.contract;

import com.example.qlsv.dto.Certificates;

public interface AddCertificatesContract {
    interface View {
        void displayCertificatesAddSuccess();
        void displayCertificatesAddError(String message);
    }

    interface Presenter {
        void addCertificates(String studentId, Certificates certificate);
    }

    interface Model {
        void addCertificates(String studentId, Certificates certificate, OnCertificatesAddListener listener);

        interface OnCertificatesAddListener {
            void onCertificatesAddSuccess();
            void onCertificatesAddFailure(Throwable t);
        }
    }
}
