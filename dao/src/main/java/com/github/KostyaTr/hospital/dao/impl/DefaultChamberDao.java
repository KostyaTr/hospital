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
    public boolean updateChamberLoad(Long id, int load) {
        final String sql = "update chamber set chamber_load = chamber_load + ? where id = ?";

        try (Connection connection = DataSource.getInstance().getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(sql)) {
            preparedStatement.setInt(1, load);
            preparedStatement.setLong(2, id);
            return preparedStatement.executeUpdate() == ONE_ROW_AFFECTED;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public List<Long> getEmptyChambersByDeptId(Long deptId) {
        final String sql = "select id from chamber where chamber_capacity > chamber_load and vip = false and dept_id = ?;";
        return getChambers(sql, deptId);
    }

    private List<Long> getChambers(String sql, Long deptId) {
        try (Connection connection = DataSource.getInstance().getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(sql)) {
            preparedStatement.setLong(1, deptId);
            try (ResultSet resultSet = preparedStatement.executeQuery()){
                List<Long> chambers = new ArrayList<>();
                while (resultSet.next()){
                    chambers.add(resultSet.getLong("id"));
                }
                return chambers;
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
}
