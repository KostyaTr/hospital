package com.github.KostyaTr.hospital.dao.impl;

import com.github.KostyaTr.hospital.dao.DataSource;
import com.github.KostyaTr.hospital.dao.SpecialityDao;

import java.sql.*;

public class DefaultSpecialityDao implements SpecialityDao {
    private static class SingletonHolder {
        static final SpecialityDao HOLDER_INSTANCE = new DefaultSpecialityDao();
    }

    public static SpecialityDao getInstance() {
        return DefaultSpecialityDao.SingletonHolder.HOLDER_INSTANCE;
    }


    @Override
    public String getSpecialityById(Long specialityId) {
        final String sql = "select * from speciality where id = ?";

        try(Connection connection = DataSource.getInstance().getConnection();
            PreparedStatement preparedStatement = connection.prepareStatement(sql)) {
            preparedStatement.setLong(1, specialityId);
            try(ResultSet resultSet = preparedStatement.executeQuery()){
                if (resultSet.next()){
                    return resultSet.getString("speciality_name");
                } else {
                    return null;
                }
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public Long addSpeciality(String specialityName) {
        final String sql = "insert into speciality (speciality_name) values (?)";

        try(Connection connection = DataSource.getInstance().getConnection();
            PreparedStatement preparedStatement = connection.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {
            preparedStatement.setString(1, specialityName);
            preparedStatement.executeUpdate();
            try(ResultSet key = preparedStatement.getGeneratedKeys()){
                key.next();
                return key.getLong(1);
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
}
