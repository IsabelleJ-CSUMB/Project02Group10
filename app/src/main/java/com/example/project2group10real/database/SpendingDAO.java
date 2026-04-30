package com.example.project2group10real.database;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;

import com.example.project2group10real.database.entities.SpendingLog;

import java.util.ArrayList;
import java.util.List;

@Dao
public interface SpendingDAO {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insert(SpendingLog...spendingLog);
    @Query("SELECT * FROM " + UltimateBudgetingDatabase.SPENDING_TABLE + " WHERE userID = :userID")
    LiveData<List<SpendingLog>> getAllLogsByUserIDLiveData(int userID);

    @Query("SELECT * FROM " + UltimateBudgetingDatabase.SPENDING_TABLE)
    ArrayList<SpendingLog> getAllRecords();

    @Query("DELETE from " + UltimateBudgetingDatabase.SPENDING_TABLE) void deleteAll();

}
