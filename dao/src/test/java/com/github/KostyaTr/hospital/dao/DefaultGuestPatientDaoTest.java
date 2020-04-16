package com.github.KostyaTr.hospital.dao;

import com.github.KostyaTr.hospital.dao.impl.DefaultGuestPatientDao;
import com.github.KostyaTr.hospital.model.GuestPatient;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.Date;

import static org.junit.jupiter.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.assertFalse;

public class DefaultGuestPatientDaoTest {
    private GuestPatientDao guestPatientDao = DefaultGuestPatientDao.getInstance();
    @BeforeAll
    static void insert() throws SQLException {
        final String sqlUser = "insert into user values(1,'first','last','phone','email')";
        final String sqlDoctor = "insert into doctor values(1, 1, 1, 1);";
        final String sqlDept = "insert into department values(1, '1', 1, 1);";
        final String sqlSpec = "insert into speciality values(1,'spec')";
        final String sqlServ = "insert into medical_service values(1,'serv', 1, null, 100);";
        final String sqlPatient = "insert into guest_patient values(1, 'first','last','phone','email', 1, 1, 1, '2020-04-08');";

        try(Connection connection = DataSource.getInstance().getConnection()) {
            PreparedStatement preparedStatement = connection.prepareStatement("delete from guest_patient where 1=1");
            preparedStatement.executeUpdate();
            preparedStatement = connection.prepareStatement("delete from doctor where 1=1");
            preparedStatement.executeUpdate();
            preparedStatement = connection.prepareStatement("delete from department where 1=1");
            preparedStatement.executeUpdate();
            preparedStatement = connection.prepareStatement("delete from user where 1=1");
            preparedStatement.executeUpdate();
            preparedStatement = connection.prepareStatement("delete from medical_service where 1=1");
            preparedStatement.executeUpdate();
            preparedStatement = connection.prepareStatement("delete from speciality where 1=1");
            preparedStatement.executeUpdate();

            preparedStatement = connection.prepareStatement(sqlSpec);
            preparedStatement.executeUpdate();
            preparedStatement = connection.prepareStatement(sqlServ);
            preparedStatement.executeUpdate();
            preparedStatement = connection.prepareStatement(sqlDept);
            preparedStatement.executeUpdate();
            preparedStatement = connection.prepareStatement(sqlUser);
            preparedStatement.executeUpdate();
            preparedStatement = connection.prepareStatement(sqlDoctor);
            preparedStatement.executeUpdate();
            preparedStatement = connection.prepareStatement(sqlPatient);
            preparedStatement.executeUpdate();
        }
    }

    @Test
    void getPatients(){
        assertFalse(guestPatientDao.getPatients().isEmpty());
    }

    @Test
    void addPatient(){
        assertNotNull(guestPatientDao.addPatient(new GuestPatient((long)2, "first","last","phone","email", (long)1, (long)1, (long)1, new Date())));
    }

    @Test
    void getPatientsByDoctorId(){
        assertTrue(guestPatientDao.getPatientsByDoctorId((long) 2).isEmpty());
        assertFalse(guestPatientDao.getPatientsByDoctorId((long) 1).isEmpty());
    }

    @AfterAll
    static void delete() throws SQLException {
        try(Connection connection = DataSource.getInstance().getConnection()) {
            PreparedStatement preparedStatement = connection.prepareStatement("delete from guest_patient where 1=1");
            preparedStatement.executeUpdate();
            preparedStatement = connection.prepareStatement("delete from doctor where 1=1");
            preparedStatement.executeUpdate();
            preparedStatement = connection.prepareStatement("delete from department where 1=1");
            preparedStatement.executeUpdate();
            preparedStatement = connection.prepareStatement("delete from user where 1=1");
            preparedStatement.executeUpdate();
            preparedStatement = connection.prepareStatement("delete from medical_service where 1=1");
            preparedStatement.executeUpdate();
            preparedStatement = connection.prepareStatement("delete from speciality where 1=1");
            preparedStatement.executeUpdate();
        }
    }
}
