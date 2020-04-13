package com.github.KostyaTr.hospital.dao.impl;

import com.github.KostyaTr.hospital.dao.DataSource;
import com.github.KostyaTr.hospital.dao.MedicineDao;
import com.github.KostyaTr.hospital.model.Medicine;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class DefaultMedicineDao implements MedicineDao {
    private static class SingletonHolder{
        static final MedicineDao HOLDER_INSTANCE = new DefaultMedicineDao();
    }

    public static MedicineDao getInstance(){
        return DefaultMedicineDao.SingletonHolder.HOLDER_INSTANCE;
    }

    @Override
    public List<Medicine> getMedicine() {
        final String sql = "select * from medicine;";

        try (Connection connection = DataSource.getInstance().getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(sql);
             ResultSet resultSet = preparedStatement.executeQuery()) {
            List<Medicine> medicines = new ArrayList<>();
            while (resultSet.next()){
                final Medicine user = new Medicine(
                        resultSet.getLong("id"),
                        resultSet.getString("medicine_name"),
                        resultSet.getDouble("normal_dose"),
                        resultSet.getDouble("critical_dose"),
                        resultSet.getInt("package_amount"),
                        resultSet.getDouble("price"),
                        resultSet.getInt("stock_balance"));
                medicines.add(user);
            }
            return medicines;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

    }
}
