package com.example.project2group10real.database;

import android.content.Context;
import android.util.Log;

import androidx.annotation.NonNull;
import androidx.room.Room;
import androidx.room.RoomDatabase;
import androidx.sqlite.db.SupportSQLiteDatabase;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public abstract class UltimateBudgetingDatabase extends RoomDatabase {

    public static final String DATABASE_NAME = "ULTIMATE_BUDGETING_DATABASE";
    public static final String USER_TABLE = "USER_TABLE";
    public static final String SPENDING_TABLE = "SPENDING_TABLE";
    public static final String BUDGETING_TABLE = "BUDGETING_TABLE";

    private static volatile UltimateBudgetingDatabase INSTANCE;
    private static final int NUMBER_OF_THREADS = 4;

    //means database only uses max of 4 threads
    static final ExecutorService databaseWriteExecutor = Executors.newFixedThreadPool(NUMBER_OF_THREADS);
    static UltimateBudgetingDatabase getDatabase(final Context context) {
        if(INSTANCE == null) {
            synchronized (UltimateBudgetingDatabase.class) {
                if(INSTANCE == null) {
                    INSTANCE = Room.databaseBuilder(context.getApplicationContext(), UltimateBudgetingDatabase.class, DATABASE_NAME)
                            .fallbackToDestructiveMigration().addCallback(addDefaultValues).build();
                }
            }
        }
        return INSTANCE;
    }

    private static final RoomDatabase.Callback addDefaultValues = new RoomDatabase.Callback() {
        @Override
        public void onCreate(@NonNull SupportSQLiteDatabase db) {
            super.onCreate(db);
            databaseWriteExecutor.execute(() ->
            {
                UserDAO dao = INSTANCE.userDAO();
                dao.deleteAll();
                User admin = new User("testAdmin", "admin");
                admin.setAdmin(true);
                dao.insert(admin);

                User testUser1 = new User("testUser1", "testUser1");
                dao.insert(testUser1);
            });
        }
    };

    public abstract SpendingDAO spendingDao();

    public abstract UserDAO userDAO();

    public abstract BudgetingDAO budgetingDAO();

}
