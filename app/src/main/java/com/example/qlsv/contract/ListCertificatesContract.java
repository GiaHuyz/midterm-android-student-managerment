package com.example.qlsv.contract;

import com.example.qlsv.dto.Certificates;
import com.example.qlsv.dto.User;

import java.util.List;

public interface ListCertificatesContract {
    interface View {
        void display(List<Certificates> certificates);
        void displayError(String message);
    }

    interface Presenter {
        void loadCertificates(String currentUserId);
    }

    interface Model {
        void getCertificates(String currentStudentId, OnFinishedListener listener);

        interface OnFinishedListener {
            void onFinished(List<Certificates> certificates);
            void onFailure(Throwable t);
        }
    }
}
