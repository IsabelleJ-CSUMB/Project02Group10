package com.example.project2group10real;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import com.example.project2group10real.database.UltimateBudgetingRepository;
import com.example.project2group10real.database.entities.User;

import java.util.ArrayList;
import java.util.List;

public class DeleteUsersActivity extends AppCompatActivity {

    private static final String DELETE_USERS_USERID = "DELETE_USERS_USERID";

    private ListView userListView;
    private UltimateBudgetingRepository repository;
    private List<User> userList = new ArrayList<>();
    private User selectedUser = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_delete_users);

        repository = UltimateBudgetingRepository.getRepository(getApplication());

        userListView = findViewById(R.id.userListView);

        // Load all users from the database
        repository.getAllUsers().observe(this, users -> {
            userList = users;
            List<String> usernames = new ArrayList<>();
            for (User u : users) {
                usernames.add(u.getUsername());
            }
            ArrayAdapter<String> adapter = new ArrayAdapter<>(this,
                    android.R.layout.simple_list_item_single_choice, usernames);
            userListView.setAdapter(adapter);
            userListView.setChoiceMode(ListView.CHOICE_MODE_SINGLE);
        });

        // Highlight the selected user
        userListView.setOnItemClickListener((parent, view, position, id) -> {
            selectedUser = userList.get(position);
        });

        // Delete button
        findViewById(R.id.deleteSelectedUserButton).setOnClickListener(v -> {
            if (selectedUser == null) {
                Toast.makeText(this, "Please select a user first", Toast.LENGTH_SHORT).show();
                return;
            }
            // Confirm before deleting
            new AlertDialog.Builder(this)
                    .setMessage("Delete user: " + selectedUser.getUsername() + "?")
                    .setPositiveButton("Delete", (dialog, which) -> {
                        repository.deleteUser(selectedUser);
                        Toast.makeText(this, "User deleted", Toast.LENGTH_SHORT).show();
                        selectedUser = null;
                    })
                    .setNegativeButton("Cancel", null)
                    .show();
        });
    }

    static Intent deleteUsersIntentFactory(Context context, int userID) {
        Intent intent = new Intent(context, DeleteUsersActivity.class);
        intent.putExtra(DELETE_USERS_USERID, userID);
        return intent;
    }
}