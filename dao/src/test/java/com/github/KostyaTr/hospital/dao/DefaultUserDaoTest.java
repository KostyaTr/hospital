package com.github.KostyaTr.hospital.dao;

import com.github.KostyaTr.hospital.dao.impl.DefaultUserDao;
import com.github.KostyaTr.hospital.model.User;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import java.sql.*;

import static org.junit.jupiter.api.Assertions.*;

public class DefaultUserDaoTest {
    private UserDao userDao = DefaultUserDao.getInstance();
    @BeforeAll
    static void insert() throws SQLException{
        final String sqlUser = "insert into user values(1,'first','last','phone','email')";

        try(Connection connection = DataSource.getInstance().getConnection()) {
            PreparedStatement preparedStatement = connection.prepareStatement("delete from user where 1=1");
            preparedStatement.executeUpdate();

            preparedStatement = connection.prepareStatement(sqlUser);
            preparedStatement.executeUpdate();
        }
    }

    @Test
    void insertUser() {
      assertNotNull(userDao.saveUser(new User(
               null, "first1", "last1",
               "phone1","email1")));
    }

    @Test
    void getUserById() {
        assertNotNull(userDao.getUserById((long) 1));
        assertNull(userDao.getUserById((long) 2));
        assertEquals("first", userDao.getUserById((long) 1).getFirstName());
    }

    @AfterAll
    static void delete() throws SQLException {
        try(Connection connection = DataSource.getInstance().getConnection()) {
            PreparedStatement preparedStatement = connection.prepareStatement("delete from user where 1=1");
            preparedStatement.executeUpdate();
        }
    }
}