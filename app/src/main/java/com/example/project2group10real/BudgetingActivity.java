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
import com.example.project2group10real.database.entities.BudgetingLog;
import com.example.project2group10real.database.entities.RecurringBill;
import com.example.project2group10real.databinding.ActivityBudgetingBinding;

import java.util.List;

public class BudgetingActivity extends AppCompatActivity {

    private static final String BUDGETING_ACTIVITY_USERID = "BUDGETING_ACTIVITY_USERID";
    private static final String PREFS_NAME = "BudgetingPrefs";
    private static final String KEY_GOAL = "budget_goal_";

    private ActivityBudgetingBinding binding;
    private UltimateBudgetingRepository repository;
    private int loggedInID;

    private double budgetGoal = 0;
    private double totalBills = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        binding = ActivityBudgetingBinding.inflate(getLayoutInflater());
        EdgeToEdge.enable(this);
        setContentView(binding.getRoot());

        repository = UltimateBudgetingRepository.getRepository(getApplication());
        loggedInID = getIntent().getIntExtra(BUDGETING_ACTIVITY_USERID, -1);

        SharedPreferences prefs = getSharedPreferences(PREFS_NAME, Context.MODE_PRIVATE);
        budgetGoal = prefs.getFloat(KEY_GOAL + loggedInID, 0f);
        updateStatus();

        repository.getRecurringBillsByUserID(loggedInID).observe(this, bills -> {
            if (bills != null) {
                totalBills = 0;
                StringBuilder sb = new StringBuilder();
                for (RecurringBill bill : bills) {
                    totalBills += bill.getBillAmount();
                    sb.append(bill.getBillName())
                            .append(" (recurring): $")
                            .append(String.format("%.2f", bill.getBillAmount()))
                            .append("\n");
                }
                binding.billsListTextView.setText(sb.toString());
                updateStatus();
            }
        });

        binding.setBudgetButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                setBudget();
            }
        });

        // Add Recurring Bill button
        binding.addBillButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                addRecurringBill();
            }
        });
    }

    private void setBudget() {
        if (binding.budgetGoalEditText.getVisibility() == View.GONE) {
            binding.budgetGoalEditText.setVisibility(View.VISIBLE);
            binding.setBudgetButton.setText("Set Budget");
            return;
        }
        String raw = binding.budgetGoalEditText.getText().toString();
        if (raw.isEmpty()) return;

        try {
            budgetGoal = Double.parseDouble(raw);
        } catch (NumberFormatException e) {
            Log.d("Budgeting", "Error reading budget amount");
            return;
        }

        binding.budgetGoalEditText.setText("");
        SharedPreferences prefs = getSharedPreferences(PREFS_NAME, Context.MODE_PRIVATE);
        prefs.edit().putFloat(KEY_GOAL + loggedInID, (float) budgetGoal).apply();
        updateStatus();

        BudgetingLog log = new BudgetingLog(loggedInID, (int) totalBills, (int) budgetGoal);
        repository.insertBudgetingLog(log);
    }

    private void addRecurringBill() {
        String name = binding.billNameEditText.getText().toString();
        String amtRaw = binding.billAmountEditText.getText().toString();

        if (name.isEmpty() || amtRaw.isEmpty()) return;

        double amount;
        try {
            amount = Double.parseDouble(amtRaw);
        } catch (NumberFormatException e) {
            Log.d("Budgeting", "Error reading bill amount");
            return;
        }

        RecurringBill bill = new RecurringBill(loggedInID, name, amount);
        repository.insertRecurringBill(bill);

        binding.billNameEditText.setText("");
        binding.billAmountEditText.setText("");
    }

    private void updateStatus() {
        if (budgetGoal > 0) {
            binding.budgetGoalEditText.setVisibility(View.GONE);
            binding.setBudgetButton.setText("Change Budget");
        } else {
            binding.budgetGoalEditText.setVisibility(View.VISIBLE);
            binding.setBudgetButton.setText("Set Budget");
            binding.budgetProgressBar.setProgress(0);
            binding.budgetStatusTextView.setText("Set a budget to get started.");
            return;
        }

        int percent = (int) Math.min((totalBills / budgetGoal) * 100, 100);
        binding.budgetProgressBar.setProgress(percent);

        double remaining = budgetGoal - totalBills;
        if (remaining >= 0) {
            binding.budgetStatusTextView.setText(
                    "Budget: $" + String.format("%.2f", budgetGoal) +
                            "  |  Spent: $" + String.format("%.2f", totalBills) +
                            "  |  Left: $" + String.format("%.2f", remaining)
            );
        } else {
            binding.budgetStatusTextView.setText(
                    "Over budget by $" + String.format("%.2f", Math.abs(remaining)) + "!"
            );
        }
    }

    static Intent budgetingActivityIntentFactory(Context context, int userID) {
        Intent intent = new Intent(context, BudgetingActivity.class);
        intent.putExtra(BUDGETING_ACTIVITY_USERID, userID);
        return intent;
    }
}