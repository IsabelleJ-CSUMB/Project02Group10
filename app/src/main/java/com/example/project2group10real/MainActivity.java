package com.example.project2group10real;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;

import androidx.appcompat.app.AppCompatActivity;

import com.example.project2group10real.databinding.ActivityMainBinding;

public class MainActivity extends AppCompatActivity {




    private ActivityMainBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityMainBinding.inflate(getLayoutInflater());

        setContentView(binding.getRoot());


        SharedPreferences sharedPref = getSharedPreferences("UserPrefs", Context.MODE_PRIVATE);
        Boolean isLoggedIn = sharedPref.getBoolean("isLoggedIn", false);

        if(isLoggedIn) {
            //shared pref is shared in login and uses the same file to read and write from
            int savedUserID = sharedPref.getInt("loggedInUserID", -1);
            startActivity(LandingActivity.landingActivityIntentFactory(getApplicationContext(), savedUserID));
            finish();
            return;
        }


        binding.loginButton.setOnClickListener(new View.OnClickListener() {

            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, LoginActivity.class);
                startActivity(intent);
            }
        });

        binding.signUpButton.setOnClickListener(new View.OnClickListener() {

            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, SignUpActivity.class);
                startActivity(intent);
            }
        });


    }

    static Intent mainIntentFactory(Context context) {
        return new Intent(context, MainActivity.class);
    }
}