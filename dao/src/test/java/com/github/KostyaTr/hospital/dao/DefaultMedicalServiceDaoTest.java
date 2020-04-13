package com.github.KostyaTr.hospital.dao;

import com.github.KostyaTr.hospital.dao.impl.DefaultMedicalServiceDao;
import com.github.KostyaTr.hospital.model.MedicalService;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

import static org.junit.jupiter.api.Assertions.*;

public class DefaultMedicalServiceDaoTest {
    private MedicalServiceDao medicalServiceDao = DefaultMedicalServiceDao.getInstance();

    @BeforeAll
    static void insert() throws SQLException {
        final String sqlSpec = "insert into speciality values(1,'spec')";
        final String sqlServ = "insert into medical_service values(1,'serv', 1, null, 100);";

        try(Connection connection = DataSource.getInstance().getConnection()) {
            PreparedStatement preparedStatement = connection.prepareStatement("delete from medical_service where 1=1");
            preparedStatement.executeUpdate();
            preparedStatement = connection.prepareStatement("delete from speciality where 1=1");
            preparedStatement.executeUpdate();

            preparedStatement = connection.prepareStatement(sqlSpec);
            preparedStatement.executeUpdate();
            preparedStatement = connection.prepareStatement(sqlServ);
            preparedStatement.executeUpdate();
        }
    }

    @Test
    void getMedicalServiceById() {
        assertNotNull(medicalServiceDao.getMedicalServiceById((long)1));
        assertNull(medicalServiceDao.getMedicalServiceById((long)2));
    }

    @Test
    void getMedicalServices(){
        assertFalse(medicalServiceDao.getMedicalServices().isEmpty());
    }


    @AfterAll
    static void delete() throws SQLException {
        try(Connection connection = DataSource.getInstance().getConnection()) {
            PreparedStatement preparedStatement = connection.prepareStatement("delete from medical_service where 1=1");
            preparedStatement.executeUpdate();
            preparedStatement = connection.prepareStatement("delete from speciality where 1=1");
            preparedStatement.executeUpdate();
        }
    }
}
