package com.github.KostyaTr.hospital.dao;

import com.github.KostyaTr.hospital.dao.config.DaoConfig;
import com.github.KostyaTr.hospital.dao.impl.DefaultUserDao;
import com.github.KostyaTr.hospital.model.User;
import org.hibernate.SessionFactory;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.transaction.annotation.Transactional;


import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(SpringExtension.class)
@ContextConfiguration(classes = DaoConfig.class)
@Transactional
public class DefaultUserDaoTest {
    @Autowired
    private SessionFactory sessionFactory;

    @Autowired
    private UserDao userDao;

    @Test
    void insertUser() {
      assertNotNull(userDao.saveUser(new User(
               null, "first1", "last1",
               "phone1","email1")));
    }

    @Test
    void deleteUser(){
        Long userId = userDao.saveUser(new User(null, "delete","delete","delete","delete"));
        assertTrue(userDao.removeUser(userId));
        assertTrue(userDao.removeUser(0L));
    }

    @Test
    void getUserById() {
       Long userId = userDao.saveUser(new User(
                null, "get", "get",
                "get","get"));
        assertNotNull(userDao.getUserById(userId));
        assertNull(userDao.getUserById(0L));
        assertEquals("get", userDao.getUserById(userId).getFirstName());
    }
}
