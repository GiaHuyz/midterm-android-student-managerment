package com.example.qlsv;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
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
    private String profile;
    private User userProfile;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityUserManagementBinding.inflate(getLayoutInflater());
        View view = binding.getRoot();
        setContentView(view);

        setSupportActionBar(binding.toolbar);
        profile = getIntent().getStringExtra("PROFILE");

        binding.rvUsers.setLayoutManager(new LinearLayoutManager(this));
        usersAdapter = new UsersAdapter(new ArrayList<>(), this);
        binding.rvUsers.setAdapter(usersAdapter);

        presenter = new UserManagementPresenter(this);
        presenter.loadUsers(profile);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu_usermanagement,menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        Intent intent;
        if (item.getItemId() == R.id.menu_item_addUS) {
            intent = new Intent(UserManagementActivity.this, AddUserActivity.class);
            startActivity(intent);
            return true;
        } else if (item.getItemId() == R.id.login_history) {
            intent = new Intent(UserManagementActivity.this, LoginHistoryActivity.class);
            startActivity(intent);
            return true;
        } else if (item.getItemId() == R.id.profile) {
            presenter.getProfile(profile);
            return true;
        }
        return super.onOptionsItemSelected(item);
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
        Intent intent = new Intent(this, UserDetailActivity.class);
        intent.putExtra("USER", user);
        startActivity(intent);
    }

    @Override
    public void display(User user) {
        Intent intent = new Intent(UserManagementActivity.this, UserDetailActivity.class);
        intent.putExtra("PROFILE", user);
        startActivity(intent);
    }
}