package com.github.KostyaTr.hospital.dao.impl;

import com.github.KostyaTr.hospital.dao.DataSource;
import com.github.KostyaTr.hospital.dao.DischargedPatientDao;
import com.github.KostyaTr.hospital.model.DischargedPatient;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class DefaultDischargedPatientDao implements DischargedPatientDao {
    private final int ONE_ROW_AFFECTED = 1;

    private static class SingletonHolder {
        static final DischargedPatientDao HOLDER_INSTANCE = new DefaultDischargedPatientDao();
    }

    public static DischargedPatientDao getInstance() {
        return DefaultDischargedPatientDao.SingletonHolder.HOLDER_INSTANCE;
    }

    @Override
    public Long addDischargedPatient(DischargedPatient dischargedPatient) {
        final String sql = "insert into discharged_patient(inpatient_id, patient_status, discharge_date) values(?,?,?)";
        try(Connection connection = DataSource.getInstance().getConnection();
            PreparedStatement preparedStatement = connection.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {
            preparedStatement.setLong(1, dischargedPatient.getInpatientId());
            preparedStatement.setString(2, dischargedPatient.getPatientStatus());
            preparedStatement.setDate(3, (Date) dischargedPatient.getDischargeDate());
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
    public List<DischargedPatient> getDischargedPatients() {
        final String sql = "select * from discharged_patient";
        try (Connection connection = DataSource.getInstance().getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(sql);
             ResultSet resultSet = preparedStatement.executeQuery()) {
            List<DischargedPatient> dischargedPatients = new ArrayList<>();
            while (resultSet.next()){
                final DischargedPatient dischargedPatient = new DischargedPatient(
                        resultSet.getLong("id"),
                        resultSet.getLong("inpatient_id"),
                        resultSet.getString("patient_status"),
                        resultSet.getDate("discharge_date"));
                dischargedPatients.add(dischargedPatient);
            }
            return dischargedPatients;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
}
