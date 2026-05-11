package com.example.project2group10real;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import com.example.project2group10real.database.entities.RecurringBill;

import org.junit.Test;

public class BudgetTest {

    @Test
    public void testBillName() {
        RecurringBill bill = new RecurringBill();
        bill.setBillName("Netflix");
        assertEquals("Netflix", bill.getBillName());
    }

    @Test
    public void testBillAmount() {
        RecurringBill bill = new RecurringBill();
        bill.setBillAmount(15.99);
        assertEquals(15.99, bill.getBillAmount(), 0.01);
    }

    @Test
    public void testBillAmountIsPositive() {
        RecurringBill bill = new RecurringBill();
        bill.setBillAmount(50.00);
        assertTrue(bill.getBillAmount() > 0);
    }

}