package com.example.project2group10real.viewHolders;

import android.app.Application;

import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;

import com.example.project2group10real.database.UltimateBudgetingRepository;
import com.example.project2group10real.database.entities.SpendingLog;

import java.util.List;

public class SpendingLogViewModel extends AndroidViewModel {
    private final UltimateBudgetingRepository repository;


    public SpendingLogViewModel(Application application) {
        super(application);
        repository = UltimateBudgetingRepository.getRepository(application);
    }

    public LiveData<List<SpendingLog>> getAllLogsByID(int userID) {
        return repository.getAllSpendingLogsByUserID(userID);
    }

    public void insert(SpendingLog log) {
        repository.insertSpendingLog(log);
    }
}
