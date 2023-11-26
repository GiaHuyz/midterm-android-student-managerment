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

public class UsersAdapter extends RecyclerView.Adapter<UsersAdapter.UserViewHolder> {

    private List<User> userList;

    public UsersAdapter(List<User> userList) {
        this.userList = userList;
    }

    @NonNull
    @Override
    public UserViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_users, parent, false);
        return new UserViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull UserViewHolder holder, int position) {
        User user = userList.get(position);
        holder.tvNameUser.setText(user.getName());
        holder.tvAgeUser.setText(String.valueOf(user.getAge()));
        holder.tvPhoneUser.setText(user.getPhone());
        holder.tvStatusUser.setText(user.getStatus());
    }

    @Override
    public int getItemCount() {
        return userList.size();
    }

    public void setUsers(List<User> users) {
        this.userList = users;
    }

    public static class UserViewHolder extends RecyclerView.ViewHolder {
        TextView tvNameUser, tvAgeUser, tvPhoneUser, tvStatusUser;

        public UserViewHolder(@NonNull View itemView) {
            super(itemView);
            tvNameUser = itemView.findViewById(R.id.tvNameUser);
            tvAgeUser = itemView.findViewById(R.id.tvAgeUser);
            tvPhoneUser = itemView.findViewById(R.id.tvPhoneUser);
            tvStatusUser = itemView.findViewById(R.id.tvStatusUser);
        }
    }
}

