package com.github.KostyaTr.hospital.dao.impl;

import com.github.KostyaTr.hospital.dao.DataSource;
import com.github.KostyaTr.hospital.dao.DoctorSpecialityDao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class DefaultDoctorSpecialityDao implements DoctorSpecialityDao {

    private static class SingletonHolder {
        static final DoctorSpecialityDao HOLDER_INSTANCE = new DefaultDoctorSpecialityDao();
    }

    public static DoctorSpecialityDao getInstance() {
        return DefaultDoctorSpecialityDao.SingletonHolder.HOLDER_INSTANCE;
    }

    @Override
    public List<Long> getDoctorsSpecialities(Long doctorId) {
       final String sql = "select speciality_id from doctor_speciality where doctor_id = ?";

       try (Connection connection = DataSource.getInstance().getConnection();
            PreparedStatement preparedStatement = connection.prepareStatement(sql)){
           preparedStatement.setLong(1, doctorId);
           try(ResultSet resultSet = preparedStatement.executeQuery()) {
               List<Long> specialities = new ArrayList<>();
              while(resultSet.next()){
                  specialities.add(resultSet.getLong("speciality_id"));
              }
              return specialities;
           }
       } catch (SQLException e) {
           throw new RuntimeException(e);
       }
    }
}
