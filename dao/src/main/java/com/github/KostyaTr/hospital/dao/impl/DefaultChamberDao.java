package com.github.KostyaTr.hospital.dao.impl;

import com.github.KostyaTr.hospital.dao.ChamberDao;
import com.github.KostyaTr.hospital.dao.DataSource;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class DefaultChamberDao implements ChamberDao {
    private final int ONE_ROW_AFFECTED = 1;

    private static class SingletonHolder {
        static final ChamberDao HOLDER_INSTANCE = new DefaultChamberDao();
    }

    public static ChamberDao getInstance() {
        return DefaultChamberDao.SingletonHolder.HOLDER_INSTANCE;
    }

    @Override
    public boolean updateChamberCapacity(Long id, int capacity) {
        final String sql = "update chamber set chamber_capacity = ? where id = ?";

        try (Connection connection = DataSource.getInstance().getConnection();
            PreparedStatement preparedStatement = connection.prepareStatement(sql)) {
            preparedStatement.setInt(1, capacity);
            preparedStatement.setLong(2, id);
            return preparedStatement.executeUpdate() == ONE_ROW_AFFECTED;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public List<Long> getEmptyChambersByDeptId(Long deptId) {
        final String sql = "select id from chamber where chamber_capacity < chamber_load and vip = false;";
        return getChambers(sql);
    }

    @Override
    public List<Long> getEmptyVipChambersByDeptId(Long deptId) {
        final String sql = "select id from chamber where chamber_capacity < chamber_load and vip = true;";
        return getChambers(sql);
    }

    private List<Long> getChambers(String sql) {
        try (Connection connection = DataSource.getInstance().getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(sql);
             ResultSet resultSet = preparedStatement.executeQuery()) {
            List<Long> chambers = new ArrayList<>();
            while (resultSet.next()){
                chambers.add(resultSet.getLong("id"));
            }
            return chambers;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
}
