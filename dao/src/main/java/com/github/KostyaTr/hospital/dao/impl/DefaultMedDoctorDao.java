package com.github.KostyaTr.hospital.dao.impl;

import com.github.KostyaTr.hospital.dao.DataSource;
import com.github.KostyaTr.hospital.model.MedDoctor;
import com.github.KostyaTr.hospital.dao.MedDoctorDao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class DefaultMedDoctorDao implements MedDoctorDao {

    private final int ONE_ROW_AFFECTED = 1;

    private static class SingletonHolder {
        static final MedDoctorDao HOLDER_INSTANCE = new DefaultMedDoctorDao();
    }

    public static MedDoctorDao getInstance() {
        return DefaultMedDoctorDao.SingletonHolder.HOLDER_INSTANCE;
    }

    @Override
    public List<MedDoctor> getDoctors() {
        final String sql = "select doctor.id, first_name, last_name, phone_number, " +
                "email, group_concat(speciality_name, '') as specialities, department_name, head_of_dept" +
                " from doctor \n" +
                "join user on doctor.user_id = user.id\n" +
                "join doctor_speciality on doctor.id = doctor_speciality.doctor_id\n" +
                "join speciality on speciality.id = doctor_speciality.speciality_id\n" +
                "join department on dept_id = department.id\n" +
                "group by doctor.id;";

        return getMedDoctors(sql);
    }

    @Override
    public MedDoctor getDoctorByUserId(Long userId) {
        final String sql = "select doctor.id, first_name, last_name, phone_number, " +
                "email, group_concat(speciality_name, '') as specialities, department_name, head_of_dept" +
                " from doctor \n" +
                "join user on doctor.user_id = user.id\n" +
                "join doctor_speciality on doctor.id = doctor_speciality.doctor_id\n" +
                "join speciality on speciality.id = doctor_speciality.speciality_id\n" +
                "join department on dept_id = department.id\n" +
                "where doctor.id = ?\n" +
                "group by doctor.id;";

        return getMedDoctor(userId, sql);
    }

    @Override
    public boolean removeDoctorById(Long doctorId) {
        final String sql = "delete from doctor where id = ?";
        try(Connection connection = DataSource.getInstance().getConnection();
            PreparedStatement preparedStatement = connection.prepareStatement(sql)) {
            preparedStatement.setLong(1, doctorId);
            return preparedStatement.executeUpdate() == ONE_ROW_AFFECTED;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public MedDoctor getHeadPhysicianByDepartment(Long deptId) {
        final String sql = "select doctor.id, first_name, last_name, phone_number, email, group_concat(speciality_name, '') as specialities," +
                "department_name, head_of_dept" +
                " from doctor\n" +
                "join user on doctor.user_id = user.id\n" +
                "join doctor_speciality on doctor.id = doctor_speciality.doctor_id\n" +
                "join speciality on speciality.id = doctor_speciality.speciality_id\n" +
                "join department on dept_id = department.id\n" +
                "where head_of_dept = true and dept_id = ?\n" +
                "group by doctor.id;";

        return getMedDoctor(deptId, sql);
    }

    @Override
    public List<MedDoctor> getHeadPhysicians() {
        final String sql = "select doctor.id, first_name, last_name, phone_number, " +
                "email, group_concat(speciality_name, '') as specialities, department_name, head_of_dept" +
                " from doctor \n" +
                "join user on doctor.user_id = user.id\n" +
                "join doctor_speciality on doctor.id = doctor_speciality.doctor_id\n" +
                "join speciality on speciality.id = doctor_speciality.speciality_id\n" +
                "join department on dept_id = department.id\n " +
                "where head_of_dept = true\n" +
                "group by doctor.id;";

        return getMedDoctors(sql);
    }

    private MedDoctor getMedDoctor(Long id, String sql) {
        try(Connection connection = DataSource.getInstance().getConnection();
            PreparedStatement preparedStatement = connection.prepareStatement(sql)) {
            preparedStatement.setLong(1, id);
            try (ResultSet resultSet = preparedStatement.executeQuery()){
                if (resultSet.next()) {
                    return new MedDoctor(
                            resultSet.getLong("id"),
                            resultSet.getString("first_name"),
                            resultSet.getString("last_name"),
                            resultSet.getString("phone_number"),
                            resultSet.getString("email"),
                            resultSet.getString("specialities"),
                            resultSet.getString("department_name"),
                            resultSet.getBoolean("head_of_dept"));
                } else {
                    return null;
                }
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    private List<MedDoctor> getMedDoctors(String sql) {
        try (Connection connection = DataSource.getInstance().getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(sql);
             ResultSet resultSet = preparedStatement.executeQuery()){
            final List<MedDoctor> medDoctors = new ArrayList<>();
            while (resultSet.next()){
                final MedDoctor medDoctor = new MedDoctor(
                        resultSet.getLong("id"),
                        resultSet.getString("first_name"),
                        resultSet.getString("last_name"),
                        resultSet.getString("phone_number"),
                        resultSet.getString("email"),
                        resultSet.getString("specialities"),
                        resultSet.getString("department_name"),
                        resultSet.getBoolean("head_of_dept"));
                medDoctors.add(medDoctor);
            }
            return medDoctors;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
}
