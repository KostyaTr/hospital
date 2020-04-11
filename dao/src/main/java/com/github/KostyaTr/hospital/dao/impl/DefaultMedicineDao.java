package com.github.KostyaTr.hospital.dao.impl;

import com.github.KostyaTr.hospital.dao.DataSource;
import com.github.KostyaTr.hospital.dao.MedicineDao;
import com.github.KostyaTr.hospital.model.Medicine;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class DefaultMedicineDao implements MedicineDao {
    private final int ONE_ROW_AFFECTED = 1;

    private static class SingletonHolder{
        static final MedicineDao HOLDER_INSTANCE = new DefaultMedicineDao();
    }

    public static MedicineDao getInstance(){
        return DefaultMedicineDao.SingletonHolder.HOLDER_INSTANCE;
    }



    @Override
    public Long addMedicine(Medicine medicine) {
        final String sql = "insert into medicine(medicine_name, normal_dose, critical_dose, package_amount, price, stock_balance) values(?,?,?,?,?,?)";
        try(Connection connection = DataSource.getInstance().getConnection();
            PreparedStatement preparedStatement = connection.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {
            preparedStatement.setString(1, medicine.getMedicineName());
            preparedStatement.setDouble(2, medicine.getNormalDose());
            preparedStatement.setDouble(3, medicine.getCriticalDose());
            preparedStatement.setInt(4, medicine.getPackageAmount());
            preparedStatement.setDouble(4, medicine.getPrice());
            preparedStatement.setInt(4, medicine.getStockBalance());
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
    public boolean updateStockBalanceById(Integer stockBalance, Long medicineId) {
        final String sql = "update medicine set stock_balance = ? where id = ?;";

        try (Connection connection = DataSource.getInstance().getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(sql)) {
            preparedStatement.setLong(1, stockBalance);
            preparedStatement.setLong(2, medicineId);
            return preparedStatement.executeUpdate() == ONE_ROW_AFFECTED;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
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
