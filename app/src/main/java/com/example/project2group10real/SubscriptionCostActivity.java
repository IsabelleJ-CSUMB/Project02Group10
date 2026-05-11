package com.example.project2group10real;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.project2group10real.database.UltimateBudgetingRepository;
import com.example.project2group10real.databinding.ActivitySubscriptionCostBinding;
import com.example.project2group10real.viewHolders.SpendingLogAdapter;

import java.util.Collections;

public class SubscriptionCostActivity extends AppCompatActivity {
    private static final String SUBSCRIPTION_ACTIVITY_USERID = "SUBSCRIPTION_ACTIVITY_USERID";
    private ActivitySubscriptionCostBinding binding;
    private UltimateBudgetingRepository repository;
    private int loggedInID;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivitySubscriptionCostBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        repository = UltimateBudgetingRepository.getRepository(getApplication());
        loggedInID = getIntent().getIntExtra(SUBSCRIPTION_ACTIVITY_USERID, -1);

        RecyclerView recyclerView = binding.subscriptionRecyclerView;
        final SpendingLogAdapter adapter = new SpendingLogAdapter(new SpendingLogAdapter.SpendingLogDiff());
        recyclerView.setAdapter(adapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        repository.getRecurringBillsByUserID(loggedInID).observe(this, recurringBills -> {
            if (recurringBills != null) {
                adapter.submitList(Collections.unmodifiableList(recurringBills.stream()
                        .map(bill -> new com.example.project2group10real.database.entities.SpendingLog(
                                loggedInID, bill.getBillAmount(), bill.getBillName()))
                        .toList()));
            }
        });

        binding.subscriptionBackButton.setOnClickListener(v -> {
            startActivity(BudgetingActivity.budgetingActivityIntentFactory(getApplicationContext(), loggedInID));
        });
    }

    static Intent subscriptionCostActivityIntentFactory(Context context, int userID) {
        Intent intent = new Intent(context, SubscriptionCostActivity.class);
        intent.putExtra(SUBSCRIPTION_ACTIVITY_USERID, userID);
        return intent;
    }
}