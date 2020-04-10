package com.github.KostyaTr.hospital.dao.impl;

import com.github.KostyaTr.hospital.dao.DataSource;
import com.github.KostyaTr.hospital.dao.GuestPatientDao;
import com.github.KostyaTr.hospital.model.Appointment;
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
        final String sql = "select guest_patient.id, guest.first_name, guest.last_name, guest.phone_number, guest.email, guest_patient.coupon_num, concat(doctor_user.first_name, \" \", doctor_user.last_name) as doctor_name,  medical_service.service_name, guest_patient.visit_date from guest_patient\n" +
                "join guest on guest.id = guest_patient.id\n" +
                "join doctor on doctor.id = guest_patient.doctor_id\n" +
                "join user as doctor_user on doctor.user_id = doctor_user.id\n" +
                "join medical_service on guest_patient.medical_service_id = medical_service.id\n" +
                "where doctor.id = ?;";

        return getPatients(doctorId, sql);
    }

    @Override
    public List<GuestPatient> getPatients() {
        final String sql = "select guest_patient.id, guest.first_name, guest.last_name, guest.phone_number, guest.email, guest_patient.coupon_num, concat(doctor_user.first_name, \" \", doctor_user.last_name) as doctor_name,  medical_service.service_name, guest_patient.visit_date from guest_patient\n" +
                "join guest on guest.id = guest_patient.id\n" +
                "join doctor on doctor.id = guest_patient.doctor_id\n" +
                "join user as doctor_user on doctor.user_id = doctor_user.id\n" +
                "join medical_service on guest_patient.medical_service_id = medical_service.id;";


        try (Connection connection = DataSource.getInstance().getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(sql);
             ResultSet resultSet = preparedStatement.executeQuery()){
            return getPatients(resultSet);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public Long addPatient(Appointment appointment) {
        final String sql = "insert into guest_patient(guest_id, doctor_id, coupon_num, medical_service_id, visit_date) values(?,?,?,?,?)";

        try(Connection connection = DataSource.getInstance().getConnection();
            PreparedStatement preparedStatement = connection.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {
            preparedStatement.setLong(1, appointment.getUserId());
            preparedStatement.setLong(2, appointment.getDoctorId());
            preparedStatement.setLong(3, appointment.getCoupon_num());
            preparedStatement.setLong(4, appointment.getMedicalServiceId());
            preparedStatement.setTimestamp(5, (Timestamp) appointment.getVisitDate());
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
        final String sql = "select guest_patient.id, guest.first_name, guest.last_name, guest.phone_number, guest.email, guest_patient.coupon_num, concat(doctor_user.first_name, \" \", doctor_user.last_name) as doctor_name,  medical_service.service_name, guest_patient.visit_date from guest_patient\n" +
                "join guest on guest.id = guest_patient.id\n" +
                "join doctor on doctor.id = guest_patient.doctor_id\n" +
                "join user as doctor_user on doctor.user_id = doctor_user.id\n" +
                "join medical_service on guest_patient.medical_service_id = medical_service.id\n" +
                "where guest_patient.id = ?;";

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
                            resultSet.getLong("coupon_num"),
                            resultSet.getTimestamp("visit_date"),
                            resultSet.getString("doctor_name"),
                            resultSet.getString("service_name"));
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
        final String sql = "select guest_patient.id, guest.first_name, guest.last_name, guest.phone_number, guest.email, guest_patient.coupon_num, concat(doctor_user.first_name, \" \", doctor_user.last_name) as doctor_name,  medical_service.service_name, guest_patient.visit_date from guest_patient\n" +
                "join guest on guest.id = guest_patient.id\n" +
                "join doctor on doctor.id = guest_patient.doctor_id\n" +
                "join user as doctor_user on doctor.user_id = doctor_user.id\n" +
                "join medical_service on guest_patient.medical_service_id = medical_service.id\n" +
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
                    resultSet.getLong("coupon_num"),
                    resultSet.getTimestamp("visit_date"),
                    resultSet.getString("doctor_name"),
                    resultSet.getString("service_name"));
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
