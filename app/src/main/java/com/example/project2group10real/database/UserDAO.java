package com.example.project2group10real.database;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;

import com.example.project2group10real.database.entities.User;

import java.util.List;

@Dao
public interface UserDAO {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insert(User...user);

    @Delete
    void delete(User user);

    @Query("SELECT * from " + UltimateBudgetingDatabase.USER_TABLE + " ORDER BY username")
    LiveData<List<User>> getAllUsers();
    @Query("DELETE from " + UltimateBudgetingDatabase.USER_TABLE) void deleteAll();

    @Query("SELECT * FROM " + UltimateBudgetingDatabase.USER_TABLE + " WHERE username = :username")
    LiveData<User> getUserByUsername(String username);

    @Query("SELECT * FROM " + UltimateBudgetingDatabase.USER_TABLE + " WHERE user_id = :userID")
    LiveData<User> getUserByUserID(int userID);

}
