package com.github.KostyaTr.hospital.dao.impl.display;

import com.github.KostyaTr.hospital.dao.DataSource;
import com.github.KostyaTr.hospital.dao.display.InpatientDao;
import com.github.KostyaTr.hospital.model.display.Inpatient;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class DefaultInpatientDao implements InpatientDao {
    private static class SingletonHolder {
        static final InpatientDao HOLDER_INSTANCE = new DefaultInpatientDao();
    }

    public static InpatientDao getInstance() {
        return DefaultInpatientDao.SingletonHolder.HOLDER_INSTANCE;
    }

    @Override
    public Inpatient getInpatientById(Long patientId) {
        final String sql = "select inpatient.id, concat(user.first_name, \" \", user.last_name) as patient_name, chamber.chamber_id, inpatient.diagnose, medicine.medicine_name," +
                " treatment_course.drug_dose, inpatient.patient_status, inpatient.enrollment_date  from inpatient \n" +
                "join user on user.id = inpatient.user_id\n" +
                "left join treatment_course on treatment_course.id = inpatient.treatment_course_id\n" +
                "left join medicine on medicine.id = treatment_course.medicine_id\n" +
                "join chamber on inpatient.dept_chamber_id = chamber.id\n" +
                "where inpatient.id = ?;";

        try(Connection connection = DataSource.getInstance().getConnection();
            PreparedStatement preparedStatement = connection.prepareStatement(sql)) {
            preparedStatement.setLong(1, patientId);
            try(ResultSet resultSet = preparedStatement.executeQuery()){
                if (resultSet.next()){
                    return new Inpatient(
                            resultSet.getLong("id"),
                            resultSet.getString("patient_name"),
                            resultSet.getLong("chamber_id"),
                            resultSet.getString("diagnose"),
                            resultSet.getString("medicine_name"),
                            resultSet.getDouble("drug_dose"),
                            resultSet.getString("patient_status"),
                            resultSet.getDate("enrollment_date"));
                } else {
                    return null;
                }
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public List<Inpatient> getInpatientsByDoctorId(Long doctorId) {
        final String sql = "select inpatient.id, concat(user.first_name, \" \", user.last_name) as patient_name, chamber.chamber_id, inpatient.diagnose, medicine.medicine_name," +
                " treatment_course.drug_dose, inpatient.patient_status, inpatient.enrollment_date  from inpatient \n" +
                "join user on user.id = inpatient.user_id\n" +
                "left join treatment_course on treatment_course.id = inpatient.treatment_course_id\n" +
                "left join medicine on medicine.id = treatment_course.medicine_id\n" +
                "join chamber on inpatient.dept_chamber_id = chamber.id\n" +
                "where inpatient.doctor_id = ?;";

        try (Connection connection = DataSource.getInstance().getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(sql)) {
            preparedStatement.setLong(1, doctorId);
            try (ResultSet resultSet = preparedStatement.executeQuery()){
                List<Inpatient> inpatients = new ArrayList<>();
                while (resultSet.next()){
                    final Inpatient inpatient = new Inpatient(
                            resultSet.getLong("id"),
                            resultSet.getString("patient_name"),
                            resultSet.getLong("chamber_id"),
                            resultSet.getString("diagnose"),
                            resultSet.getString("medicine_name"),
                            resultSet.getDouble("drug_dose"),
                            resultSet.getString("patient_status"),
                            resultSet.getDate("enrollment_date"));
                    inpatients.add(inpatient);
                }
                return inpatients;
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
}
