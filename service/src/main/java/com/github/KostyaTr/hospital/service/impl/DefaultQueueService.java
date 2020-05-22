package com.github.KostyaTr.hospital.service.impl;

import com.github.KostyaTr.hospital.dao.GuestPatientDao;
import com.github.KostyaTr.hospital.dao.PatientDao;
import com.github.KostyaTr.hospital.dao.impl.DefaultGuestPatientDao;
import com.github.KostyaTr.hospital.dao.impl.DefaultPatientDao;
import com.github.KostyaTr.hospital.service.QueueService;

import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.ArrayList;
import java.util.Date;
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
        if (patientCoupon >= guestPatientCoupon){
            return patientCoupon + 1;
        } else {
            return guestPatientCoupon + 1;
        }
    }

    @Override
    public List<LocalDate> getAvailableDays(Long doctorId){
        int workdays = 5;
        List<LocalDate> availableDays = new ArrayList<>(5);
        int day = LocalDate.now().getDayOfMonth();
        for (int i = 0; i < workdays; i++) {
            if (LocalDateTime.now().withDayOfMonth(day).getDayOfWeek().equals(DayOfWeek.SATURDAY)){
                day+= 2;
            } else if (LocalDateTime.now().withDayOfMonth(day).getDayOfWeek().equals(DayOfWeek.SUNDAY)){
                day++;
            }
            final Date latestTimeToDoctorByDay = patientDao.getLatestTimeToDoctorByDay(doctorId, day);
            LocalDateTime latestPatientVisitDate;
            if (latestTimeToDoctorByDay != null){
                latestPatientVisitDate = latestTimeToDoctorByDay.toInstant()
                        .atZone(ZoneId.systemDefault())
                        .toLocalDateTime();
            } else {
                latestPatientVisitDate = null;
            }

            final Date latestGuestTimeToDoctorByDay = guestPatientDao.getLatestTimeToDoctorByDay(doctorId, day);
            LocalDateTime latestGuestPatientVisitDate;
            if (latestGuestTimeToDoctorByDay != null){
                latestGuestPatientVisitDate = latestGuestTimeToDoctorByDay.toInstant()
                        .atZone(ZoneId.systemDefault())
                        .toLocalDateTime();
            } else {
                latestGuestPatientVisitDate = null;
            }

            if (latestGuestPatientVisitDate == null && latestPatientVisitDate == null){
                if (LocalDateTime.now().getDayOfMonth() == day){
                    if (workday(LocalDateTime.now().withDayOfMonth(day))){
                        availableDays.add(getAvailableDay(day));
                    } else {
                        workdays++;
                    }
                } else {
                    availableDays.add(getAvailableDay(day));
                }
            } else if(latestGuestPatientVisitDate == null){
                if (workday(latestPatientVisitDate)){
                    availableDays.add(getAvailableDay(day));
                }
            } else if (latestPatientVisitDate == null || latestGuestPatientVisitDate.compareTo(latestPatientVisitDate) < 0){
                if (workday(latestGuestPatientVisitDate)){
                    availableDays.add(getAvailableDay(day));
                }
            } else {
                if (workday(latestPatientVisitDate)){
                    availableDays.add(getAvailableDay(day));
                }
            }
            day = LocalDate.now().withDayOfMonth(day).plusDays(1).getDayOfMonth();
        }
        return availableDays;
    }

    @Override
    public LocalDateTime getVisitTime(Long doctorId, LocalDate day) {
        final Date latestTimeToDoctorByDay = patientDao.getLatestTimeToDoctorByDay(doctorId, day.getDayOfMonth());
        LocalDateTime latestPatientVisitDate;
        if (latestTimeToDoctorByDay != null){
            latestPatientVisitDate = latestTimeToDoctorByDay.toInstant()
                    .atZone(ZoneId.systemDefault())
                    .toLocalDateTime();
        } else {
            latestPatientVisitDate = null;
        }

        final Date latestGuestTimeToDoctorByDay = guestPatientDao.getLatestTimeToDoctorByDay(doctorId, day.getDayOfMonth());
        LocalDateTime latestGuestPatientVisitDate;
        if (latestGuestTimeToDoctorByDay != null){
            latestGuestPatientVisitDate = latestGuestTimeToDoctorByDay.toInstant()
                    .atZone(ZoneId.systemDefault())
                    .toLocalDateTime();
        } else {
            latestGuestPatientVisitDate = null;
        }

        if (latestGuestPatientVisitDate == null && latestPatientVisitDate == null){
            if (day.getDayOfMonth() == LocalDate.now().getDayOfMonth() && day.getMonthValue() == LocalDate.now().getMonthValue()){
                if (workday(LocalDateTime.now().withDayOfMonth(day.getDayOfMonth()).withMonth(day.getMonthValue()))){
                    return LocalDateTime.now().plusMinutes(20);
                } else {
                    return null;
                }
            } else {
                return day.atStartOfDay().withHour(9).withMinute(0);
            }
        } else if(latestGuestPatientVisitDate == null){
            if (workday(latestPatientVisitDate)){
                return latestPatientVisitDate.plusMinutes(20);
            }
        } else if (latestPatientVisitDate == null || latestGuestPatientVisitDate.compareTo(latestPatientVisitDate) > 0){
            if (workday(latestGuestPatientVisitDate)){
                return latestGuestPatientVisitDate.plusMinutes(20);
            }
        } else {
            if (workday(latestPatientVisitDate)){
                return latestPatientVisitDate.plusMinutes(20);
            }
        }
        return null;
    }

    private LocalDate getAvailableDay(int day) {
        return LocalDate.now().withDayOfMonth(day);
    }

    private boolean workday(LocalDateTime dateTime){
        return dateTime.getHour() <= 16 && (dateTime.getHour() != 16 || dateTime.getMinute() <= 20);
    }
}
