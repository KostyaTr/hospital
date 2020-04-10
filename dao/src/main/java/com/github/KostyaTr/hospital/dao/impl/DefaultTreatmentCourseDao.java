package com.github.KostyaTr.hospital.dao.impl;

import com.github.KostyaTr.hospital.dao.DataSource;
import com.github.KostyaTr.hospital.dao.TreatmentCourseDao;
import com.github.KostyaTr.hospital.model.TreatmentCourse;

import java.sql.*;

public class DefaultTreatmentCourseDao implements TreatmentCourseDao {
    private final int ONE_ROW_AFFECTED = 1;

    private static class SingletonHolder{
        final static TreatmentCourseDao INSTANCE_HOLDER = new DefaultTreatmentCourseDao();
    }

    public static TreatmentCourseDao getInstance(){return DefaultTreatmentCourseDao.SingletonHolder.INSTANCE_HOLDER;}


    @Override
    public TreatmentCourse getTreatmentCourseById(Long treatmentCourseId) {
       final String sql = "select * from treatment_course where id = ?";

        try(Connection connection = DataSource.getInstance().getConnection();
            PreparedStatement preparedStatement = connection.prepareStatement(sql)) {
            preparedStatement.setLong(1, treatmentCourseId);
            try(ResultSet resultSet = preparedStatement.executeQuery()){
                if (resultSet.next()){
                    return new TreatmentCourse(
                            resultSet.getLong("id"),
                            resultSet.getLong("medicine_id"),
                            resultSet.getDouble("drug_dose"),
                            resultSet.getString("reception_description"),
                            resultSet.getInt("times_a_day"),
                            resultSet.getInt("duration_in_days"));
                } else {
                    return null;
                }
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public Long addTreatmentCourse(TreatmentCourse treatmentCourse) {
        final String sql = "insert into treatment_course" +
                "(medicine_id, drug_dose, reception_description, times_a_day, duration_in_days) values" +
                "(?,?,?,?,?)";

        try(Connection connection = DataSource.getInstance().getConnection();
            PreparedStatement preparedStatement = connection.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {
            preparedStatement.setLong(1, treatmentCourse.getMedicineId());
            preparedStatement.setDouble(2, treatmentCourse.getMedicineDose());
            preparedStatement.setString(3, treatmentCourse.getReceptionDesc());
            preparedStatement.setInt(4, treatmentCourse.getTimesPerDay());
            preparedStatement.setInt(5, treatmentCourse.getDurationInDays());
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
    public boolean removeTreatmentCourseById(Long treatmentCourseId) {
        final String sql = "delete from treatment_course where id = ?";

        try (Connection connection = DataSource.getInstance().getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(sql)) {
            preparedStatement.setLong(1, treatmentCourseId);
            return preparedStatement.executeUpdate() == ONE_ROW_AFFECTED;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public boolean updateTreatmentCourseById(TreatmentCourse treatmentCourse) {
        final String sql = "update treatment_course set medicine_id = ?, drug_dose = ?," +
                " reception_description = ?, times_a_day = ?, duration_in_days = ?  " +
                "where id = ?;";

        try (Connection connection = DataSource.getInstance().getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(sql)) {
            preparedStatement.setLong(1, treatmentCourse.getMedicineId());
            preparedStatement.setDouble(2, treatmentCourse.getMedicineDose());
            preparedStatement.setString(3, treatmentCourse.getReceptionDesc());
            preparedStatement.setLong(4, treatmentCourse.getTimesPerDay());
            preparedStatement.setLong(5, treatmentCourse.getDurationInDays());
            preparedStatement.setLong(6, treatmentCourse.getTreatmentCourseId());
            return preparedStatement.executeUpdate() == ONE_ROW_AFFECTED;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
}
