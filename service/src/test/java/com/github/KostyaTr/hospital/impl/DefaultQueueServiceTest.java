package com.github.KostyaTr.hospital.impl;

import com.github.KostyaTr.hospital.dao.GuestPatientDao;
import com.github.KostyaTr.hospital.dao.PatientDao;
import com.github.KostyaTr.hospital.service.impl.DefaultQueueService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDate;
import java.util.Calendar;
import java.util.Date;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class DefaultQueueServiceTest {

    @Mock
    PatientDao patientDao;
    @Mock
    GuestPatientDao guestPatientDao;

    @InjectMocks
    DefaultQueueService queueService;

    @Test
    void couponNumPatientTest(){
        final LocalDate date = LocalDate.now();
        int day = date.getDayOfMonth();

        when(patientDao.getLatestCouponToDoctorByDay((long) 1, day)).thenReturn(16);
        when(guestPatientDao.getLatestCouponToDoctorByDay((long) 1, day)).thenReturn(14);

        assertEquals(17, queueService.getCouponNum((long) 1, date));
    }

    @Test
    void couponNumGuestPatientTest(){
        final LocalDate date = LocalDate.now();
        int day = date.getDayOfMonth();

        when(patientDao.getLatestCouponToDoctorByDay((long) 1, day)).thenReturn(16);
        when(guestPatientDao.getLatestCouponToDoctorByDay((long) 1, day)).thenReturn(18);
        int latestCoupon = queueService.getCouponNum((long) 1, date);

        assertEquals(19, latestCoupon);
    }
}
