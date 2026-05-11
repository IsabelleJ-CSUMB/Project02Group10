package com.example.project2group10real;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;

import com.example.project2group10real.database.entities.User;

import org.junit.Test;

public class UserTest {

    @Test
    public void testUsername(){
        User user = new User();
        user.setUsername("test");
        assertEquals("test", user.getUsername());

    }
    @Test
    public void testIsAdminDefaultsToFalse(){
        User user = new User();
        assertFalse(user.isAdmin());

    }

}
