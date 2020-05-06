package com.github.KostyaTr.hospital.dao.impl;

import com.github.KostyaTr.hospital.dao.DataSource;
import com.github.KostyaTr.hospital.dao.ReceiptDao;
import com.github.KostyaTr.hospital.model.Receipt;

import java.sql.*;

public class DefaultReceiptDao implements ReceiptDao {
    private static class SingletonHolder{
        static final ReceiptDao HOLDER_INSTANCE = new DefaultReceiptDao();
    }

    public static ReceiptDao getInstance(){
        return DefaultReceiptDao.SingletonHolder.HOLDER_INSTANCE;
    }


    @Override
    public Receipt getReceiptByUserId(Long userId) {
        final String sql = "select * from receipt where user_id = ?";

        try (Connection connection = DataSource.getInstance().getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(sql)) {
            preparedStatement.setLong(1, userId);
            try(ResultSet resultSet = preparedStatement.executeQuery()){
                if (resultSet.next()){
                    return new Receipt(
                            resultSet.getLong("user_id"),
                            resultSet.getDouble("price_for_chamber"),
                            resultSet.getDouble("price_for_medicine"));
                }
                return null;
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public boolean updateReceipt(Receipt receipt) {
        final String sql = "update receipt set price_for_chamber = price_for_chamber + ?," +
                " price_for_medicine = price_for_medicine + ? where user_id = ?";
        try (Connection connection = DataSource.getInstance().getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(sql)) {
            preparedStatement.setDouble(1, receipt.getPriceForChamber());
            preparedStatement.setDouble(2, receipt.getPriceForMedicine());
            preparedStatement.setLong(3, receipt.getUserId());
            return preparedStatement.executeUpdate() == 1;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public boolean insertReceipt(Receipt receipt) {
        final String sql = "insert into receipt values(?, ?, ?) ;";
        try (Connection connection = DataSource.getInstance().getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(sql)) {
            preparedStatement.setLong(1, receipt.getUserId());
            preparedStatement.setDouble(2, receipt.getPriceForChamber());
            preparedStatement.setDouble(3, receipt.getPriceForMedicine());
            return preparedStatement.executeUpdate() == 1;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
}
