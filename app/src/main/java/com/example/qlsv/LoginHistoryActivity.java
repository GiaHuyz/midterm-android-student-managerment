package com.example.qlsv;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import com.example.qlsv.adapter.LoginHistoryAdapter;
import com.example.qlsv.contract.LoginHistoryContract;
import com.example.qlsv.databinding.ActivityLoginBinding;
import com.example.qlsv.databinding.ActivityLoginHistoryBinding;
import com.example.qlsv.dto.LoginHistory;
import com.example.qlsv.presenter.LoginHistoryPresenter;

import java.util.ArrayList;
import java.util.List;

public class LoginHistoryActivity extends AppCompatActivity implements LoginHistoryContract.View {
    private ActivityLoginHistoryBinding binding;
    private LoginHistoryAdapter adapter;
    private LoginHistoryContract.Presenter presenter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityLoginHistoryBinding.inflate(getLayoutInflater());
        View view = binding.getRoot();
        setContentView(view);

        binding.rvLoginHistories.setLayoutManager(new LinearLayoutManager(this));

        presenter = new LoginHistoryPresenter(this);
        presenter.loadLoginHistories();
    }


    @Override
    public void display(List<LoginHistory> loginHistories) {
        adapter = new LoginHistoryAdapter(loginHistories);
        binding.rvLoginHistories.setAdapter(adapter);
    }

    @Override
    public void displayError(String message) {
        Toast.makeText(this, message, Toast.LENGTH_LONG).show();
    }
}