package com.github.KostyaTr.hospital.dao.impl.display;

import com.github.KostyaTr.hospital.dao.DataSource;
import com.github.KostyaTr.hospital.dao.display.TreatmentCourseDao;
import com.github.KostyaTr.hospital.model.display.TreatmentCourse;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class DefaultTreatmentCourseDao implements TreatmentCourseDao {
    private static class SingletonHolder{
        final static TreatmentCourseDao INSTANCE_HOLDER = new DefaultTreatmentCourseDao();
    }

    public static TreatmentCourseDao getInstance(){return DefaultTreatmentCourseDao.SingletonHolder.INSTANCE_HOLDER;}


    @Override
    public List<TreatmentCourse> getTreatmentCourses() {
        final String sql = "select * from treatment_course\n" +
                "join medicine on medicine.id = treatment_course.medicine_id;";

        try (Connection connection = DataSource.getInstance().getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(sql);
             ResultSet resultSet = preparedStatement.executeQuery()) {
            List<TreatmentCourse> courses = new ArrayList<>();
            while (resultSet.next()){
                final TreatmentCourse course = new TreatmentCourse(
                        resultSet.getLong("id"),
                        resultSet.getString("medicine_name"),
                        resultSet.getDouble("drug_dose"),
                        resultSet.getString("reception_description"),
                        resultSet.getInt("times_a_day"),
                        resultSet.getInt("duration_in_days"));
                courses.add(course);
            }
            return courses;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
}
