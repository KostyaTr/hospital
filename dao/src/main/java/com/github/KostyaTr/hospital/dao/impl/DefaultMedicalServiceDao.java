package com.github.KostyaTr.hospital.dao.impl;

import com.github.KostyaTr.hospital.dao.DataSource;
import com.github.KostyaTr.hospital.dao.MedicalServiceDao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
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
    public List<String> getMedicalServices() {
        final String sql = "select service_name from medical_service;";

        try (Connection connection = DataSource.getInstance().getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(sql);
             ResultSet resultSet = preparedStatement.executeQuery()){
            final List<String> medicalServices = new ArrayList<>();
            while (resultSet.next()){
                medicalServices.add(resultSet.getString("service_name"));
            }
            return medicalServices;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public Double getPriceOfMedicalServiceById(Long medicalServiceId) {
        final String sql = "select service_cost from medical_service where id = ?;";

        try (Connection connection = DataSource.getInstance().getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(sql)){
             preparedStatement.setLong(1, medicalServiceId);
            try(ResultSet resultSet = preparedStatement.executeQuery()) {
                if (resultSet.next()) {
                    return resultSet.getDouble("service_cost");
                } else {
                    return null;
                }
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public Long getNeededSpecialityId(Long medicalServiceId) {
        final String sql = "select needed_speciality_id from medical_service where id = ?;";

        try (Connection connection = DataSource.getInstance().getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(sql)){
            preparedStatement.setLong(1, medicalServiceId);
            try(ResultSet resultSet = preparedStatement.executeQuery()) {
                if (resultSet.next()) {
                    return resultSet.getLong("needed_speciality_id");
                } else {
                    return null;
                }
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
}
