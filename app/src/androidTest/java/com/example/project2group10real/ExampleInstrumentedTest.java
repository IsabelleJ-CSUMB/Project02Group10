package com.example.project2group10real;

import android.content.Context;

import androidx.lifecycle.LiveData;
import androidx.room.Room;
import androidx.test.core.app.ApplicationProvider;
import androidx.test.platform.app.InstrumentationRegistry;
import androidx.test.ext.junit.runners.AndroidJUnit4;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;

import static org.junit.Assert.*;

import com.example.project2group10real.database.BudgetingDAO;
import com.example.project2group10real.database.SpendingDAO;
import com.example.project2group10real.database.UltimateBudgetingDatabase;
import com.example.project2group10real.database.UserDAO;
import com.example.project2group10real.database.entities.SpendingLog;

import java.time.LocalDateTime;
import java.util.List;
import java.util.concurrent.atomic.AtomicReference;

@RunWith(AndroidJUnit4.class)
public class ExampleInstrumentedTest {
    /**
     * Instrumented test, which will execute on an Android device.
     *
     * @see <a href="http://d.android.com/tools/testing">Testing documentation</a>
     */
    private UltimateBudgetingDatabase db;
    private UserDAO userDAO;
    private SpendingDAO spendingDAO;
    private BudgetingDAO budgetingDAO;



    @Before
    public void createDatabase() {
        Context context = ApplicationProvider.getApplicationContext();
        db = Room.inMemoryDatabaseBuilder(context, UltimateBudgetingDatabase.class).build();
        userDAO = db.userDAO();
        spendingDAO = db.spendingDao();
        budgetingDAO = db.budgetingDAO();
    }
    @Test
    public void spendingDateTest() {
        SpendingLog a = new SpendingLog(1,10,"Test", LocalDateTime.of(2026, 4, 29, 12, 0));
        spendingDAO.insert(a);
        SpendingLog b = new SpendingLog(1,10,"Test");
        spendingDAO.insert(b);
        List<SpendingLog> logs = spendingDAO.getAllLogsbyUserIDCurrentMonth(1, SeeMonthActivity.convertDateToLong(LocalDateTime.now())).getValue();
        assertFalse(logs != null && logs.contains(a));

    }
    @Test
    public void useAppContext() {
        // Context of the app under test.
        Context appContext = InstrumentationRegistry.getInstrumentation().getTargetContext();
        assertEquals("com.example.project2group10real", appContext.getPackageName());
    }
}