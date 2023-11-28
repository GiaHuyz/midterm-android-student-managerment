package com.example.qlsv.adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.qlsv.R;
import com.example.qlsv.dto.LoginHistory;

import java.util.List;

public class LoginHistoryAdapter extends RecyclerView.Adapter<LoginHistoryAdapter.LoginHistoryViewHolder> {

    private List<LoginHistory> loginHistories;

    public LoginHistoryAdapter(List<LoginHistory> loginHistories) {
        this.loginHistories = loginHistories;
    }

    @NonNull
    @Override
    public LoginHistoryViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_login_history, parent, false);
        return new LoginHistoryViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull LoginHistoryViewHolder holder, int position) {
        LoginHistory history = loginHistories.get(position);
        holder.bind(history);
    }

    @Override
    public int getItemCount() {
        return loginHistories.size();
    }


    public static class LoginHistoryViewHolder extends RecyclerView.ViewHolder {
        private TextView tvEmail, tvTimestamp;

        public LoginHistoryViewHolder(@NonNull View itemView) {
            super(itemView);
            tvEmail = itemView.findViewById(R.id.tvEmail);
            tvTimestamp = itemView.findViewById(R.id.tvTime);
        }

        public void bind(LoginHistory history) {
            tvEmail.setText(history.getEmail());
            tvTimestamp.setText(history.getFormattedTimestamp());
        }
    }
}

