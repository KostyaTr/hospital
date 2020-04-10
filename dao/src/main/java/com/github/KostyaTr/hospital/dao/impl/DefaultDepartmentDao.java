package com.github.KostyaTr.hospital.dao.impl;

import com.github.KostyaTr.hospital.dao.DataSource;
import com.github.KostyaTr.hospital.dao.DepartmentDao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class DefaultDepartmentDao implements DepartmentDao {
    private static class SingletonHolder {
        static final DepartmentDao HOLDER_INSTANCE = new DefaultDepartmentDao();
    }

    public static DepartmentDao getInstance() {
        return DefaultDepartmentDao.SingletonHolder.HOLDER_INSTANCE;
    }


    @Override
    public List<String> getDepartments() {
        final String sql = "select department_name from department;";
        try (Connection connection = DataSource.getInstance().getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(sql);
             ResultSet resultSet = preparedStatement.executeQuery()) {
            List<String> departments = new ArrayList<>();
            while (resultSet.next()){
                departments.add(resultSet.getString("department_name"));
            }
            return departments;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
}
