package com.example.project2group10real;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.project2group10real.database.UltimateBudgetingRepository;
import com.example.project2group10real.database.entities.User;
import com.example.project2group10real.databinding.ActivitySignUpBinding;

public class SignUpActivity extends AppCompatActivity {

    private ActivitySignUpBinding binding;
    private UltimateBudgetingRepository repository;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivitySignUpBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        repository = UltimateBudgetingRepository.getRepository(getApplication());

        binding.signUpButton.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                String username = binding.userNameSignUpEditText.getText().toString().trim();
                String password = binding.passwordSignUpEditText.getText().toString().trim();

                if (username.isEmpty() || password.isEmpty()) {
                    Toast.makeText(SignUpActivity.this, "Please enter username and password", Toast.LENGTH_SHORT).show();
                    return;
                }

                User newUser = new User(username, password);
                repository.insertUser(newUser);

                Toast.makeText(SignUpActivity.this, "Account created!", Toast.LENGTH_SHORT).show();
                Intent intent = MainActivity.intentFactory(SignUpActivity.this);
                startActivity(intent);
            }
        });

        binding.goBackToMainActivityButtonFromSignUp.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                Intent intent = MainActivity.intentFactory(SignUpActivity.this);
                startActivity(intent);
            }
        });
    }
    //Intent Factory

    public static Intent intentFactory(Context context) {
        return new Intent(context, SignUpActivity.class);
    }
}