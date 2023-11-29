package com.example.qlsv;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.SearchView;
import androidx.recyclerview.widget.LinearLayoutManager;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.RadioGroup;
import android.widget.Toast;

import com.example.qlsv.adapter.UsersAdapter;
import com.example.qlsv.contract.ListStudentContract;
import com.example.qlsv.contract.UserAddContact;
import com.example.qlsv.databinding.ActivityStudentManagementBinding;
import com.example.qlsv.dto.User;
import com.example.qlsv.presenter.ListStudentPresenter;
import com.example.qlsv.presenter.UserAddPresenter;
import com.opencsv.CSVWriter;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

public class StudentManagementActivity extends AppCompatActivity implements ListStudentContract.View, UserAddContact.View, UsersAdapter.OnItemClickListener {
    private ActivityStudentManagementBinding binding;
    private ListStudentPresenter presenter;
    private UserAddPresenter addPresenter;
    private UsersAdapter adapter;
    private List<User> allStudents;
    private List<User> filteredStudents;
    private static final int REQUEST_CODE_CSV_PICKER = 1;
    private User profile;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityStudentManagementBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        setSupportActionBar(binding.toolbar);

        profile = (User) getIntent().getSerializableExtra("PROFILE");

        allStudents = new ArrayList<>();
        filteredStudents = new ArrayList<>();

        binding.rvStudents.setLayoutManager(new LinearLayoutManager(this));
        adapter = new UsersAdapter(filteredStudents, this);
        binding.rvStudents.setAdapter(adapter);

        presenter = new ListStudentPresenter(this);
        presenter.loadStudents();
        addPresenter = new UserAddPresenter(this);

        binding.search.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                searchStudents(newText);
                return true;
            }
        });

        binding.sortBy.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                sortStudents();
            }
        });

        binding.sortOptions.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                sortStudents();
            }
        });
    }

    private void sortStudents() {
        int sortBy = binding.sortBy.getCheckedRadioButtonId();
        int sortOption = binding.sortOptions.getCheckedRadioButtonId();

        Comparator<User> comparator = null;
        if (sortBy == R.id.rdName) {
            comparator = Comparator.comparing(User::getName);
        } else if (sortBy == R.id.rdAge) {
            comparator = Comparator.comparing(User::getAge);
        }

        if (comparator != null) {
            if (sortOption == R.id.rdDESC) {
                comparator = comparator.reversed();
            }
            Collections.sort(filteredStudents, comparator);
            adapter.notifyDataSetChanged();
        }
    }

    private void searchStudents(String text) {
        filteredStudents.clear();
        if (text.isEmpty()) {
            filteredStudents.addAll(allStudents);
        } else {
            text = text.toLowerCase();
            for (User student : allStudents) {
                if (student.getName().toLowerCase().contains(text) || String.valueOf(student.getAge()).contains(text)
                || student.getPhone().contains(text) || student.getStudentId().contains(text)) {
                    filteredStudents.add(student);
                }
            }
        }
        adapter.notifyDataSetChanged();
    }

    private void selectCSVFile() {
        Intent intent = new Intent(Intent.ACTION_GET_CONTENT);
        intent.addCategory(Intent.CATEGORY_OPENABLE);
        intent.setType("*/*");
        startActivityForResult(intent, REQUEST_CODE_CSV_PICKER);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == REQUEST_CODE_CSV_PICKER && resultCode == RESULT_OK && data != null) {
            Uri uri = data.getData();
            readCSVFile(uri);
        }
    }


    private void readCSVFile(Uri uri) {
        try {
            InputStream inputStream = getContentResolver().openInputStream(uri);
            BufferedReader reader = new BufferedReader(new InputStreamReader(inputStream));

            String line;
            List<User> users = new ArrayList<>();
            while ((line = reader.readLine()) != null) {
                String[] values = line.split(",");
                if (values.length == 7) {
                    User user = createUserFromCSV(values);
                    users.add(user);
                }
            }
            addPresenter.addListUser(users);
            reader.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void exportStudentListToCSV() {
        if (allStudents.isEmpty()) {
            Toast.makeText(this, "No students to export", Toast.LENGTH_SHORT).show();
            return;
        }

        try {
            File dir = Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOWNLOADS);
            String fileName = "all_students.csv";
            File file = new File(dir, fileName);

            FileWriter writer = new FileWriter(file);
            writer.append("\uFEFF");
            CSVWriter csvWriter = new CSVWriter(writer);

            for (User student : allStudents) {
                String[] data = {
                        student.getName(),
                        String.valueOf(student.getAge()),
                        student.getEmail(),
                        student.getPhone(),
                        student.getStatus(),
                        student.getStudentId(),
                        student.getRole()
                };
                csvWriter.writeNext(data);
            }

            csvWriter.close();
            Toast.makeText(this, "All students exported to " + file.getAbsolutePath(), Toast.LENGTH_LONG).show();
        } catch (IOException e) {
            e.printStackTrace();
            Toast.makeText(this, "Failed to export all students", Toast.LENGTH_SHORT).show();
        }
    }

    private User createUserFromCSV(String[] values) {
        User user = new User();
        user.setName(values[0]);
        user.setAge(Integer.parseInt(values[1]));
        user.setEmail(values[2]);
        user.setPhone(values[3]);
        user.setStatus("normal");
        user.setStudentId(values[5]);
        user.setRole("student");
        return user;
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu_student,menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        Intent intent;
        if (item.getItemId() == R.id.add_student) {
            intent = new Intent(this, AddUserActivity.class);
            intent.putExtra("MANAGER", "TRUE");
            startActivity(intent);
            return true;
        } else if (item.getItemId() == R.id.import_file) {
            selectCSVFile();
            return true;
        } else if (item.getItemId() == R.id.export_file) {
            exportStudentListToCSV();
            return true;
        } else if (item.getItemId() == R.id.profile) {
            presenter.getProfile(profile.getId());
            return true;
        }
        return super.onOptionsItemSelected(item);
    }



    @Override
    public void display(List<User> students) {
        allStudents.clear();
        allStudents.addAll(students);
        searchStudents(binding.search.getQuery().toString());
    }

    @Override
    public void displayError(String message) {
        Toast.makeText(this, message, Toast.LENGTH_LONG).show();
    }

    @Override
    public void displayUserAddSuccess() {
        Toast.makeText(this, "User added successfully", Toast.LENGTH_SHORT).show();
    }

    @Override
    public void displayUserAddError(String message) {
        Toast.makeText(this, "Failed to add user: " + message, Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onItemClick(User user, int position) {
        Intent intent = new Intent(this, UserDetailActivity.class);
        intent.putExtra("USER", user);
        intent.putExtra("IS_MANAGER", "TRUE");
        startActivity(intent);
    }

    @Override
    public void display(User user) {
        Intent intent = new Intent(this, UserDetailActivity.class);
        intent.putExtra("PROFILE", user);
        startActivity(intent);
    }
}