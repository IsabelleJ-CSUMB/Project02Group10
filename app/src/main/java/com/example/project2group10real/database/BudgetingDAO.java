package com.example.project2group10real.database;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;

import com.example.project2group10real.database.entities.BudgetingLog;

@Dao
public interface BudgetingDAO {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insert(BudgetingLog...budgetingLog);

    @Delete
    void delete(BudgetingLog budgetingLog);


}
