package com.github.KostyaTr.hospital.dao.impl.display;

import com.github.KostyaTr.hospital.dao.DataSource;
import com.github.KostyaTr.hospital.dao.display.AppointmentDao;
import com.github.KostyaTr.hospital.model.display.Appointment;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class DefaultAppointmentDao implements AppointmentDao {
    private static class SingletonHolder{
        final static AppointmentDao INSTANCE_HOLDER = new DefaultAppointmentDao();
    }

    public static AppointmentDao getInstance(){return DefaultAppointmentDao.SingletonHolder.INSTANCE_HOLDER;}


    @Override
    public List<Appointment> getAppointmentsByUserId(Long userId) {
       final String sql = "select patient.id, patient.coupon_num, concat(user.first_name, \" \", user.last_name) as doctor_name, medical_service.service_name, patient.visit_date from patient\n" +
               "join medical_service on patient.medical_service_id = medical_service.id\n" +
               "join doctor on patient.doctor_id = doctor.id\n" +
               "join user on doctor.user_id = user.id\n" +
               "where patient.user_id = ?;";

        try(Connection connection = DataSource.getInstance().getConnection();
            PreparedStatement preparedStatement = connection.prepareStatement(sql)) {
            preparedStatement.setLong(1, userId);
            try(ResultSet resultSet = preparedStatement.executeQuery()){
                final List<Appointment> appointments = new ArrayList<>();
                while (resultSet.next()){
                    final Appointment appointment = new Appointment(
                            resultSet.getLong("id"),
                            resultSet.getLong("coupon_num"),
                            resultSet.getString("doctor_name"),
                            resultSet.getString("service_name"),
                            resultSet.getTimestamp("visit_date"));
                    appointments.add(appointment);
                }
                return appointments;
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
}
