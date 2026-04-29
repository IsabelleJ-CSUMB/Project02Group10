package com.example.project2group10real;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;

import com.example.project2group10real.databinding.ActivityLandingBinding;
import com.example.project2group10real.databinding.ActivityMainBinding;

public class LandingActivity extends AppCompatActivity {
    private ActivityLandingBinding binding;
     public static final String TAG = "PROJECT2GROUP10_ULTIMATE_BUDGETING";
    public static final String LANDING_ACTIVITY_USERID = "LANDING_ACTIVITY_USERID";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityLandingBinding.inflate(getLayoutInflater());
        EdgeToEdge.enable(this);
        setContentView(binding.getRoot());

        binding.spendingButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(LandingActivity.this, SpendingActivity.class);
                startActivity(intent);
            }
        });

        binding.budgetingButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(LandingActivity.this, BudgetingActivity.class);
                startActivity(intent);
            }
        });


    }

    static Intent landingActivityIntentFactory(Context context, int userID) {
        Intent intent = new Intent(context, LandingActivity.class);
        intent.putExtra(LANDING_ACTIVITY_USERID, userID);
        return intent;
    }
}