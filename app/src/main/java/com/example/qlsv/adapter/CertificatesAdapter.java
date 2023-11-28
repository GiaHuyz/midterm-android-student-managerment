package com.example.qlsv.adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.qlsv.R;
import com.example.qlsv.dto.Certificates;

import java.util.List;

public class CertificatesAdapter extends RecyclerView.Adapter<CertificatesAdapter.CertificatesViewHolder>  {
    private List<Certificates> certificates;

    public CertificatesAdapter(List<Certificates> certificates) {
        this.certificates = certificates;
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
        holder.bind(certificate);
    }

    @Override
    public int getItemCount() {
        return certificates.size();
    }


    public static class CertificatesViewHolder extends RecyclerView.ViewHolder {
        private TextView tvEmail, tvIssueDate, tvExpiryDate;

        public CertificatesViewHolder(@NonNull View itemView) {
            super(itemView);
            tvEmail = itemView.findViewById(R.id.tvEmail);
            tvIssueDate = itemView.findViewById(R.id.tvIssueDate);
            tvExpiryDate = itemView.findViewById(R.id.tvExpiryDate);
        }

        public void bind(Certificates certificate) {
            tvEmail = itemView.findViewById(R.id.tvEmail);
            tvIssueDate = itemView.findViewById(R.id.tvIssueDate);
            tvExpiryDate = itemView.findViewById(R.id.tvExpiryDate);
        }
    }
}
