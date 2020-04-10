package com.github.KostyaTr.hospital.dao.impl;

import com.github.KostyaTr.hospital.dao.CardDao;
import com.github.KostyaTr.hospital.dao.DataSource;
import com.github.KostyaTr.hospital.model.Card;

import java.sql.*;

public class DefaultCardDao implements CardDao {
    private static class SingletonHolder {
        static final CardDao HOLDER_INSTANCE = new DefaultCardDao();
    }

    public static CardDao getInstance() {
        return DefaultCardDao.SingletonHolder.HOLDER_INSTANCE;
    }


    @Override
    public Card getCardByUserId(Long userId) {
        final String sql = "select * from card where user_id = ?";
        try(Connection connection = DataSource.getInstance().getConnection();
            PreparedStatement preparedStatement = connection.prepareStatement(sql)) {
            preparedStatement.setLong(1, userId);
            try(ResultSet resultSet = preparedStatement.executeQuery()){
                if (resultSet.next()){
                    return new Card(
                            resultSet.getLong("id"),
                            resultSet.getLong("user_id"),
                            resultSet.getString("history"),
                            resultSet.getString("address"),
                            resultSet.getDate("date_of_birth"),
                            resultSet.getBoolean("insurance"));
                } else {
                    return null;
                }
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public Long addCard(Card card) {
        final String sql = "insert into card(user_id, history, address, date_of_birth, insurance) values(?,?,?,?,?)";

        try(Connection connection = DataSource.getInstance().getConnection();
            PreparedStatement preparedStatement = connection.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {
            preparedStatement.setLong(1, card.getUserId());
            preparedStatement.setString(2, card.getHistory());
            preparedStatement.setString(3, card.getAddress());
            preparedStatement.setDate(4, (Date) card.getBirthday());
            preparedStatement.setBoolean(5, card.isInsurance());
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
