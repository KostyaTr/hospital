package com.github.KostyaTr.hospital.dao.impl.display;

import com.github.KostyaTr.hospital.dao.DataSource;
import com.github.KostyaTr.hospital.dao.display.DoctorSpecialityDeptDao;
import com.github.KostyaTr.hospital.model.display.DoctorSpecialityDept;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class DefaultDoctorSpecialityDeptDao implements DoctorSpecialityDeptDao {
    private static class SingletonHolder {
        static final DoctorSpecialityDeptDao HOLDER_INSTANCE = new DefaultDoctorSpecialityDeptDao();
    }

    public static DoctorSpecialityDeptDao getInstance() {
        return DefaultDoctorSpecialityDeptDao.SingletonHolder.HOLDER_INSTANCE;
    }

    @Override
    public List<DoctorSpecialityDept> getDoctors() {
        final String sql = "select doctor.id, first_name, last_name, phone_number, email, group_concat(speciality_name, '') as specialities, department_name from doctor \n" +
                "join user on doctor.user_id = user.id\n" +
                "join doctor_speciality on doctor.id = doctor_speciality.doctor_id\n" +
                "join speciality on speciality.id = doctor_speciality.speciality_id\n" +
                "join department on dept_id = department.id\n" +
                "group by doctor.id;";

        try (Connection connection = DataSource.getInstance().getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(sql);
             ResultSet resultSet = preparedStatement.executeQuery()){
            return getDoctorSpecialityDept(resultSet);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public DoctorSpecialityDept getDoctorByUserId(Long userId) {
        final String sql = "select doctor.id, first_name, last_name, phone_number, email, group_concat(speciality_name, '') as specialities,   department_name from doctor \n" +
                "join user on doctor.user_id = user.id\n" +
                "join doctor_speciality on doctor.id = doctor_speciality.doctor_id\n" +
                "join speciality on speciality.id = doctor_speciality.speciality_id\n" +
                "join department on dept_id = department.id\n" +
                "where doctor.user_id  = ?\n" +
                "group by doctor.id;";
        return getDoctorSpecialityDept(userId, sql);
    }

    private DoctorSpecialityDept getDoctorSpecialityDept(Long id, String sql) {
        try (Connection connection = DataSource.getInstance().getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(sql)){
            preparedStatement.setLong(1, id);
            try(ResultSet resultSet = preparedStatement.executeQuery()){
                if (resultSet.next()){
                    return new DoctorSpecialityDept(
                            resultSet.getLong("id"),
                            resultSet.getString("first_name"),
                            resultSet.getString("last_name"),
                            resultSet.getString("phone_number"),
                            resultSet.getString("email"),
                            resultSet.getString("specialities"),
                            resultSet.getString("department_name")
                    );
                }
                return null;
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public DoctorSpecialityDept getDoctorById(Long doctorId) {
        final String sql  = "select doctor.id, first_name, last_name, phone_number, email, group_concat(speciality_name, '') as specialities,   department_name from doctor \n" +
                "join user on doctor.user_id = user.id\n" +
                "join doctor_speciality on doctor.id = doctor_speciality.doctor_id\n" +
                "join speciality on speciality.id = doctor_speciality.speciality_id\n" +
                "join department on dept_id = department.id\n" +
                "where  doctor.id  = ?\n" +
                "group by doctor.id;\n";

        return getDoctorSpecialityDept(doctorId, sql);
    }

    @Override
    public List<DoctorSpecialityDept> getDoctorsBySpeciality(Long specialityId) {
        final String sql = "select doctor.id, first_name, last_name, phone_number, email, group_concat(speciality_name, '') as specialities,   department_name from doctor \n" +
                "join user on doctor.user_id = user.id\n" +
                "join doctor_speciality on doctor.id = doctor_speciality.doctor_id\n" +
                "join speciality on speciality.id = doctor_speciality.speciality_id\n" +
                "join department on dept_id = department.id\n" +
                "where  speciality.id  = ?\n" +
                "group by doctor.id;\n";

        try (Connection connection = DataSource.getInstance().getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(sql)){
            preparedStatement.setLong(1, specialityId);
            try(ResultSet resultSet = preparedStatement.executeQuery()){
                return getDoctorSpecialityDept(resultSet);
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    private List<DoctorSpecialityDept> getDoctorSpecialityDept(ResultSet resultSet) throws SQLException {
        final List<DoctorSpecialityDept> medDoctors = new ArrayList<>();
        while (resultSet.next()){
            final DoctorSpecialityDept medDoctor = new DoctorSpecialityDept(
                    resultSet.getLong("id"),
                    resultSet.getString("first_name"),
                    resultSet.getString("last_name"),
                    resultSet.getString("phone_number"),
                    resultSet.getString("email"),
                    resultSet.getString("specialities"),
                    resultSet.getString("department_name")
            );
            medDoctors.add(medDoctor);
        }
        return medDoctors;
    }
}
