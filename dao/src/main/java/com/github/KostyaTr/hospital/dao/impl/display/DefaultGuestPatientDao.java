package com.github.KostyaTr.hospital.dao.impl.display;

import com.github.KostyaTr.hospital.dao.DataSource;
import com.github.KostyaTr.hospital.dao.display.GuestPatientDao;
import com.github.KostyaTr.hospital.model.display.GuestPatient;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class DefaultGuestPatientDao implements GuestPatientDao {
    private static class SingletonHolder {
        static final GuestPatientDao HOLDER_INSTANCE = new DefaultGuestPatientDao();
    }

    public static GuestPatientDao getInstance() {
        return DefaultGuestPatientDao.SingletonHolder.HOLDER_INSTANCE;
    }

    @Override
    public List<GuestPatient> getPatientsByDoctorId(Long doctorId) {
        final String sql = "select guest_patient.id, concat(first_name, \" \", last_name) as patient_name, medical_service.service_name, visit_date from guest_patient\n" +
                "join medical_service on guest_patient.medical_service_id = medical_service.id\n" +
                "where guest_patient.doctor_id = ?;";

        try (Connection connection = DataSource.getInstance().getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(sql)){
            preparedStatement.setLong(1, doctorId);
            try (ResultSet resultSet = preparedStatement.executeQuery()) {
                final List<GuestPatient> patients = new ArrayList<>();
                while (resultSet.next()){
                    final GuestPatient patient = new GuestPatient(
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
