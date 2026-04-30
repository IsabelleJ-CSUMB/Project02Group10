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
import com.example.project2group10real.databinding.ActivityMainBinding;

public class LandingActivity extends AppCompatActivity {
    private ActivityLandingBinding binding;
     public static final String TAG = "PROJECT2GROUP10_ULTIMATE_BUDGETING";
    public static final String LANDING_ACTIVITY_USERID = "LANDING_ACTIVITY_USERID";

    private int loggedInID;
    public static final int LOG_OUT = -1;
    private User user;
    private UltimateBudgetingRepository repository;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityLandingBinding.inflate(getLayoutInflater());
        EdgeToEdge.enable(this);
        setContentView(binding.getRoot());

        repository = UltimateBudgetingRepository.getRepository(getApplication());

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

    @Override
    public boolean onPrepareOptionsMenu(Menu menu) {
        MenuItem item = menu.findItem(R.id.logOutMenuItem);
        item.setVisible(true);
        if(user == null) {
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
        //instantiates memory for the alert dialog, prevents issues with multiples
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
        loggedInID = LOG_OUT;
        updateSharedPreference();
        getIntent().putExtra(MAIN_ACTIVITY_USER_ID, loggedInID);
        startActivity(LoginActivity.loginIntentFactory(getApplicationContext()));
    }

    private void updateSharedPreference() {
        SharedPreferences sharedPreferences = getApplicationContext().getSharedPreferences(getString(R.string.preference_file_key), Context.MODE_PRIVATE);
        SharedPreferences.Editor sharedPreferencesEditor = sharedPreferences.edit();
        sharedPreferencesEditor.putInt(getString(R.string.preference_userid_key),loggedInID);
        sharedPreferencesEditor.apply();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.logout_menu,menu);
        return true;
    }

    private void loginUser(Bundle savedInstanceState) {
//        //check shared preference for logged in user
//        SharedPreferences sharedPreferences = getSharedPreferences(getString(R.string.preference_file_key), Context.MODE_PRIVATE);
//        loggedInID = sharedPreferences.getInt(getString(R.string.preference_userid_key), LOG_OUT);
//
//        if (loggedInID == LOG_OUT && savedInstanceState != null && savedInstanceState.containsKey(SAVED_INSTANCE_STATE_USERID_KEY)) {
//            loggedInID = savedInstanceState.getInt(SAVED_INSTANCE_STATE_USERID_KEY, LOG_OUT);
//        }
//        if (loggedInID == LOG_OUT) {
//            loggedInID = getIntent().getIntExtra(MAIN_ACTIVITY_USER_ID, LOG_OUT);
//        }
//        if(loggedInID == LOG_OUT) {
//            return;
//        }
//        LiveData<User> userObserver = repository.getUserByUserID(loggedInID);
//        userObserver.observe(this, user -> {
//            this.user = user;
//            if (user != null) {
//                invalidateOptionsMenu();
//            }
//        });
    }




}