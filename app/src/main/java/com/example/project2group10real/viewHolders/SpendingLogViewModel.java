package com.example.project2group10real.viewHolders;

import android.app.Application;

import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.Transformations;

import com.example.project2group10real.database.UltimateBudgetingRepository;
import com.example.project2group10real.database.entities.SpendingLog;

import java.util.ArrayList;
import java.util.Calendar;
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

    public LiveData<List<SpendingLog>> getAllLogsByIDCurrentMonth(int userID, long dateMillis) {
        Calendar cal = Calendar.getInstance();
        cal.setTimeInMillis(dateMillis);
        int targetMonth = cal.get(Calendar.MONTH);
        int targetYear = cal.get(Calendar.YEAR);

        return Transformations.map(repository.getAllSpendingLogsByUserID(userID), logs -> {
            List<SpendingLog> filtered = new ArrayList<>();
            if (logs == null) return filtered;
            for (SpendingLog log : logs) {
                if (log.getDate() != null && !log.getDate().isEmpty()) {
                    try {
                        String[] parts = log.getDate().split("-");
                        int year = Integer.parseInt(parts[0]);
                        int month = Integer.parseInt(parts[1]) - 1; // Calendar months are 0-based
                        if (year == targetYear && month == targetMonth) {
                            filtered.add(log);
                        }
                    } catch (Exception e) {
                    }
                }
            }
            return filtered;
        });
    }

    public void insert(SpendingLog log) {
        repository.insertSpendingLog(log);
    }
}