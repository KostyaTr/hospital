package com.github.KostyaTr.hospital.dao;

import com.github.KostyaTr.hospital.dao.impl.DefaultMedicineDao;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

import static org.junit.jupiter.api.Assertions.assertFalse;

public class DefaultMedicineDaoTest {
    private MedicineDao medicineDao = DefaultMedicineDao.getInstance();
    @BeforeAll
    static void insert() throws SQLException {
        final String sqlMed = "insert into medicine values(1,'first', 20, 20.1, 2, 300000, 1)";

        try(Connection connection = DataSource.getInstance().getConnection()) {
            PreparedStatement preparedStatement = connection.prepareStatement("delete from medicine where 1=1");
            preparedStatement.executeUpdate();

            preparedStatement = connection.prepareStatement(sqlMed);
            preparedStatement.executeUpdate();
        }
    }

    @Test
    void getMedicine(){
        assertFalse(medicineDao.getMedicine().isEmpty());
    }

    @AfterAll
    static void delete() throws SQLException {
        try(Connection connection = DataSource.getInstance().getConnection()) {
            PreparedStatement preparedStatement = connection.prepareStatement("delete from medicine where 1=1");
            preparedStatement.executeUpdate();
        }
    }
}
