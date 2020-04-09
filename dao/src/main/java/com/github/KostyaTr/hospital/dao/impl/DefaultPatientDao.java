package com.github.KostyaTr.hospital.dao.impl;

import com.github.KostyaTr.hospital.dao.DataSource;
import com.github.KostyaTr.hospital.model.Appointment;
import com.github.KostyaTr.hospital.model.Patient;
import com.github.KostyaTr.hospital.dao.PatientDao;

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
        final String sql = "select patient.id, user.first_name, user.last_name, user.phone_number, user.email, card.id as card_num, concat(doctor_user.first_name, \" \", doctor_user.last_name) as doctor_name, patient.coupon_num,  medical_service.service_name, patient.visit_date from patient\n" +
                "join user on user.id = patient.user_id\n" +
                "join card on card.user_id = patient.user_id\n" +
                "join doctor on doctor.id = patient.doctor_id\n" +
                "join user as doctor_user on doctor.user_id = doctor_user.id\n" +
                "join medical_service on patient.medical_service_id = medical_service.id\n" +
                "where doctor.id = ?;\n";

        return getPatients(doctorId, sql);
    }

    @Override
    public List<Patient> getPatients() {
        final String sql = "select patient.id, user.first_name, user.last_name, user.phone_number, user.email," +
                " card.id as card_num, concat(doctor_user.first_name, \" \", doctor_user.last_name) as doctor_name," +
                " patient.coupon_num,  medical_service.service_name, patient.visit_date" +
                " from patient\n" +
                "join user on user.id = patient.user_id\n" +
                "join card on card.user_id = patient.user_id\n" +
                "join doctor on doctor.id = patient.doctor_id\n" +
                "join user as doctor_user on doctor.user_id = doctor_user.id\n" +
                "join medical_service on patient.medical_service_id = medical_service.id;";


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
        final String sql = "insert into patient(user_id, doctor_id, coupon_num, medical_service_id, visit_date) values(?,?,?,?,?)";

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

     /* String timeStr = "2020-06-30 19:10:00";
        Timestamp timeCreated = Timestamp.valueOf(timeStr);*/

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
        final String sql = "select patient.id, user.first_name, user.last_name, user.phone_number, user.email, card.id as card_num, concat(doctor_user.first_name, \" \", doctor_user.last_name) as doctor_name, patient.coupon_num,  medical_service.service_name, patient.visit_date from patient\n" +
                "join user on user.id = patient.user_id\n" +
                "join card on card.user_id = patient.user_id\n" +
                "join doctor on doctor.id = patient.doctor_id\n" +
                "join user as doctor_user on doctor.user_id = doctor_user.id\n" +
                "join medical_service on patient.medical_service_id = medical_service.id\n" +
                "where patient.id = ?;";

        try(Connection connection = DataSource.getInstance().getConnection();
            PreparedStatement preparedStatement = connection.prepareStatement(sql)) {
            preparedStatement.setLong(1, patientId);
            try(ResultSet resultSet = preparedStatement.executeQuery()){
                if (resultSet.next()){
                    return new Patient(
                            resultSet.getLong("id"),
                            resultSet.getString("first_name"),
                            resultSet.getString("last_name"),
                            resultSet.getString("phone_number"),
                            resultSet.getString("email"),
                            resultSet.getLong("card_num"),
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
    public List<Patient> getPatientsByDepartmentId(Long deptId) {
        final String sql = "select patient.id, user.first_name, user.last_name, user.phone_number, user.email, card.id as card_num, concat(doctor_user.first_name, \" \", doctor_user.last_name) as doctor_name, patient.coupon_num,  medical_service.service_name, patient.visit_date from patient\n" +
                "join user on user.id = patient.user_id\n" +
                "join card on card.user_id = patient.user_id\n" +
                "join doctor on doctor.id = patient.doctor_id\n" +
                "join user as doctor_user on doctor.user_id = doctor_user.id\n" +
                "join medical_service on patient.medical_service_id = medical_service.id\n" +
                "where doctor.dept_id = ?;";
        return getPatients(deptId, sql);
    }

    private List<Patient> getPatients(ResultSet resultSet) throws SQLException {
        final List<Patient> patients = new ArrayList<>();
        while (resultSet.next()){
            final Patient patient = new Patient(
                    resultSet.getLong("id"),
                    resultSet.getString("first_name"),
                    resultSet.getString("last_name"),
                    resultSet.getString("phone_number"),
                    resultSet.getString("email"),
                    resultSet.getLong("card_num"),
                    resultSet.getLong("coupon_num"),
                    resultSet.getTimestamp("visit_date"),
                    resultSet.getString("doctor_name"),
                    resultSet.getString("service_name"));
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