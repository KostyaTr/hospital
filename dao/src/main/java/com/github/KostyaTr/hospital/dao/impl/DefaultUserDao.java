package com.github.KostyaTr.hospital.dao.impl;

import com.github.KostyaTr.hospital.dao.DataSource;
import com.github.KostyaTr.hospital.model.User;
import com.github.KostyaTr.hospital.dao.UserDao;


import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class DefaultUserDao implements UserDao{
    private final int ONE_ROW_AFFECTED = 1;

    private static class SingletonHolder {
        static final UserDao HOLDER_INSTANCE = new DefaultUserDao();
    }

    public static UserDao getInstance() {
        return DefaultUserDao.SingletonHolder.HOLDER_INSTANCE;
    }


    @Override
    public List<User> getUsers() {
        try (Connection connection = DataSource.getInstance().getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement("select * from user");
             ResultSet resultSet = preparedStatement.executeQuery()) {
            List<User> users = new ArrayList<>();
            while (resultSet.next()){
                final User user = new User(
                        resultSet.getLong("id"),
                        resultSet.getString("first_name"),
                        resultSet.getString("last_name"),
                        resultSet.getString("phone_number"),
                        resultSet.getString("email"));
                users.add(user);
            }
            return users;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public Long saveUser(User user) {
        final String sql = "insert into user(first_name, last_name, phone_number, email) values(?,?,?,?)";
        try(Connection connection = DataSource.getInstance().getConnection();
            PreparedStatement preparedStatement = connection.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {
            preparedStatement.setString(1, user.getFirstName());
            preparedStatement.setString(2, user.getLastName());
            preparedStatement.setString(3, user.getPhoneNumber());
            preparedStatement.setString(4, user.getEmail());
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
    public User getUserById(Long userId) {
        try(Connection connection = DataSource.getInstance().getConnection();
            PreparedStatement preparedStatement = connection.prepareStatement("select * from user where id = ?")) {
            preparedStatement.setLong(1, userId);
            try(ResultSet resultSet = preparedStatement.executeQuery()){
                if (resultSet.next()){
                    return new User(
                            resultSet.getLong("id"),
                            resultSet.getString("first_name"),
                            resultSet.getString("last_name"),
                            resultSet.getString("phone_number"),
                            resultSet.getString("email"));
                } else {
                    return null;
                }
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
}
