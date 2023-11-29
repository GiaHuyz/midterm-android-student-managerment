package com.example.qlsv.adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.qlsv.R;
import com.example.qlsv.dto.Certificates;
import com.example.qlsv.dto.User;

import java.util.List;

public class CertificatesAdapter extends RecyclerView.Adapter<CertificatesAdapter.CertificatesViewHolder>  {
    private List<Certificates> certificates;
    private OnItemClickListener listener;

    public CertificatesAdapter(List<Certificates> certificates, OnItemClickListener listener) {
        this.certificates = certificates;
        this.listener = listener;
    }

    public void setCertificates(List<Certificates> certificates) {
        this.certificates = certificates;
    }

    public interface OnItemClickListener {
        void onItemClick(Certificates certificates, int position);
    }

    @NonNull
    @Override
    public CertificatesAdapter.CertificatesViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_certificates, parent, false);
        return new CertificatesAdapter.CertificatesViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull CertificatesAdapter.CertificatesViewHolder holder, int position) {
        Certificates certificate = certificates.get(position);
        holder.bind(certificate, position, listener);
    }

    @Override
    public int getItemCount() {
        return certificates.size();
    }


    public static class CertificatesViewHolder extends RecyclerView.ViewHolder {

        private TextView tvTitle, tvIssueDate, tvExpiryDate;
        public CertificatesViewHolder(@NonNull View itemView) {
            super(itemView);
            tvTitle = itemView.findViewById(R.id.tvTitle);
            tvIssueDate = itemView.findViewById(R.id.tvIssueDate);
            tvExpiryDate = itemView.findViewById(R.id.tvExpiryDate);
        }

        public void bind(Certificates certificate, int position, OnItemClickListener listener) {
            tvTitle.setText(certificate.getTitle());
            tvIssueDate.setText(certificate.getIssueDate());
            tvExpiryDate.setText(certificate.getExpiryDate());

            itemView.setOnClickListener(v -> listener.onItemClick(certificate, position));
        }
    }
}
