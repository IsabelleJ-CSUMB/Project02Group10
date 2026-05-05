package com.example.project2group10real;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.project2group10real.database.UltimateBudgetingRepository;
import com.example.project2group10real.databinding.ActivityDataViewBinding;
import com.example.project2group10real.viewHolders.SpendingLogAdapter;
import com.example.project2group10real.viewHolders.SpendingLogViewModel;

import java.util.Collections;

public class DataViewActivity extends AppCompatActivity {

    private static final String DATA_VIEW_ACTIVITY_USERID = "DATA_VIEW_ACTIVITY_USERID";
    private ActivityDataViewBinding binding;
    private SpendingLogViewModel model;
    private UltimateBudgetingRepository repository;
    private int loggedInID;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityDataViewBinding.inflate(getLayoutInflater());
        EdgeToEdge.enable(this);
        setContentView(binding.getRoot());

        model = new ViewModelProvider(this).get(SpendingLogViewModel.class);

        repository = UltimateBudgetingRepository.getRepository(getApplication());
        loggedInID = getIntent().getIntExtra(DATA_VIEW_ACTIVITY_USERID, -1);
        RecyclerView recyclerView = binding.logDisplayRecyclerView;
        final SpendingLogAdapter adapter = new SpendingLogAdapter(new SpendingLogAdapter.SpendingLogDiff());
        recyclerView.setAdapter(adapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        model.getAllLogsByID(loggedInID).observe(this, spendingLogs -> {

            adapter.submitList(Collections.unmodifiableList(spendingLogs));
        });
    }


    static Intent dataViewActivityIntentFactory(Context context, int userID) {
        Intent intent = new Intent(context, DataViewActivity.class);
        intent.putExtra(DATA_VIEW_ACTIVITY_USERID, userID);
        return intent;
    }
}