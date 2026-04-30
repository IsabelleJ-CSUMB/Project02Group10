package com.example.project2group10real.database;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;

import com.example.project2group10real.database.entities.BudgetingLog;

import java.util.List;

@Dao
public interface BudgetingDAO {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insert(BudgetingLog...budgetingLog);

    @Delete
    void delete(BudgetingLog budgetingLog);

    @Query("DELETE from " + UltimateBudgetingDatabase.BUDGETING_TABLE) void deleteAll();

    @Query("SELECT * FROM " + UltimateBudgetingDatabase.BUDGETING_TABLE + " WHERE userID = :userID")
    LiveData<List<BudgetingLog>> getAllLogsByUserIDLiveData(int userID);





}
