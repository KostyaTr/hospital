package com.github.KostyaTr.hospital.dao;

import com.github.KostyaTr.hospital.dao.impl.DefaultUserDao;
import com.github.KostyaTr.hospital.model.User;
import org.junit.jupiter.api.Test;


import static org.junit.jupiter.api.Assertions.*;

public class DefaultUserDaoTest {
    private UserDao userDao = DefaultUserDao.getInstance();

    @Test
    void insertUser() {
      assertNotNull(userDao.saveUser(new User(
               null, "first1", "last1",
               "phone1","email1", null, null, null, null, null, null)));
    }

    @Test
    void deleteUser(){
        Long userId = userDao.saveUser(new User(null, "delete","delete","delete","delete", null, null, null, null, null, null));
        assertTrue(userDao.removeUser(userId));
        assertTrue(userDao.removeUser(0L));
    }

    @Test
    void getUserById() {
       Long userId = userDao.saveUser(new User(
                null, "get", "get",
                "get","get", null, null, null, null, null, null));
        assertNotNull(userDao.getUserById(userId));
        assertNull(userDao.getUserById(0L));
        assertEquals("get", userDao.getUserById(userId).getFirstName());
    }
}