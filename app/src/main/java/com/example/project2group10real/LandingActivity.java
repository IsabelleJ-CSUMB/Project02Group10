package com.example.project2group10real;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;

import androidx.activity.EdgeToEdge;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.LiveData;

import com.example.project2group10real.database.UltimateBudgetingRepository;
import com.example.project2group10real.database.entities.User;
import com.example.project2group10real.databinding.ActivityLandingBinding;

public class LandingActivity extends AppCompatActivity {
    private ActivityLandingBinding binding;
    public static final String TAG = "PROJECT2GROUP10_ULTIMATE_BUDGETING";
    public static final String LANDING_ACTIVITY_USERID = "LANDING_ACTIVITY_USERID";

    private int loggedInID = -1;
    private boolean loginStatus = false;
    private User user;
    private boolean adminStatus = false;

    private UltimateBudgetingRepository repository;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityLandingBinding.inflate(getLayoutInflater());
        EdgeToEdge.enable(this);
        setContentView(binding.getRoot());

        repository = UltimateBudgetingRepository.getRepository(getApplication());

        loginUser(savedInstanceState);

        if (loggedInID == -1) {
            startActivity(LoginActivity.loginIntentFactory(getApplicationContext()));
            finish();
            return;
        }

        binding.spendingButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(SpendingActivity.spendingViewActivityIntentFactory(getApplicationContext(), loggedInID));
            }
        });

        binding.budgetingButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(BudgetingActivity.budgetingActivityIntentFactory(getApplicationContext(), loggedInID));
            }

        });
        binding.deleteUsersButton.setOnClickListener(v ->
                startActivity(DeleteUsersActivity.deleteUsersIntentFactory(getApplicationContext(), loggedInID)));

        binding.viewDataButton.setOnClickListener(v ->
                startActivity(ViewDataActivity.viewDataIntentFactory(getApplicationContext(), loggedInID)));
    }

    static Intent landingActivityIntentFactory(Context context, int userID) {
        Intent intent = new Intent(context, LandingActivity.class);
        intent.putExtra(LANDING_ACTIVITY_USERID, userID);
        return intent;
    }

    @Override
    public boolean onPrepareOptionsMenu(Menu menu) {
        MenuItem item = menu.findItem(R.id.logOutMenuItem);
        item.setVisible(true);
        if (user == null) {
            return false;
        }
        item.setTitle(user.getUsername());
        item.setOnMenuItemClickListener(new MenuItem.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(@NonNull MenuItem menuItem) {
                showLogoutDialog();
                return false;
            }
        });
        return true;
    }

    private void showLogoutDialog() {
        AlertDialog.Builder alertBuilder = new AlertDialog.Builder(LandingActivity.this);
        final AlertDialog alertDialog = alertBuilder.create();
        alertBuilder.setMessage("Logout?");
        alertBuilder.setPositiveButton("Logout", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                logout();
            }
        });
        alertBuilder.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                alertDialog.dismiss();
            }
        });
        alertBuilder.create().show();
    }

    private void logout() {
        SharedPreferences sharedPreferences = getApplicationContext().getSharedPreferences("UserPrefs", Context.MODE_PRIVATE);
        sharedPreferences.edit()
                .putBoolean("isLoggedIn", false)
                .putInt("loggedInUserID", -1)
                .apply();
        startActivity(MainActivity.mainIntentFactory(getApplicationContext()));
        finish();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.logout_menu, menu);
        return true;
    }

    private void loginUser(Bundle savedInstanceState) {
        SharedPreferences sharedPreferences = getSharedPreferences("UserPrefs", Context.MODE_PRIVATE);
        loginStatus = sharedPreferences.getBoolean("isLoggedIn", false);
        if (!loginStatus) {
            return;
        }

        loggedInID = getIntent().getIntExtra(LANDING_ACTIVITY_USERID, -1);
        if (loggedInID == -1) {
            loggedInID = sharedPreferences.getInt("loggedInUserID", -1);
        }

        LiveData<User> userObserver = repository.getUserByUserID(loggedInID);
        userObserver.observe(this, user -> {
            this.user = user;
            if (user != null) {
                adminStatus = user.isAdmin();
                invalidateOptionsMenu();
                if (adminStatus) {
                    binding.deleteUsersButton.setVisibility(View.VISIBLE);
                    binding.viewDataButton.setVisibility(View.VISIBLE);
                } else {
                    binding.deleteUsersButton.setVisibility(View.GONE);
                    binding.viewDataButton.setVisibility(View.GONE);
                }
            }
        });
    }
}