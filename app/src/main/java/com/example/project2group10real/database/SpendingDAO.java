package com.example.project2group10real.database;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;

import com.example.project2group10real.database.entities.BudgetingLog;
import com.example.project2group10real.database.entities.SpendingLog;

import java.util.List;

@Dao
public interface SpendingDAO {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insert(SpendingLog spendingLog);

    @Query("SELECT * FROM " + UltimateBudgetingDatabase.SPENDING_TABLE)
    List<SpendingLog> getAllRecords();

    @Query("SELECT * FROM " + UltimateBudgetingDatabase.SPENDING_TABLE + " WHERE userID = :userID ORDER BY id DESC")
    LiveData<List<SpendingLog>> getAllLogsByUserID(int userID);

    @Delete
    void delete(BudgetingLog budgetingLog);
}
