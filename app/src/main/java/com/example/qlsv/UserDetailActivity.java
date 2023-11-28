package com.example.qlsv;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.util.Patterns;
import android.view.View;
import android.widget.CompoundButton;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.example.qlsv.contract.UserEditContract;
import com.example.qlsv.databinding.ActivityUserDetailBinding;
import com.example.qlsv.dto.User;
import com.example.qlsv.presenter.UserEditPresenter;

import java.util.HashMap;

public class UserDetailActivity extends AppCompatActivity implements UserEditContract.View {
    private ActivityUserDetailBinding binding;
    private RadioButton rdRole;
    private RadioButton rdStatus;
    private UserEditContract.Presenter presenter;
    private static final int PICK_IMAGE_REQUEST = 1;
    private User user;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityUserDetailBinding.inflate(getLayoutInflater());
        View view = binding.getRoot();
        setContentView(view);

        presenter = new UserEditPresenter(this);
        user = (User) getIntent().getSerializableExtra("USER");

        binding.rdRole.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                if (checkedId == R.id.rdRoleStudent) {
                    binding.edtMSSV.setVisibility(View.VISIBLE);
                } else {
                    binding.edtMSSV.setVisibility(View.GONE);
                }
            }
        });

        binding.btnUploadImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                selectImage();
            }
        });

        loadInfoUser(user);
        editUser(user);
        delele(user.getId());
    }

    private void selectImage() {
        Intent intent = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
        startActivityForResult(intent, PICK_IMAGE_REQUEST);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == PICK_IMAGE_REQUEST && resultCode == RESULT_OK && data != null && data.getData() != null) {
            Uri imageUri = data.getData();
            uploadImageToFirestore(user.getId(), imageUri);
        }
    }

    private void uploadImageToFirestore(String userId, Uri imageUri) {
        presenter.uploadImage(userId, imageUri);
    }

    @Override
    public void onUploadSuccess(String imageUrl) {
        Glide.with(this).load(imageUrl).into(binding.imgUS);
    }

    @Override
    public void onUploadFailure(String message) {
        Toast.makeText(this, message, Toast.LENGTH_LONG).show();
    }

    private void delele(String userId) {
        binding.btnDelete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                presenter.deleteUser(userId);
                finish();
            }
        });
    }

    private void editUser(User user) {
        binding.btnEdit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if(!validateInput()) {
                    return;
                }

                User newUser = new User();
                newUser.setId(user.getId());

                if(binding.edtMSSV.getVisibility() == View.VISIBLE) {
                    newUser.setStudentId(binding.edtMSSV.getText().toString());
                } else {
                    newUser.setStudentId(null);
                }

                newUser.setName(binding.edtName.getText().toString());
                newUser.setEmail(binding.edtEmail.getText().toString());
                newUser.setAge(Integer.parseInt(binding.edtAge.getText().toString()));

                int selectedRoleId = binding.rdRole.getCheckedRadioButtonId();
                rdRole = binding.getRoot().findViewById(selectedRoleId);
                newUser.setRole(rdRole.getText().toString().toLowerCase());

                int selectedStatusId = binding.rdStatus.getCheckedRadioButtonId();
                rdStatus = binding.getRoot().findViewById(selectedStatusId);
                newUser.setStatus(rdStatus.getText().toString().toLowerCase());

                newUser.setPhone(binding.edtPhone.getText().toString());

                presenter.editUser(newUser);
            }
        });
    }

    private void loadInfoUser(User user) {
        if(user.getStudentId() != null) {
            binding.edtMSSV.setVisibility(View.VISIBLE);
            binding.edtMSSV.setText(user.getStudentId());
        } else {
            binding.edtMSSV.setVisibility(View.GONE);
        }
        binding.edtName.setText(user.getName());
        binding.edtPhone.setText(user.getPhone());
        binding.edtEmail.setText(user.getEmail());
        binding.edtAge.setText(String.valueOf(user.getAge()));
        if(user.getAvatarURL() != null) {
            loadImage(user.getAvatarURL());
        }
        switch (user.getRole()) {
            case "admin":
                binding.rdRoleAdmin.setChecked(true);
                break;
            case "manager":
                binding.rdRoleManager.setChecked(true);
                break;
            case "student":
                binding.rdRoleStudent.setChecked(true);
                break;
            default:
                break;
        }
        switch (user.getStatus()) {
            case "normal":
                binding.rdStatusNormal.setChecked(true);
                break;
            case "locked":
                binding.rdStatusLocked.setChecked(true);
                break;
            default:
                break;
        }
    }

    public void loadImage(String imageUrl) {
        Glide.with(this)
                .load(imageUrl)
                .into(binding.imgUS);
    }

    private boolean validateInput() {
        if (binding.edtName.getText().toString().isEmpty() ||
                binding.edtEmail.getText().toString().isEmpty() ||
                binding.edtAge.getText().toString().isEmpty() ||
                binding.edtPhone.getText().toString().isEmpty()) {
            Toast.makeText(getApplicationContext(), "Please fill in all fields", Toast.LENGTH_SHORT).show();
            return false;
        }

        if(binding.edtMSSV.getVisibility() == View.VISIBLE && binding.edtMSSV.getText().toString().isEmpty()) {
            Toast.makeText(getApplicationContext(), "Please fill in all fields", Toast.LENGTH_SHORT).show();
            return false;
        }

        if(!binding.edtPhone.getText().toString().matches("\\d{10}")) {
            Toast.makeText(getApplicationContext(), "Phone number must be 10 digits", Toast.LENGTH_SHORT).show();
            return false;
        }

        if (!Patterns.EMAIL_ADDRESS.matcher(binding.edtEmail.getText().toString()).matches()) {
            Toast.makeText(getApplicationContext(), "Invalid email format", Toast.LENGTH_SHORT).show();
            return false;
        }

        int age = Integer.parseInt(binding.edtAge.getText().toString());
        if (age <= 0 || age >= 100) {
            Toast.makeText(getApplicationContext(), "Invalid age", Toast.LENGTH_SHORT).show();
            return false;
        }

        return true;
    }

    @Override
    public void displayUserDeletionSuccess(String userId) {
        Toast.makeText(this, "User deleted successfully", Toast.LENGTH_SHORT).show();
        finish();
    }

    @Override
    public void displayUserDeletionError(String message) {
        Toast.makeText(this, "Failed to delete user: " + message, Toast.LENGTH_SHORT).show();
    }

    @Override
    public void displayUserEditSuccess() {
        Toast.makeText(this, "User updated successfully", Toast.LENGTH_SHORT).show();
    }

    @Override
    public void displayUserEditError(String message) {
        Toast.makeText(this, "Failed to update user: " + message, Toast.LENGTH_SHORT).show();
    }
}