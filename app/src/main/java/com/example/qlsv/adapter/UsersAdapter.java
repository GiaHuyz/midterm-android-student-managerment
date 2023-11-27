package com.example.qlsv.adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.qlsv.R;
import com.example.qlsv.dto.User;

import java.util.List;

public class UsersAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {
    private static final int TYPE_USER = 0;
    private static final int TYPE_STUDENT = 1;

    private OnItemClickListener listener;

    private List<User> users;

    public UsersAdapter(List<User> users, OnItemClickListener listener) {
        this.users = users;
        this.listener = listener;
    }

    public void setUsers(List<User> users) {
        this.users = users;
    }

    public interface OnItemClickListener {
        void onItemClick(User user, int position);
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view;
        if (viewType == TYPE_USER) {
            view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_users, parent, false);
            return new UserViewHolder(view);
        } else {
            view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_students, parent, false);
            return new StudentViewHolder(view);
        }
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
        User user = users.get(position);
        if (holder instanceof StudentViewHolder) {
            ((StudentViewHolder) holder).bind(user, position, listener);
        } else if (holder instanceof UserViewHolder) {
            ((UserViewHolder) holder).bind(user, position, listener);
        }
    }

    @Override
    public int getItemViewType(int position) {
        User user = users.get(position);
        if ("student".equals(user.getRole()) && user.getStudentInfo() != null) {
            return TYPE_STUDENT;
        } else {
            return TYPE_USER;
        }
    }

    @Override
    public int getItemCount() {
        return users.size();
    }

    public class UserViewHolder extends RecyclerView.ViewHolder {
        TextView tvNameUser, tvAgeUser, tvPhoneUser, tvStatusUser;

        public UserViewHolder(@NonNull View itemView) {
            super(itemView);
            tvNameUser = itemView.findViewById(R.id.tvNameUser);
            tvAgeUser = itemView.findViewById(R.id.tvAgeUser);
            tvPhoneUser = itemView.findViewById(R.id.tvPhoneUser);
            tvStatusUser = itemView.findViewById(R.id.tvStatusUser);
        }

        public void bind(User user, int position, OnItemClickListener listener) {
            tvNameUser.setText(user.getName());
            tvAgeUser.setText(String.valueOf(user.getAge()));
            tvPhoneUser.setText(user.getPhone());
            tvStatusUser.setText(user.getStatus());

            itemView.setOnClickListener(v -> listener.onItemClick(user, position));
        }
    }

    public class StudentViewHolder extends RecyclerView.ViewHolder {
        TextView tvMSSV, tvNameUser, tvAgeUser, tvPhoneUser, tvStatusUser;

        public StudentViewHolder(@NonNull View itemView) {
            super(itemView);
            tvMSSV = itemView.findViewById(R.id.tvMSSV);
            tvNameUser = itemView.findViewById(R.id.tvNameUser);
            tvAgeUser = itemView.findViewById(R.id.tvAgeUser);
            tvPhoneUser = itemView.findViewById(R.id.tvPhoneUser);
            tvStatusUser = itemView.findViewById(R.id.tvStatusUser);
        }

        public void bind(User student, int position, OnItemClickListener listener) {
            if (student.getStudentId() != null) {
                tvMSSV.setText(student.getStudentId());
            }
            tvNameUser.setText(student.getName());
            tvAgeUser.setText(String.valueOf(student.getAge()));
            tvPhoneUser.setText(student.getPhone());
            tvStatusUser.setText(student.getStatus());

            itemView.setOnClickListener(v -> listener.onItemClick(student, position));
        }
    }

}

