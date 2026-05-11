package com.example.project2group10real;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;

public class AdminActivity extends AppCompatActivity {

    private static final String ADMIN_ACTIVITY_USERID = "ADMIN_ACTIVITY_USERID";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin);
    }

    static Intent adminIntentFactory(Context context, int userID) {
        Intent intent = new Intent(context, AdminActivity.class);
        intent.putExtra(ADMIN_ACTIVITY_USERID, userID);
        return intent;
    }
}