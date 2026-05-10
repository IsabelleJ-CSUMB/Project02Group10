package com.example.project2group10real.database;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;

import com.example.project2group10real.database.entities.SpendingLog;

import java.time.LocalDateTime;
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
    void delete(SpendingLog spendingLog);

    @Query("SELECT * FROM " + UltimateBudgetingDatabase.SPENDING_TABLE + " WHERE userID = :userID AND date/(1000 * 60 * 60 * 24)=:date / (1000 * 60 * 60 * 24)")
    LiveData<List<SpendingLog>> getAllLogsbyUserIDCurrentMonth(int userID, long date);
}
