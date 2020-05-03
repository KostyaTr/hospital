package com.github.KostyaTr.hospital.dao.impl;

import com.github.KostyaTr.hospital.dao.DataSource;
import com.github.KostyaTr.hospital.dao.DischargedPatientDao;
import com.github.KostyaTr.hospital.model.DischargedPatient;
import com.github.KostyaTr.hospital.model.Status;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class DefaultDischargedPatientDao implements DischargedPatientDao {
    private static class SingletonHolder {
        static final DischargedPatientDao HOLDER_INSTANCE = new DefaultDischargedPatientDao();
    }

    public static DischargedPatientDao getInstance() {
        return DefaultDischargedPatientDao.SingletonHolder.HOLDER_INSTANCE;
    }

    @Override
    public Long addDischargedPatient(DischargedPatient dischargedPatient) {
        final String sql = "insert into discharged_patient(patient_name, doctor_name, diagnose," +
                "card_history, treatment_course, operation_name,  patient_status," +
                " enrollment_date, discharge_date) values(?,?,?,?,?,?,?,?,?)";

        try(Connection connection = DataSource.getInstance().getConnection();
            PreparedStatement preparedStatement = connection.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {
            preparedStatement.setString(1, dischargedPatient.getPatientName());
            preparedStatement.setString(2, dischargedPatient.getDoctorName());
            preparedStatement.setString(3, dischargedPatient.getDiagnose());
            preparedStatement.setString(4, dischargedPatient.getCardHistory());
            preparedStatement.setString(5, dischargedPatient.getTreatmentCourse());
            preparedStatement.setString(6, null);
            preparedStatement.setString(7, dischargedPatient.getStatus().toString());
            preparedStatement.setDate(8, new Date(dischargedPatient.getEnrollmentDate().getTime()));
            preparedStatement.setDate(9, new Date(dischargedPatient.getDischargeDate().getTime()));
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
    public List<DischargedPatient> getDischargedPatients(int page) {
        final String sql = "select * from discharged_patient limit 10 offset ?";

        try (Connection connection = DataSource.getInstance().getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(sql)) {
            preparedStatement.setInt(1, (page - 1) * 10);
            try (ResultSet resultSet = preparedStatement.executeQuery()){
                List<DischargedPatient> dischargedPatients = new ArrayList<>();
                while (resultSet.next()){
                    final DischargedPatient dischargedPatient = new DischargedPatient(
                            resultSet.getLong("id"),
                            resultSet.getString("patient_name"),
                            resultSet.getString("doctor_name"),
                            resultSet.getString("diagnose"),
                            resultSet.getString("card_history"),
                            resultSet.getString("treatment_course"),
                            Status.valueOf(resultSet.getString("patient_status")),
                            resultSet.getDate("enrollment_date"),
                            resultSet.getDate("discharge_date"));
                    dischargedPatients.add(dischargedPatient);
                }
                return dischargedPatients;
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public double getDischargedPatientsCount() {
        final String sql = "select count(*) as cnt from discharged_patient";

        try (Connection connection = DataSource.getInstance().getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(sql);
             ResultSet resultSet = preparedStatement.executeQuery()){
                if (resultSet.next()){
                    return resultSet.getLong("cnt");
                }
                else {
                    return 0L;
                }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
}
