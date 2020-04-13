package com.github.KostyaTr.hospital.dao;

import com.github.KostyaTr.hospital.dao.impl.DefaultChamberDao;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

import static org.junit.jupiter.api.Assertions.*;

public class DefaultChamberDaoTest {
    private ChamberDao chamberDao = DefaultChamberDao.getInstance();

    @BeforeAll
    static void insert() throws SQLException {
        final String sqlChamber = "insert into chamber values(1, 1, 1, 0, 3, 1, 100);";
        final String sqlDept = "insert into department values(1, '1', 1, 1);";

        try (Connection connection = DataSource.getInstance().getConnection()) {
            PreparedStatement preparedStatement = connection.prepareStatement("delete from chamber where 1=1");
            preparedStatement.executeUpdate();

            preparedStatement = connection.prepareStatement("delete from department where 1=1");
            preparedStatement.executeUpdate();

            preparedStatement = connection.prepareStatement(sqlDept);
            preparedStatement.executeUpdate();
            preparedStatement = connection.prepareStatement(sqlChamber);
            preparedStatement.executeUpdate();

        }
    }

    @Test
    void updateChamberLoad() {
        assertTrue(chamberDao.updateChamberLoad((long)1, 1));
    }

    @Test
    void getChambers() {
        assertFalse(chamberDao.getEmptyChambersByDeptId((long) 1).isEmpty());
    }

    @AfterAll
    static void delete() throws SQLException {
        try(Connection connection = DataSource.getInstance().getConnection()) {
            PreparedStatement preparedStatement = connection.prepareStatement("delete from chamber where 1=1");
            preparedStatement.executeUpdate();

            preparedStatement = connection.prepareStatement("delete from department where 1=1");
            preparedStatement.executeUpdate();
        }
    }
}
