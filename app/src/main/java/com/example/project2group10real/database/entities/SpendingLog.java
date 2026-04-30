package com.example.project2group10real.database.entities;

import androidx.room.Entity;
import androidx.room.Ignore;
import androidx.room.PrimaryKey;

import com.example.project2group10real.database.UltimateBudgetingDatabase;

import java.util.Objects;

@Entity(tableName = UltimateBudgetingDatabase.SPENDING_TABLE)
public class SpendingLog {
    @PrimaryKey(autoGenerate = true)
    private int id;
    private int userID;
    private int amount;
    private String category;

    @Override
    public boolean equals(Object o) {
        if (o == null || getClass() != o.getClass()) return false;
        SpendingLog that = (SpendingLog) o;
        return id == that.id && userID == that.userID && amount == that.amount && Objects.equals(category, that.category);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, userID, amount, category);
    }

    public SpendingLog() {

    }

    @Ignore
    public SpendingLog(int userID, int amount, String category) {
        this.userID = userID;
        this.amount = amount;
        this.category = category;
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
