package com.github.KostyaTr.hospital.dao.impl;

import com.github.KostyaTr.hospital.dao.DataSource;
import com.github.KostyaTr.hospital.dao.GuestPatientDao;
import com.github.KostyaTr.hospital.model.GuestPatient;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class DefaultGuestPatientDao implements GuestPatientDao {
    private final int ONE_ROW_AFFECTED = 1;

    private static class SingletonHolder {
        static final GuestPatientDao HOLDER_INSTANCE = new DefaultGuestPatientDao();
    }

    public static GuestPatientDao getInstance() {
        return DefaultGuestPatientDao.SingletonHolder.HOLDER_INSTANCE;
    }


    @Override
    public List<GuestPatient> getPatientsByDoctorId(Long doctorId) {
        final String sql = "select * from guest_patient where doctor_id = ?;";

        return getPatients(doctorId, sql);
    }

    @Override
    public List<GuestPatient> getPatients() {
        final String sql = "select * from guest_patient;";


        try (Connection connection = DataSource.getInstance().getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(sql);
             ResultSet resultSet = preparedStatement.executeQuery()){
            return getPatients(resultSet);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public Long addPatient(GuestPatient guestPatient) {
        final String sql = "insert into guest_patient(guest_id, doctor_id, coupon_num, medical_service_id, visit_date) values(?,?,?,?,?,?,?)";

        try(Connection connection = DataSource.getInstance().getConnection();
            PreparedStatement preparedStatement = connection.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {
            preparedStatement.setString(1, guestPatient.getFirstName());
            preparedStatement.setString(2, guestPatient.getLastName());
            preparedStatement.setString(3, guestPatient.getPhoneNumber());
            preparedStatement.setString(4, guestPatient.getEmail());
            preparedStatement.setLong(5, guestPatient.getDoctorId());
            preparedStatement.setLong(6, guestPatient.getCouponNum());
            preparedStatement.setLong(7, guestPatient.getMedicalServiceId());
            preparedStatement.setTimestamp(8, (Timestamp) guestPatient.getVisitDate());
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
             PreparedStatement preparedStatement = connection.prepareStatement("delete from guest_patient where id = ?")) {
            preparedStatement.setLong(1, patientId);
            return preparedStatement.executeUpdate() == ONE_ROW_AFFECTED;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public GuestPatient getPatientById(Long patientId) {
        final String sql = "select * from guest_patient where id = ?;";

        try(Connection connection = DataSource.getInstance().getConnection();
            PreparedStatement preparedStatement = connection.prepareStatement(sql)) {
            preparedStatement.setLong(1, patientId);
            try(ResultSet resultSet = preparedStatement.executeQuery()){
                if (resultSet.next()){
                    return new GuestPatient(
                            resultSet.getLong("id"),
                            resultSet.getString("first_name"),
                            resultSet.getString("last_name"),
                            resultSet.getString("phone_number"),
                            resultSet.getString("email"),
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

    @Override
    public List<GuestPatient> getPatientsByDepartmentId(Long deptId) {
        final String sql = "select guest_patient.id, guest_patient.first_name, guest_patient.last_name, guest_patient.phone_number,\n" +
                "guest_patient.email, guest_patient.doctor_id, guest_patient.coupon_num, guest_patient.medical_service_id, guest_patient.visit_date\n" +
                "from guest_patient\n" +
                "join doctor on doctor.id = guest_patient.doctor_id\n" +
                "where doctor.dept_id = ?;";
        return getPatients(deptId, sql);
    }

    private List<GuestPatient> getPatients(ResultSet resultSet) throws SQLException {
        final List<GuestPatient> patients = new ArrayList<>();
        while (resultSet.next()){
            final GuestPatient patient = new GuestPatient(
                    resultSet.getLong("id"),
                    resultSet.getString("first_name"),
                    resultSet.getString("last_name"),
                    resultSet.getString("phone_number"),
                    resultSet.getString("email"),
                    resultSet.getLong("doctor_id"),
                    resultSet.getLong("coupon_num"),
                    resultSet.getLong("medical_service_id"),
                    resultSet.getTimestamp("visit_date"));
            patients.add(patient);
        }
        return patients;
    }

    private List<GuestPatient> getPatients(Long id, String sql) {
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
