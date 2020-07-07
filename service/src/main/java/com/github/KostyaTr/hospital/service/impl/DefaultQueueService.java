package com.github.KostyaTr.hospital.service.impl;

import com.github.KostyaTr.hospital.dao.GuestPatientDao;
import com.github.KostyaTr.hospital.dao.PatientDao;
import com.github.KostyaTr.hospital.dao.impl.DefaultGuestPatientDao;
import com.github.KostyaTr.hospital.dao.impl.DefaultPatientDao;
import com.github.KostyaTr.hospital.service.QueueService;

import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

public class DefaultQueueService implements QueueService {
    private PatientDao patientDao = DefaultPatientDao.getInstance();
    private GuestPatientDao guestPatientDao = DefaultGuestPatientDao.getInstance();

    private static class SingletonHolder {
        static final QueueService HOLDER_INSTANCE = new DefaultQueueService();
    }

    public static QueueService getInstance() {
        return DefaultQueueService.SingletonHolder.HOLDER_INSTANCE;
    }


    @Override
    public int getCouponNum(Long doctorId, LocalDate visitDate) {
        int day = visitDate.getDayOfMonth();
        int patientCoupon = patientDao.getLatestCouponToDoctorByDay(doctorId, day);
        int guestPatientCoupon = guestPatientDao.getLatestCouponToDoctorByDay(doctorId, day);
        if (patientCoupon >= guestPatientCoupon) {
            return patientCoupon + 1;
        } else {
            return guestPatientCoupon + 1;
        }
    }

    @Override
    public List<LocalDate> getAvailableDays(Long doctorId) {
        int workdays = 5;
        List<LocalDate> availableDays = new ArrayList<>(5);
        LocalDate now = LocalDate.now();
        for (int i = 0; i < workdays; i++) {
            if (now.getDayOfWeek().equals(DayOfWeek.SATURDAY)) {
                now = now.plusDays(2);
            } else if (now.getDayOfWeek().equals(DayOfWeek.SUNDAY)) {
                now = now.plusDays(1);
            }
            int day = now.getDayOfMonth();
            final LocalDateTime latestTimeToDoctorByDay = patientDao.getLatestTimeToDoctorByDay(doctorId, day);
            final LocalDateTime latestGuestTimeToDoctorByDay = guestPatientDao.getLatestTimeToDoctorByDay(doctorId, day);

            if (latestGuestTimeToDoctorByDay == null && latestTimeToDoctorByDay == null) {
                if (LocalDateTime.now().getDayOfMonth() == day) {
                    if (workday(LocalDateTime.now().withDayOfMonth(day))) {
                        availableDays.add(getAvailableDay(day));
                    } else {
                        workdays++;
                    }
                } else {
                    availableDays.add(getAvailableDay(day));
                }
            } else if (latestGuestTimeToDoctorByDay == null) {
                if (workday(latestTimeToDoctorByDay)) {
                    availableDays.add(getAvailableDay(day));
                } else {
                    workdays++;
                }
            } else if (latestTimeToDoctorByDay == null || latestGuestTimeToDoctorByDay.compareTo(latestTimeToDoctorByDay) < 0) {
                if (workday(latestGuestTimeToDoctorByDay)) {
                    availableDays.add(getAvailableDay(day));
                } else {
                    workdays++;
                }
            } else {
                if (workday(latestTimeToDoctorByDay)) {
                    availableDays.add(getAvailableDay(day));
                } else {
                    workdays++;
                }
            }
            now = now.plusDays(1);
        }
        return availableDays;
    }

    @Override
    public LocalDateTime getVisitTime(Long doctorId, LocalDate day) {
        final LocalDateTime latestTimeToDoctorByDay = patientDao.getLatestTimeToDoctorByDay(doctorId, day.getDayOfMonth());

        final LocalDateTime latestGuestTimeToDoctorByDay = guestPatientDao.getLatestTimeToDoctorByDay(doctorId, day.getDayOfMonth());

        if (latestGuestTimeToDoctorByDay == null && latestTimeToDoctorByDay == null) {
            if (day.getDayOfMonth() == LocalDate.now().getDayOfMonth() && day.getMonthValue() == LocalDate.now().getMonthValue()) {
                if (workday(LocalDateTime.now().withDayOfMonth(day.getDayOfMonth()).withMonth(day.getMonthValue()))) {
                    return LocalDateTime.now().plusMinutes(20);
                } else {
                    return null;
                }
            } else {
                return day.atStartOfDay().withHour(9).withMinute(0);
            }
        } else if (latestGuestTimeToDoctorByDay == null) {
            if (workday(latestTimeToDoctorByDay)) {
                return latestTimeToDoctorByDay.plusMinutes(20);
            }
        } else if (latestTimeToDoctorByDay == null || latestGuestTimeToDoctorByDay.compareTo(latestTimeToDoctorByDay) > 0) {
            if (workday(latestGuestTimeToDoctorByDay)) {
                return latestGuestTimeToDoctorByDay.plusMinutes(20);
            }
        } else {
            if (workday(latestTimeToDoctorByDay)) {
                return latestTimeToDoctorByDay.plusMinutes(20);
            }
        }
        return null;
    }

    private LocalDate getAvailableDay(int day) {
        return LocalDate.now().withDayOfMonth(day);
    }

    private boolean workday(LocalDateTime dateTime) {
        return dateTime.getHour() <= 16 && (dateTime.getHour() != 16 || dateTime.getMinute() <= 20);
    }
}
