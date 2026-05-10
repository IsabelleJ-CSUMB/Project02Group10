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
    private int goal;

    @Override
    public boolean equals(Object o) {
        if (o == null || getClass() != o.getClass()) return false;
        BudgetingLog that = (BudgetingLog) o;
        return id == that.id && userID == that.userID && amount == that.amount && goal == that.goal;
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, userID, amount, goal);
    }

    @Ignore
    public BudgetingLog(int userID, int amount, int goal) {
        this.userID = userID;
        this.amount = amount;
        this.goal = goal;
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

    public int getGoal() {
        return goal;
    }

    public void setGoal(int goal) {
        this.goal = goal;
    }

    public void setCategory(int goal) {
        this.goal = this.goal;
    }
}