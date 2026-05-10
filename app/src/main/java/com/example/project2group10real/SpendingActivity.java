package com.example.project2group10real;

import android.content.Context;
import android.content.Intent;
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

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        binding = ActivitySpendingBinding.inflate(getLayoutInflater());
        EdgeToEdge.enable(this);
        setContentView(binding.getRoot());

        repository = UltimateBudgetingRepository.getRepository(getApplication());
        loggedInID = getIntent().getIntExtra(SPENDING_VIEW_ACTIVITY_USERID, -1);
        // Button 1 → Log page
        binding.button1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getInformation();
                insertSpendingRecord();
            }
    });

        // Button 2 → See all months
        binding.button2.setOnClickListener(v -> {
            startActivity(SeeMonthActivity.seeAllMonthsActivityIntentFactory(getApplicationContext(), loggedInID));
        });

        // Button 3 → Data page
        binding.button3.setOnClickListener(v -> {
            startActivity(DataViewActivity.dataViewActivityIntentFactory(getApplicationContext(), loggedInID));
        });
    }

    private void insertSpendingRecord() {
        if (localName.isEmpty()) {
            return;
        }
        SpendingLog log = new SpendingLog(loggedInID, localCost, localName);
        repository.insertSpendingLog(log);
    }

    private void getInformation() {
        localName = binding.nameInputEditText.getText().toString();
        try {
            localCost = Double.parseDouble(binding.amountInputEditText.getText().toString());
        } catch (NumberFormatException e) {
            Log.d("DataView", "Error in reading cost text");
        }
    }

    static Intent spendingViewActivityIntentFactory(Context context, int userID) {
        Intent intent = new Intent(context, SpendingActivity.class);
        intent.putExtra(SPENDING_VIEW_ACTIVITY_USERID, userID);
        return intent;
    }
}