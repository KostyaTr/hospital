package com.github.KostyaTr.hospital.dao;

import com.github.KostyaTr.hospital.model.Appointment;

import java.util.List;

public interface AppointmentDao {
    List<Appointment> getAppointmentsByUserId(Long userId);
}
