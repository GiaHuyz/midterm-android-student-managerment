package com.example.qlsv;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.View;
import android.widget.Toast;

import com.example.qlsv.adapter.UsersAdapter;
import com.example.qlsv.contract.UserManagementContract;
import com.example.qlsv.databinding.ActivityUserManagementBinding;
import com.example.qlsv.dto.User;
import com.example.qlsv.presenter.UserManagementPresenter;

import java.util.ArrayList;
import java.util.List;

public class UserManagementActivity extends AppCompatActivity implements UserManagementContract.View, UsersAdapter.OnItemClickListener {

    private ActivityUserManagementBinding binding;
    private UserManagementContract.Presenter presenter;
    private UsersAdapter usersAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityUserManagementBinding.inflate(getLayoutInflater());
        View view = binding.getRoot();
        setContentView(view);

        setSupportActionBar(binding.toolbar);
        User user = (User) getIntent().getSerializableExtra("USER");

        binding.rvUsers.setLayoutManager(new LinearLayoutManager(this));
        usersAdapter = new UsersAdapter(new ArrayList<>(), this);
        binding.rvUsers.setAdapter(usersAdapter);

        presenter = new UserManagementPresenter(this);
        presenter.loadUsers(user.getId());
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu_usermanagement,menu);
        return true;
    }

    @Override
    public void display(List<User> users) {
        usersAdapter.setUsers(users);
        usersAdapter.notifyDataSetChanged();
    }

    @Override
    public void displayError(String message) {
        Toast.makeText(this, message, Toast.LENGTH_LONG).show();
    }

    @Override
    public void onItemClick(User user, int position) {
        Intent intent;
        if ("student".equals(user.getRole()) && user.getStudentInfo() != null) {
            intent = new Intent(this, StudentDetailActivity.class);
        } else {
            intent = new Intent(this, UserDetailActivity.class);
        }
        intent.putExtra("USER", user);
        startActivity(intent);
    }

}