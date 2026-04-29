package com.example.project2group10real.database.entities;

import androidx.room.Entity;
import androidx.room.PrimaryKey;

import com.example.project2group10real.database.UltimateBudgetingDatabase;

@Entity(tableName = UltimateBudgetingDatabase.BUDGETING_TABLE)
public class BudgetingLog {
    @PrimaryKey(autoGenerate = true)
    private int id;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }
}
