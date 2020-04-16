package com.github.KostyaTr.hospital.dao.impl;

import com.github.KostyaTr.hospital.dao.DataSource;
import com.github.KostyaTr.hospital.dao.PatientDao;
import com.github.KostyaTr.hospital.model.Patient;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class DefaultPatientDao implements PatientDao {
    private final int ONE_ROW_AFFECTED = 1;

    private static class SingletonHolder{
        static final PatientDao HOLDER_INSTANCE = new DefaultPatientDao();
    }

    public static PatientDao getInstance(){
        return DefaultPatientDao.SingletonHolder.HOLDER_INSTANCE;
    }


    @Override
    public List<Patient> getPatientsByDoctorId(Long doctorId) {
        final String sql = "select * from patient where doctor_id = ?;";
        return getPatients(doctorId, sql);
    }

    @Override
    public Long getLatestCouponToDoctorByDay(Long doctorId, int day) {
        final String sql = "select coupon_num from patient\n" +
                "where doctor_id = ? and day(visit_date) = ?\n" +
                "order by coupon_num desc\n" +
                "limit 1;";

        try(Connection connection = DataSource.getInstance().getConnection();
            PreparedStatement preparedStatement = connection.prepareStatement(sql)) {
            preparedStatement.setLong(1, doctorId);
            preparedStatement.setInt(2, day);
            try(ResultSet resultSet = preparedStatement.executeQuery()){
                if (resultSet.next()){
                    return resultSet.getLong("coupon_num");
                } else {
                    return (long) 0;
                }
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public List<Patient> getPatients() {
        final String sql = "select * from patient;";
        try (Connection connection = DataSource.getInstance().getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(sql);
             ResultSet resultSet = preparedStatement.executeQuery()){
            return getPatients(resultSet);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public Long addPatient(Patient patient) {
        final String sql = "insert into patient(user_id, doctor_id, coupon_num, medical_service_id, visit_date) values(?,?,?,?,?)";

        try(Connection connection = DataSource.getInstance().getConnection();
            PreparedStatement preparedStatement = connection.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {
            preparedStatement.setLong(1, patient.getUserId());
            preparedStatement.setLong(2, patient.getDoctorId());
            preparedStatement.setLong(3, patient.getCouponNum());
            preparedStatement.setLong(4, patient.getMedicalServiceId());
            preparedStatement.setTimestamp(5, new Timestamp(patient.getVisitDate().getTime()));
            preparedStatement.executeUpdate();
            try(ResultSet key = preparedStatement.getGeneratedKeys()){
                key.next();
                return key.getLong(1);
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public boolean removePatientById(Long patientId) {
        try (Connection connection = DataSource.getInstance().getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement("delete from patient where id = ?")) {
            preparedStatement.setLong(1, patientId);
            return preparedStatement.executeUpdate() == ONE_ROW_AFFECTED;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public Patient getPatientById(Long patientId) {
        final String sql = "select * from patient where id = ?;";

        try(Connection connection = DataSource.getInstance().getConnection();
            PreparedStatement preparedStatement = connection.prepareStatement(sql)) {
            preparedStatement.setLong(1, patientId);
            try(ResultSet resultSet = preparedStatement.executeQuery()){
                if (resultSet.next()){
                    return new Patient(
                            resultSet.getLong("id"),
                            resultSet.getLong("user_id"),
                            resultSet.getLong("doctor_id"),
                            resultSet.getLong("coupon_num"),
                            resultSet.getLong("medical_service_id"),
                            resultSet.getTimestamp("visit_date"));
                } else {
                    return null;
                }
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    private List<Patient> getPatients(ResultSet resultSet) throws SQLException {
        final List<Patient> patients = new ArrayList<>();
        while (resultSet.next()){
            final Patient patient = new Patient(
                    resultSet.getLong("id"),
                    resultSet.getLong("user_id"),
                    resultSet.getLong("doctor_id"),
                    resultSet.getLong("coupon_num"),
                    resultSet.getLong("medical_service_id"),
                    resultSet.getTimestamp("visit_date"));
            patients.add(patient);
        }
        return patients;
    }

    private List<Patient> getPatients(Long id, String sql) {
        try (Connection connection = DataSource.getInstance().getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(sql)){
            preparedStatement.setLong(1, id);
            try (ResultSet resultSet = preparedStatement.executeQuery()) {
                return getPatients(resultSet);
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
}