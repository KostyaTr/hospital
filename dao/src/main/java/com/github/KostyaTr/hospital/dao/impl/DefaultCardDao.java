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
    public Long addCard(Card card) {
        final String sql = "insert into card(user_id, history, address, date_of_birth, insurance) values(?,?,?,?,?)";

        try(Connection connection = DataSource.getInstance().getConnection();
            PreparedStatement preparedStatement = connection.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {
            preparedStatement.setLong(1, card.getUserId());
            preparedStatement.setString(2, card.getHistory());
            preparedStatement.setString(3, card.getAddress());
            preparedStatement.setDate(4, new java.sql.Date(card.getBirthday().getTime()));
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
