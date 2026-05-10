package com.example.project2group10real.database;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;

import com.example.project2group10real.database.entities.RecurringBill;

import java.util.List;

@Dao
public interface RecurringBillDAO {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insert(RecurringBill... bills);

    @Delete
    void delete(RecurringBill bill);

    @Query("SELECT * FROM " + UltimateBudgetingDatabase.RECURRING_BILL_TABLE + " WHERE userID = :userID")
    LiveData<List<RecurringBill>> getBillsByUserID(int userID);
}