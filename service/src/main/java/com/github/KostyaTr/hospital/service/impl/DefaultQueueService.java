package com.github.KostyaTr.hospital.service.impl;

import com.github.KostyaTr.hospital.dao.GuestPatientDao;
import com.github.KostyaTr.hospital.dao.PatientDao;
import com.github.KostyaTr.hospital.dao.impl.DefaultGuestPatientDao;
import com.github.KostyaTr.hospital.dao.impl.DefaultPatientDao;
import com.github.KostyaTr.hospital.service.QueueService;

import java.util.Calendar;
import java.util.Date;

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
    public Long getCouponNum(Long doctorId, Date visitDate) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(visitDate);
        int day = calendar.get(Calendar.DAY_OF_MONTH);

        Long patientCoupon = patientDao.getLatestCouponToDoctorByDay(doctorId, day);
        Long guestPatientCoupon = guestPatientDao.getLatestCouponToDoctorByDay(doctorId, day);
        if (patientCoupon >= guestPatientCoupon){
            return patientCoupon + 1;
        } else {
            return guestPatientCoupon + 1;
        }
    }
}
