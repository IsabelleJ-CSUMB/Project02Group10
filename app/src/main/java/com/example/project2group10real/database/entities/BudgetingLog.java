package com.example.project2group10real.database.entities;

import androidx.room.Entity;
import androidx.room.Ignore;
import androidx.room.PrimaryKey;

import com.example.project2group10real.database.UltimateBudgetingDatabase;

import java.util.Objects;

@Entity(tableName = UltimateBudgetingDatabase.BUDGETING_TABLE)
public class BudgetingLog {
    @PrimaryKey(autoGenerate = true)
    private int id;
    private int userID;
    private int amount;
    private String category;

    @Override
    public boolean equals(Object o) {
        if (o == null || getClass() != o.getClass()) return false;
        BudgetingLog that = (BudgetingLog) o;
        return id == that.id && userID == that.userID && amount == that.amount && Objects.equals(category, that.category);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, userID, amount, category);
    }

    @Ignore
    public BudgetingLog(int userID, int amount, String category) {
        this.userID = userID;
        this.amount = amount;
        this.category = category;
    }

    public BudgetingLog() {

    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getUserID() {
        return userID;
    }

    public void setUserID(int userID) {
        this.userID = userID;
    }

    public int getAmount() {
        return amount;
    }

    public void setAmount(int amount) {
        this.amount = amount;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }
}
