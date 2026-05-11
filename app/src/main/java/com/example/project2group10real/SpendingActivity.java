package com.example.project2group10real;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.View;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;

import com.example.project2group10real.database.UltimateBudgetingRepository;
import com.example.project2group10real.database.entities.SpendingLog;
import com.example.project2group10real.databinding.ActivitySpendingBinding;

public class SpendingActivity extends AppCompatActivity {
    private static final String SPENDING_VIEW_ACTIVITY_USERID = "SPENDING_VIEW_ACTIVITY_USERID";
    private ActivitySpendingBinding binding;
    private double localCost = 0;
    private String localName = "";
    private int loggedInID;
    private UltimateBudgetingRepository repository;

    private double budgetGoal = 0;
    private double totalSpent = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        binding = ActivitySpendingBinding.inflate(getLayoutInflater());
        EdgeToEdge.enable(this);
        setContentView(binding.getRoot());

        repository = UltimateBudgetingRepository.getRepository(getApplication());
        loggedInID = getIntent().getIntExtra(SPENDING_VIEW_ACTIVITY_USERID, -1);


        SharedPreferences prefs = getSharedPreferences("BudgetingPrefs", Context.MODE_PRIVATE);
        budgetGoal = prefs.getFloat("budget_goal_" + loggedInID, 0f);

        repository.getAllSpendingLogsByUserID(loggedInID).observe(this, logs -> {
            totalSpent = 0;
            if (logs != null) {
                for (SpendingLog log : logs) {
                    totalSpent += log.getAmount();
                }
            }
            updateProgressBar();
        });

        binding.button1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getInformation();
                insertSpendingRecord();
            }
        });

        binding.button2.setOnClickListener(v ->
                startActivity(SeeMonthActivity.seeAllMonthsActivityIntentFactory(getApplicationContext(), loggedInID)));

        binding.button3.setOnClickListener(v ->
                startActivity(DataViewActivity.dataViewActivityIntentFactory(getApplicationContext(), loggedInID)));

        binding.spendingViewBackButton.setOnClickListener(v ->
                startActivity(LandingActivity.landingActivityIntentFactory(getApplicationContext(), loggedInID)));
    }

    private void insertSpendingRecord() {
        if (localName.isEmpty()) return;
        SpendingLog log = new SpendingLog(loggedInID, localCost, localName);
        repository.insertSpendingLog(log);
        binding.amountInputEditText.setText("");
        binding.nameInputEditText.setText("");
    }

    private void getInformation() {
        localName = binding.nameInputEditText.getText().toString();
        try {
            localCost = Double.parseDouble(binding.amountInputEditText.getText().toString());
        } catch (NumberFormatException e) {
            Log.d("DataView", "Error in reading cost text");
        }
    }

    private void updateProgressBar() {
        if (budgetGoal <= 0) {
            binding.spendingProgressBar.setProgress(0);
            binding.spendingStatusTextView.setText("No budget set. Go to Budgeting to set one.");
            return;
        }

        int percent = (int) Math.min((totalSpent / budgetGoal) * 100, 100);
        binding.spendingProgressBar.setProgress(percent);

        double remaining = budgetGoal - totalSpent;
        if (remaining >= 0) {
            binding.spendingStatusTextView.setText(
                    "Spent: $" + String.format("%.2f", totalSpent) +
                            " / $" + String.format("%.2f", budgetGoal) +
                            "  (" + percent + "%)"
            );
        } else {
            binding.spendingStatusTextView.setText(
                    "Over budget by $" + String.format("%.2f", Math.abs(remaining)) + "!"
            );
        }
    }

    static Intent spendingViewActivityIntentFactory(Context context, int userID) {
        Intent intent = new Intent(context, SpendingActivity.class);
        intent.putExtra(SPENDING_VIEW_ACTIVITY_USERID, userID);
        return intent;
    }
}
