package com.github.KostyaTr.hospital.dao;

import com.github.KostyaTr.hospital.dao.impl.DefaultMedDoctorDao;
import com.github.KostyaTr.hospital.model.MedDoctor;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import java.sql.*;

import static org.junit.jupiter.api.Assertions.*;

public class DefaultMedDoctorDaoTest {
    private MedDoctorDao medDoctorDao = DefaultMedDoctorDao.getInstance();

    @BeforeAll
    static void insert() throws SQLException {
        final String sqlUser = "insert into user values(1,'first','last','phone','email')";
        final String sqslDoctor = "insert into doctor values(1, 1, 1, 1);";
        final String sqlDept = "insert into department values(1, '1', 1, 1);";

        try(Connection connection = DataSource.getInstance().getConnection()) {
            PreparedStatement preparedStatement = connection.prepareStatement("delete from doctor where 1=1");
            preparedStatement.executeUpdate();
            preparedStatement = connection.prepareStatement("delete from department where 1=1");
            preparedStatement.executeUpdate();

            preparedStatement = connection.prepareStatement("delete from user where 1=1");
            preparedStatement.executeUpdate();

            preparedStatement = connection.prepareStatement(sqlDept);
            preparedStatement.executeUpdate();
            preparedStatement = connection.prepareStatement(sqlUser);
            preparedStatement.executeUpdate();
            preparedStatement = connection.prepareStatement(sqslDoctor);
            preparedStatement.executeUpdate();

        }
    }


    @Test
    void getDoctors(){
        assertFalse(medDoctorDao.getDoctors().isEmpty());
    }

    @Test
    public void getDoctorById() {
        assertNull(medDoctorDao.getDoctorById((long) 2));
        assertNull(medDoctorDao.getDoctorByUserId((long)2));
        final int doctorById = Math.toIntExact(medDoctorDao.getDoctorById((long) 1).getDoctorId());
        assertEquals(1, doctorById);
        final int doctorByUserId = Math.toIntExact(medDoctorDao.getDoctorByUserId((long) 1).getDoctorId());
        assertEquals(1, doctorByUserId);
    }

    @AfterAll
    static void delete() throws SQLException {
        try(Connection connection = DataSource.getInstance().getConnection()) {
            PreparedStatement preparedStatement = connection.prepareStatement("delete from doctor where 1=1");
            preparedStatement.executeUpdate();
            preparedStatement = connection.prepareStatement("delete from department where 1=1");
            preparedStatement.executeUpdate();

            preparedStatement = connection.prepareStatement("delete from user where 1=1");
            preparedStatement.executeUpdate();
        }
    }
}
