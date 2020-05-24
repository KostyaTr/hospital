package com.github.KostyaTr.hospital.impl;

import com.github.KostyaTr.hospital.dao.GuestPatientDao;
import com.github.KostyaTr.hospital.dao.PatientDao;
import com.github.KostyaTr.hospital.service.impl.DefaultQueueService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Date;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
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
    void couponNumPatientTest() {
        final LocalDate date = LocalDate.now();
        int day = date.getDayOfMonth();

        when(patientDao.getLatestCouponToDoctorByDay((long) 1, day)).thenReturn(16);
        when(guestPatientDao.getLatestCouponToDoctorByDay((long) 1, day)).thenReturn(14);

        assertEquals(17, queueService.getCouponNum((long) 1, date));
    }

    @Test
    void couponNumGuestPatientTest() {
        final LocalDate date = LocalDate.now();
        int day = date.getDayOfMonth();

        when(patientDao.getLatestCouponToDoctorByDay((long) 1, day)).thenReturn(16);
        when(guestPatientDao.getLatestCouponToDoctorByDay((long) 1, day)).thenReturn(18);
        int latestCoupon = queueService.getCouponNum((long) 1, date);

        assertEquals(19, latestCoupon);
    }

    @Test
    void getAvailableDaysGuestPatientNull() {
        LocalDateTime currentTime = LocalDateTime.now();

        if (currentTime.getDayOfWeek().equals(DayOfWeek.SATURDAY)) {
            currentTime = currentTime.plusDays(2);
        } else if (currentTime.getDayOfWeek().equals(DayOfWeek.SUNDAY)) {
            currentTime = currentTime.plusDays(1);
        }
        int day = currentTime.getDayOfMonth();

        when(patientDao.getLatestTimeToDoctorByDay(1L, day)).thenReturn(new Date());
        when(guestPatientDao.getLatestTimeToDoctorByDay(1L, day)).thenReturn(null);

        List<LocalDate> availableDays = queueService.getAvailableDays(1L);
        assertFalse(availableDays.isEmpty());
        assertEquals(availableDays.size(), 5);

        if (currentTime.getHour() <= 16 && (currentTime.getHour() != 16 || currentTime.getMinute() <= 20)) {
            assertEquals(availableDays.get(0).getDayOfMonth(), currentTime.getDayOfMonth());
        } else {
            assertEquals(availableDays.get(0).getDayOfMonth(), currentTime.plusDays(1).getDayOfMonth());
        }

    }

    @Test
    void getAvailableDaysPatientNull() {
        LocalDateTime currentTime = LocalDateTime.now();

        if (currentTime.getDayOfWeek().equals(DayOfWeek.SATURDAY)) {
            currentTime = currentTime.plusDays(2);
        } else if (currentTime.getDayOfWeek().equals(DayOfWeek.SUNDAY)) {
            currentTime = currentTime.plusDays(1);
        }
        int day = currentTime.getDayOfMonth();

        when(guestPatientDao.getLatestTimeToDoctorByDay(1L, day)).thenReturn(new Date());
        when(patientDao.getLatestTimeToDoctorByDay(1L, day)).thenReturn(null);

        List<LocalDate> availableDays = queueService.getAvailableDays(1L);
        assertFalse(availableDays.isEmpty());
        assertEquals(availableDays.size(), 5);

        if (currentTime.getHour() <= 16 && (currentTime.getHour() != 16 || currentTime.getMinute() <= 20)) {
            assertEquals(availableDays.get(0).getDayOfMonth(), currentTime.getDayOfMonth());
        } else {
            assertEquals(availableDays.get(0).getDayOfMonth(), currentTime.plusDays(1).getDayOfMonth());
        }
    }

    @Test
    void getAvailableDaysBothNull() {
        final LocalDateTime currentTime = LocalDateTime.now();

        List<LocalDate> availableDays = queueService.getAvailableDays(1L);
        assertFalse(availableDays.isEmpty());
        assertEquals(5, availableDays.size());
        if (currentTime.getDayOfWeek().equals(DayOfWeek.SATURDAY)) {
            assertEquals(availableDays.get(0).getDayOfMonth(), currentTime.plusDays(2).getDayOfMonth());
        } else if (currentTime.getDayOfWeek().equals(DayOfWeek.SUNDAY)) {
            assertEquals(availableDays.get(0).getDayOfMonth(), currentTime.plusDays(1).getDayOfMonth());
        } else {
            if (currentTime.getHour() <= 16 && (currentTime.getHour() != 16 || currentTime.getMinute() <= 20)) {
                assertEquals(availableDays.get(0).getDayOfMonth(), currentTime.getDayOfMonth());
            } else {
                assertEquals(availableDays.get(0).getDayOfMonth(), currentTime.plusDays(1).getDayOfMonth());
            }
        }
    }

    @Test
    void getAvailableDaysNoneNull() {
        LocalDateTime currentTime = LocalDateTime.now();

        if (currentTime.getDayOfWeek().equals(DayOfWeek.SATURDAY)) {
            currentTime = currentTime.plusDays(2);
        } else if (currentTime.getDayOfWeek().equals(DayOfWeek.SUNDAY)) {
            currentTime = currentTime.plusDays(1);
        }
        int day = currentTime.getDayOfMonth();

        when(guestPatientDao.getLatestTimeToDoctorByDay(1L, day)).thenReturn(new Date());
        when(patientDao.getLatestTimeToDoctorByDay(1L, day)).thenReturn(new Date());

        List<LocalDate> availableDays = queueService.getAvailableDays(1L);
        assertFalse(availableDays.isEmpty());
        assertEquals(availableDays.size(), 5);

        if (currentTime.getHour() <= 16 && (currentTime.getHour() != 16 || currentTime.getMinute() <= 20)) {
            assertEquals(availableDays.get(0).getDayOfMonth(), currentTime.getDayOfMonth());
        } else {
            assertEquals(availableDays.get(0).getDayOfMonth(), currentTime.plusDays(1).getDayOfMonth());
        }
    }

    @Test
    void getVisitTimeNoneNull() {
        final LocalDateTime currentTime = LocalDateTime.now();
        int day = currentTime.getDayOfMonth();

        when(guestPatientDao.getLatestTimeToDoctorByDay(1L, day)).thenReturn(new Date());
        when(patientDao.getLatestTimeToDoctorByDay(1L, day)).thenReturn(new Date());

        LocalDateTime visitTime = queueService.getVisitTime(1L, currentTime.toLocalDate());

        if (currentTime.getHour() <= 16 && (currentTime.getHour() != 16 || currentTime.getMinute() <= 20)) {
            assertEquals(visitTime.getMinute(), currentTime.plusMinutes(20).getMinute());
        } else {
            assertNull(visitTime);
        }
    }

    @Test
    void getVisitTimeGuestPatientNull() {
        final LocalDateTime currentTime = LocalDateTime.now();
        int day = currentTime.getDayOfMonth();

        when(guestPatientDao.getLatestTimeToDoctorByDay(1L, day)).thenReturn(null);
        when(patientDao.getLatestTimeToDoctorByDay(1L, day)).thenReturn(new Date());

        LocalDateTime visitTime = queueService.getVisitTime(1L, currentTime.toLocalDate());

        if (currentTime.getHour() <= 16 && (currentTime.getHour() != 16 || currentTime.getMinute() <= 20)) {
            assertEquals(visitTime.getMinute(), currentTime.plusMinutes(20).getMinute());
        } else {
            assertNull(visitTime);
        }
    }

    @Test
    void getVisitTimeBothNull() {
        final LocalDateTime currentTime = LocalDateTime.now();
        int day = currentTime.getDayOfMonth();

        when(guestPatientDao.getLatestTimeToDoctorByDay(1L, day)).thenReturn(null);
        when(patientDao.getLatestTimeToDoctorByDay(1L, day)).thenReturn(null);

        LocalDateTime visitTime = queueService.getVisitTime(1L, currentTime.toLocalDate());

        if (currentTime.getHour() <= 16 && (currentTime.getHour() != 16 || currentTime.getMinute() <= 20)) {
            assertEquals(visitTime.getMinute(), currentTime.plusMinutes(20).getMinute());
        } else {
            assertNull(visitTime);
        }
    }

    @Test
    void getVisitTimePatientNull() {
        final LocalDateTime currentTime = LocalDateTime.now();
        int day = currentTime.getDayOfMonth();

        when(guestPatientDao.getLatestTimeToDoctorByDay(1L, day)).thenReturn(new Date());
        when(patientDao.getLatestTimeToDoctorByDay(1L, day)).thenReturn(null);

        LocalDateTime visitTime = queueService.getVisitTime(1L, currentTime.toLocalDate());

        if (currentTime.getHour() <= 16 && (currentTime.getHour() != 16 || currentTime.getMinute() <= 20)) {
            assertEquals(visitTime.getMinute(), currentTime.plusMinutes(20).getMinute());
        } else {
            assertNull(visitTime);
        }
    }
}
