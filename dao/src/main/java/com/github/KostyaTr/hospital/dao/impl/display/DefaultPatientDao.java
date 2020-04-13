package com.github.KostyaTr.hospital.dao.impl.display;

import com.github.KostyaTr.hospital.dao.DataSource;
import com.github.KostyaTr.hospital.dao.display.PatientDao;
import com.github.KostyaTr.hospital.model.display.Patient;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class DefaultPatientDao implements PatientDao {
    private static class SingletonHolder {
        static final PatientDao HOLDER_INSTANCE = new DefaultPatientDao();
    }

    public static PatientDao getInstance() {
        return DefaultPatientDao.SingletonHolder.HOLDER_INSTANCE;
    }

    @Override
    public List<Patient> getPatientsByDoctorId(Long doctorId) {
        final String sql = "select patient.id, concat(user.first_name, \" \", user.last_name) as patient_name, medical_service.service_name, patient.visit_date  from patient\n" +
                "join user on user.id = patient.user_id\n" +
                "join medical_service on patient.medical_service_id = medical_service.id\n" +
                "where patient.doctor_id = ?;";

        try (Connection connection = DataSource.getInstance().getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(sql)) {
            preparedStatement.setLong(1, doctorId);
            try (ResultSet resultSet = preparedStatement.executeQuery()){
                List<Patient> patients = new ArrayList<>();
                while (resultSet.next()){
                    final Patient patient = new Patient(
                            resultSet.getLong("id"),
                            resultSet.getString("patient_name"),
                            resultSet.getString("service_name"),
                            resultSet.getTimestamp("visit_date"));
                    patients.add(patient);
                }
                return patients;
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
}
