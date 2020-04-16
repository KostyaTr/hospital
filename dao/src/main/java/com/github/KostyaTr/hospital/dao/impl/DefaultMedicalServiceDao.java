package com.github.KostyaTr.hospital.dao.impl;

import com.github.KostyaTr.hospital.dao.DataSource;
import com.github.KostyaTr.hospital.dao.MedicalServiceDao;
import com.github.KostyaTr.hospital.model.MedicalService;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class DefaultMedicalServiceDao implements MedicalServiceDao {

    private static class SingletonHolder {
        static final MedicalServiceDao HOLDER_INSTANCE = new DefaultMedicalServiceDao();
    }

    public static MedicalServiceDao getInstance() {
        return DefaultMedicalServiceDao.SingletonHolder.HOLDER_INSTANCE;
    }


    @Override
    public List<MedicalService> getMedicalServices() {
        final String sql = "select * from medical_service;";

        try (Connection connection = DataSource.getInstance().getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(sql);
             ResultSet resultSet = preparedStatement.executeQuery()){
            final List<MedicalService> medicalServices = new ArrayList<>();
            while (resultSet.next()){
                medicalServices.add(new MedicalService(
                        resultSet.getLong("id"),
                        resultSet.getString("service_name"),
                        resultSet.getLong("needed_speciality_id"),
                        resultSet.getLong("needed_equipment_id"),
                        resultSet.getDouble("service_cost")));
            }
            return medicalServices;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public Long addMedicalServiceId(MedicalService medicalService) {
        final String sql = "insert into medicalService(service_name, needed_speciality_id, needed_equipment_id, service_cost)" +
                " values(?,?,?,?)";

        try(Connection connection = DataSource.getInstance().getConnection();
            PreparedStatement preparedStatement = connection.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {
            preparedStatement.setString(1, medicalService.getServiceName());
            preparedStatement.setLong(2, medicalService.getNeededSpecialityId());
            preparedStatement.setLong(3, medicalService.getNeededEquipmentId());
            preparedStatement.setDouble(4, medicalService.getServiceCost());
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
    public MedicalService getMedicalServiceById(Long medicalServiceId) {
        final String sql = "select * from medical_service where id = ?;";

        try (Connection connection = DataSource.getInstance().getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(sql)){
            preparedStatement.setLong(1, medicalServiceId);
            try(ResultSet resultSet = preparedStatement.executeQuery()) {
                if (resultSet.next()) {
                    return new MedicalService(
                            resultSet.getLong("id"),
                            resultSet.getString("service_name"),
                            resultSet.getLong("needed_speciality_id"),
                            resultSet.getLong("needed_equipment_id"),
                            resultSet.getDouble("service_cost"));
                } else {
                    return null;
                }
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
}
