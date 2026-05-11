package com.example.project2group10real;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import androidx.appcompat.app.AppCompatActivity;

import com.example.project2group10real.database.UltimateBudgetingRepository;
import com.example.project2group10real.database.entities.SpendingLog;
import com.example.project2group10real.database.entities.User;

import java.util.ArrayList;
import java.util.List;

public class ViewDataActivity extends AppCompatActivity {

    private static final String VIEW_DATA_USERID = "VIEW_DATA_USERID";

    private ListView viewDataListView;
    private UltimateBudgetingRepository repository;
    private List<User> userList = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_data);

        repository = UltimateBudgetingRepository.getRepository(getApplication());
        viewDataListView = findViewById(R.id.viewDataListView);

        // First load all users
        repository.getAllUsers().observe(this, users -> {
            userList = users;
            loadAllSpendingData();
        });
    }

    private void loadAllSpendingData() {
        List<String> displayList = new ArrayList<>();

        for (User user : userList) {
            repository.getAllSpendingLogsByUserID(user.getUser_id()).observe(this, logs -> {
                displayList.clear();
                for (User u : userList) {
                    displayList.add("--- " + u.getUsername() + " ---");
                    for (SpendingLog log : logs) {
                        if (log.getUserID() == u.getUser_id()) {
                            displayList.add("  $" + log.getAmount() + " - " + log.getSpendingName() + " (" + log.getDate() + ")");
                        }
                    }
                }
                ArrayAdapter<String> adapter = new ArrayAdapter<>(this,
                        android.R.layout.simple_list_item_1, displayList);
                viewDataListView.setAdapter(adapter);
            });
        }
    }

    static Intent viewDataIntentFactory(Context context, int userID) {
        Intent intent = new Intent(context, ViewDataActivity.class);
        intent.putExtra(VIEW_DATA_USERID, userID);
        return intent;
    }
}