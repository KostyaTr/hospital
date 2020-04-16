package com.github.KostyaTr.hospital.dao.impl;

import com.github.KostyaTr.hospital.dao.DataSource;
import com.github.KostyaTr.hospital.dao.MedDoctorDao;
import com.github.KostyaTr.hospital.model.MedDoctor;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class DefaultMedDoctorDao implements MedDoctorDao {

    private static class SingletonHolder {
        static final MedDoctorDao HOLDER_INSTANCE = new DefaultMedDoctorDao();
    }

    public static MedDoctorDao getInstance() {
        return DefaultMedDoctorDao.SingletonHolder.HOLDER_INSTANCE;
    }

    @Override
    public List<MedDoctor> getDoctors() {
        final String sql = "select * from doctor";
        return getMedDoctors(sql);
    }

    @Override
    public MedDoctor getDoctorById(Long doctorId) {
        final String sql = "select * from doctor where id = ?;";
        return getMedDoctor(doctorId, sql);
    }

    @Override
    public MedDoctor getDoctorByUserId(Long userId) {
        final String sql = "select * from doctor where user_id = ?;";
        return getMedDoctor(userId, sql);
    }

    private MedDoctor getMedDoctor(Long id, String sql) {
        try(Connection connection = DataSource.getInstance().getConnection();
            PreparedStatement preparedStatement = connection.prepareStatement(sql)) {
            preparedStatement.setLong(1, id);
            try (ResultSet resultSet = preparedStatement.executeQuery()){
                if (resultSet.next()) {
                    return new MedDoctor(
                            resultSet.getLong("id"),
                            resultSet.getLong("user_id"),
                            resultSet.getLong("dept_id"),
                            resultSet.getBoolean("head_of_dept"));
                } else {
                    return null;
                }
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    private List<MedDoctor> getMedDoctors(String sql) {
        try (Connection connection = DataSource.getInstance().getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(sql);
             ResultSet resultSet = preparedStatement.executeQuery()){
            final List<MedDoctor> medDoctors = new ArrayList<>();
            while (resultSet.next()){
                final MedDoctor medDoctor = new MedDoctor(
                        resultSet.getLong("id"),
                        resultSet.getLong("user_id"),
                        resultSet.getLong("dept_id"),
                        resultSet.getBoolean("head_of_dept"));
                medDoctors.add(medDoctor);
            }
            return medDoctors;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
}