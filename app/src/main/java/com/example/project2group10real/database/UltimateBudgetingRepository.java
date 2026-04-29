package com.example.project2group10real.database;

import android.app.Application;
import android.util.Log;

import androidx.lifecycle.LiveData;

import com.example.project2group10real.LandingActivity;
import com.example.project2group10real.MainActivity;
import com.example.project2group10real.database.entities.BudgetingLog;
import com.example.project2group10real.database.entities.SpendingLog;
import com.example.project2group10real.database.entities.User;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.Future;

public class UltimateBudgetingRepository {
    private final SpendingDAO spendingDAO;
    private final UserDAO userDAO;
    private final BudgetingDAO budgetingDAO;

    private static UltimateBudgetingRepository repository;

    private UltimateBudgetingRepository(Application application) {
        UltimateBudgetingDatabase db = UltimateBudgetingDatabase.getDatabase(application);
        this.spendingDAO = db.spendingDao();
        this.userDAO = db.userDAO();
        this.budgetingDAO = db.budgetingDAO();
    }

    public static UltimateBudgetingRepository getRepository(Application application) {
        if (repository != null) {
            return repository;
        }
        Future<UltimateBudgetingRepository> future = UltimateBudgetingDatabase.databaseWriteExecutor.submit(
                new Callable<UltimateBudgetingRepository>() {
                    @Override
                    public UltimateBudgetingRepository call() throws Exception {
                        return new UltimateBudgetingRepository(application);
                    }
                }
        );
        try {
            return future.get();
        } catch (InterruptedException | ExecutionException e) {
            Log.i(LandingActivity.TAG, "Problem getting Repository, thread error");
        }
        return null;
    }

    public List<SpendingLog> getAllLogs() {
        Future<ArrayList<SpendingLog>> future  = UltimateBudgetingDatabase.databaseWriteExecutor.submit(
                new Callable<ArrayList<SpendingLog>>() {
                    @Override
                    public ArrayList<SpendingLog> call() throws Exception {
                        return (ArrayList<SpendingLog>) spendingDAO.getAllRecords();
                    }
                });
        try {
            return future.get();
        } catch (InterruptedException | ExecutionException e) {
            e.printStackTrace();
            Log.i(LandingActivity.TAG, "Problem when getting all SpendingLogs in the repo");
        }
        return null;
    }

    public void insertSpendingLog(SpendingLog spendingLog) {
        UltimateBudgetingDatabase.databaseWriteExecutor.execute(() ->
        {
            spendingDAO.insert(spendingLog);
        });
    }

    public void insertBudgetingLog(BudgetingLog budgetingLog) {
        UltimateBudgetingDatabase.databaseWriteExecutor.execute(() ->
        {
            budgetingDAO.insert(budgetingLog);
        });
    }

    public void insertUser(User... user) {
        UltimateBudgetingDatabase.databaseWriteExecutor.execute(() ->
        {
            userDAO.insert(user);
        }        );
    }

    public LiveData<User> getUserByUsername(String username) {
        return userDAO.getUserByUsername(username);
    }

    public LiveData<User> getUserByUserID(int userID) {
        return userDAO.getUserByUserID(userID);
    }

    public LiveData<List<SpendingLog>> getSpendingLogsByUserIDLiveData(int userID) {
        return spendingDAO.getAllLogsByUserIDLiveData(userID);
    }

    public LiveData<List<BudgetingLog>> getBudgetingLogsByUserIDLiveData(int userID) {
        return budgetingDAO.getAllLogsByUserIDLiveData(userID);
    }

}
