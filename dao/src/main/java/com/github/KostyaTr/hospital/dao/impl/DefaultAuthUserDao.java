package com.github.KostyaTr.hospital.dao.impl;

import com.github.KostyaTr.hospital.dao.AuthUserDao;
import com.github.KostyaTr.hospital.dao.DataSource;
import com.github.KostyaTr.hospital.model.AuthUser;
import com.github.KostyaTr.hospital.model.Role;

import java.sql.*;


public class DefaultAuthUserDao implements AuthUserDao {

    private static class SingletonHolder{
        final static AuthUserDao INSTANCE_HOLDER = new DefaultAuthUserDao();
    }

    public static AuthUserDao getInstance(){return DefaultAuthUserDao.SingletonHolder.INSTANCE_HOLDER;}


    @Override
    public AuthUser getByLogin(String login) {
        try (Connection connection = DataSource.getInstance().getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement("select * from auth_user where login = ?")) {
            preparedStatement.setString(1, login);
            try (ResultSet resultSet = preparedStatement.executeQuery()) {
                if (resultSet.next()) {
                    return new AuthUser(
                            resultSet.getLong("id"),
                            resultSet.getString("login"),
                            resultSet.getString("password"),
                            Role.valueOf(resultSet.getString("role")),
                            resultSet.getLong("user_id"));
                } else {
                    return null;
                }
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public Long saveAuthUser(AuthUser user) {
        final String sql = "insert into auth_user(login, password, role, user_id) values(?,?,?,?)";
        try (Connection connection = DataSource.getInstance().getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {
            preparedStatement.setString(1, user.getLogin());
            preparedStatement.setString(2, user.getPassword());
            preparedStatement.setString(3, user.getRole().name());
            preparedStatement.setLong(4, user.getUserId());
            preparedStatement.executeUpdate();
            try (ResultSet key = preparedStatement.getGeneratedKeys()) {
                key.next();
                return key.getLong(1);
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
}
