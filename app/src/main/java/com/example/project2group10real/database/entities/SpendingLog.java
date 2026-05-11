package com.example.project2group10real.database.entities;

import androidx.room.Entity;
import androidx.room.Ignore;
import androidx.room.PrimaryKey;

import com.example.project2group10real.database.UltimateBudgetingDatabase;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;
import java.util.Objects;

@Entity(tableName = UltimateBudgetingDatabase.SPENDING_TABLE)
public class SpendingLog {

    @PrimaryKey(autoGenerate = true)
    private int id;
    private int userID;
    private double amount;
    private String spendingName;
    private String date;

    @Override
    public boolean equals(Object o) {
        if (o == null || getClass() != o.getClass()) return false;
        SpendingLog that = (SpendingLog) o;
        return id == that.id && userID == that.userID && Double.compare(amount, that.amount) == 0
                && Objects.equals(spendingName, that.spendingName)
                && Objects.equals(date, that.date);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, userID, amount, spendingName, date);
    }

    public SpendingLog() {}

    @Ignore
    public SpendingLog(int userID, double amount, String spendingName) {
        this.userID = userID;
        this.amount = amount;
        this.spendingName = spendingName;
        this.date = new SimpleDateFormat("yyyy-MM-dd HH:mm", Locale.US).format(new Date());
    }

    @Ignore
    public SpendingLog(int userID, double amount, String spendingName, String date) {
        this.userID = userID;
        this.amount = amount;
        this.spendingName = spendingName;
        this.date = date;
    }

    public int getId() { return id; }
    public void setId(int id) { this.id = id; }

    public int getUserID() { return userID; }
    public void setUserID(int userID) { this.userID = userID; }

    public double getAmount() { return amount; }
    public void setAmount(double amount) { this.amount = amount; }

    public String getSpendingName() { return spendingName; }
    public void setSpendingName(String spendingName) { this.spendingName = spendingName; }

    public String getDate() { return date; }
    public void setDate(String date) { this.date = date; }

    @Override
    public String toString() {
        return "SpendingLog{" +
                "id=" + id +
                ", userID=" + userID +
                ", amount=" + amount +
                ", spendingName='" + spendingName + '\'' +
                ", date=" + date +
                '}';
    }
}