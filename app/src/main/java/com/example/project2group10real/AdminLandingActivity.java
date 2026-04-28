package com.example.project2group10real;

import android.os.Bundle;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

public class AdminLandingActivity extends AppCompatActivity {

    /*This should be similar in layout to LandingActivity with changes for being an admin. Copy the
      layout from activity_landing.xml and add admin only components**/

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin_landing);
    }
}