package com.example.qlsv;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;

import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import com.example.qlsv.adapter.UsersAdapter;
import com.example.qlsv.contract.UserManagementContract;
import com.example.qlsv.databinding.ActivitySignUpBinding;
import com.example.qlsv.databinding.ActivityUserManagementBinding;
import com.example.qlsv.dto.User;
import com.example.qlsv.presenter.UserManagementPresenter;

import java.util.ArrayList;
import java.util.List;

public class UserManagementActivity extends AppCompatActivity implements UserManagementContract.View {

    private ActivityUserManagementBinding binding;
    private UserManagementContract.Presenter presenter;
    private UsersAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityUserManagementBinding.inflate(getLayoutInflater());
        View view = binding.getRoot();
        setContentView(view);

        binding.rvUsers.setLayoutManager(new LinearLayoutManager(this));
        adapter = new UsersAdapter(new ArrayList<>());
        binding.rvUsers.setAdapter(adapter);

        presenter = new UserManagementPresenter(this);
        presenter.loadUsers();
    }

    @Override
    public void display(List<User> users) {
        adapter.setUsers(users);
        adapter.notifyDataSetChanged();
    }

    @Override
    public void displayError(String message) {
        Toast.makeText(this, message, Toast.LENGTH_LONG).show();
    }
}