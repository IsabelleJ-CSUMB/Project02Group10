package com.example.project2group10real.database.entities;

import androidx.room.Entity;
import androidx.room.Ignore;
import androidx.room.PrimaryKey;

import com.example.project2group10real.database.UltimateBudgetingDatabase;

@Entity(tableName = UltimateBudgetingDatabase.RECURRING_BILL_TABLE)
public class RecurringBill {

    @PrimaryKey(autoGenerate = true)
    private int id;
    private int userID;
    private String billName;
    private double billAmount;

    public RecurringBill() {}

    @Ignore
    public RecurringBill(int userID, String billName, double billAmount) {
        this.userID = userID;
        this.billName = billName;
        this.billAmount = billAmount;
    }

    public int getId() { return id; }
    public void setId(int id) { this.id = id; }

    public int getUserID() { return userID; }
    public void setUserID(int userID) { this.userID = userID; }

    public String getBillName() { return billName; }
    public void setBillName(String billName) { this.billName = billName; }

    public double getBillAmount() { return billAmount; }
    public void setBillAmount(double billAmount) { this.billAmount = billAmount; }
}