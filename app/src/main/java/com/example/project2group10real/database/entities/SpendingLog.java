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
    private double amount;
    private String spendingName;

    @Override
    public boolean equals(Object o) {
        if (o == null || getClass() != o.getClass()) return false;
        SpendingLog that = (SpendingLog) o;
        return id == that.id && userID == that.userID && amount == that.amount && Objects.equals(spendingName, that.spendingName);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, userID, amount, spendingName);
    }

    public SpendingLog() {}

    @Ignore
    public SpendingLog(int userID, double amount, String spendingName) {
        this.userID = userID;
        this.amount = amount;
        this.spendingName = spendingName;
    }

    public int getId() { return id; }
    public void setId(int id) { this.id = id; }

    public int getUserID() { return userID; }
    public void setUserID(int userID) { this.userID = userID; }

    public double getAmount() { return amount; }
    public void setAmount(double amount) { this.amount = amount; }

    public String getSpendingName() { return spendingName; }
    public void setSpendingName(String spendingName) { this.spendingName = spendingName; }

    @Override
    public String toString() {
        return "SpendingLog{" +
                "amount=" + amount +
                ", spendingName='" + spendingName + '\'' +
                '}';
    }
}