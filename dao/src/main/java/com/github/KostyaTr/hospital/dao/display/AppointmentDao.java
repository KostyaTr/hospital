package com.github.KostyaTr.hospital.dao.display;

import com.github.KostyaTr.hospital.model.display.Appointment;

import java.util.List;

public interface AppointmentDao {
    List<Appointment> getAppointmentsByUserId(Long userId);
}
