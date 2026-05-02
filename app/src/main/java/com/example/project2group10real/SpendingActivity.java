package com.example.project2group10real;

import android.content.Intent;
import android.os.Bundle;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.example.project2group10real.databinding.ActivitySpendingBinding;

public class SpendingActivity extends AppCompatActivity {
    private ActivitySpendingBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        binding = ActivitySpendingBinding.inflate(getLayoutInflater());
        EdgeToEdge.enable(this);
        setContentView(binding.getRoot());

        ViewCompat.setOnApplyWindowInsetsListener(binding.main, (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        // Button 1 → Log page
        binding.button1.setOnClickListener(v -> {
            startActivity(new Intent(SpendingActivity.this, LogActivity.class));
        });

        // Button 2 → See all months
        binding.button2.setOnClickListener(v -> {
            startActivity(new Intent(SpendingActivity.this, seeAllMonthsActivity.class));
        });

        // Button 3 → Data page
        binding.button3.setOnClickListener(v -> {
            startActivity(new Intent(SpendingActivity.this, DataViewActivity.class));
        });
    }
}