package com.example.project2group10real;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.project2group10real.database.UltimateBudgetingRepository;
import com.example.project2group10real.databinding.ActivitySeeAllMonthsBinding;
import com.example.project2group10real.viewHolders.SpendingLogAdapter;
import com.example.project2group10real.viewHolders.SpendingLogViewModel;

import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.util.Collections;

public class SeeMonthActivity extends AppCompatActivity {
    private static final String SEE_ALL_MONTHS_VIEW_ACTIVITY_USERID = "SEE_ALL_MONTHS_VIEW_ACTIVITY_USERID";
    private SpendingLogViewModel model;
    private UltimateBudgetingRepository repository;
    private int loggedInID;
    private LocalDateTime date;
    private ActivitySeeAllMonthsBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivitySeeAllMonthsBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        date = LocalDateTime.now();

        model = new ViewModelProvider(this).get(SpendingLogViewModel.class);

        repository = UltimateBudgetingRepository.getRepository(getApplication());
        loggedInID = getIntent().getIntExtra(SEE_ALL_MONTHS_VIEW_ACTIVITY_USERID, -1);
        RecyclerView recyclerView = binding.monthLogDisplayRecyclerView;
        final SpendingLogAdapter adapter = new SpendingLogAdapter(new SpendingLogAdapter.SpendingLogDiff());
        recyclerView.setAdapter(adapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        model.getAllLogsByIDCurrentMonth(loggedInID, convertDateToLong(date)).observe(this, spendingLogs -> {

            adapter.submitList(Collections.unmodifiableList(spendingLogs));
        });
    }

    static Intent seeAllMonthsActivityIntentFactory(Context context, int userID) {
        Intent intent = new Intent(context, SeeMonthActivity.class);
        intent.putExtra(SEE_ALL_MONTHS_VIEW_ACTIVITY_USERID, userID);
        return intent;
    }
    public static long convertDateToLong(LocalDateTime date) {
        //.of = object factory
        ZonedDateTime zdt = ZonedDateTime.of(date, ZoneId.systemDefault());
        return zdt.toInstant().toEpochMilli();
    }
}
