package com.github.KostyaTr.hospital.dao;

import com.github.KostyaTr.hospital.dao.impl.DefaultGuestPatientDao;
import com.github.KostyaTr.hospital.model.*;
import org.hibernate.Session;
import org.junit.jupiter.api.Test;


import java.time.LocalDate;
import java.util.Collections;
import java.util.Date;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.assertFalse;

public class DefaultGuestPatientDaoTest {
    private GuestPatientDao guestPatientDao = DefaultGuestPatientDao.getInstance();

    @Test
    void getPatients(){
        Session session = HibernateUtil.getSession();
        session.beginTransaction();
        final Department departmentCheck = new Department(null, "getGuestPatientsCheck", 1, 1, null, null);
        session.save(departmentCheck);
        final User user = new User(null, "getGuestPatientsCheck", "getGuestPatientsCheck", "getGuestPatientsCheck", "getGuestPatientsCheck", null, null, null, null, null, null);
        session.save(user);
        final MedDoctor doctor = new MedDoctor(null, user, departmentCheck, false, null, null, null, null);
        session.save(doctor);
        final Speciality speciality = new Speciality(null, "getGuestPatientsCheck", Collections.singletonList(doctor), null);
        session.save(speciality);
        final MedicalService medicalService = new MedicalService(null, "getGuestPatientsCheck", speciality, 1L, 1d, null, null);
        session.save(medicalService);

        session.save(new GuestPatient(null, "getGuestPatients1","getGuestPatients1","getGuestPatients1","getGuestPatients1", doctor, 1, medicalService, new Date()));
        session.save(new GuestPatient(null, "getGuestPatients2","getGuestPatients2","getGuestPatients2","getGuestPatients2", doctor, 2, medicalService, new Date()));
        session.getTransaction().commit();


        final List<GuestPatient> patients = guestPatientDao.getPatients();
        assertFalse(patients.isEmpty());
        assertEquals(patients.get(patients.size() - 1).getCouponNum(), 2);
    }

    @Test
    void addPatient(){
        Session session = HibernateUtil.getSession();
        session.beginTransaction();
        final Department departmentCheck = new Department(null, "addGuestPatients", 1, 1, null, null);
        session.save(departmentCheck);
        final User user = new User(null, "addGuestPatients", "addGuestPatients", "addGuestPatients", "addGuestPatients", null, null, null, null, null, null);
        session.save(user);
        final MedDoctor doctor = new MedDoctor(null, user, departmentCheck, false, null, null, null, null);
        session.save(doctor);
        final Speciality speciality = new Speciality(null, "addGuestPatients", Collections.singletonList(doctor), null);
        session.save(speciality);
        final MedicalService medicalService = new MedicalService(null, "addGuestPatients", speciality, 1L, 1d, null, null);
        session.save(medicalService);

        session.getTransaction().commit();

        assertNotNull(guestPatientDao.addPatient(new GuestPatient(null, "addGuestPatients","addGuestPatients","addGuestPatients","addGuestPatients", doctor, 2, medicalService, new Date())));
    }

    @Test
    void getPatientsByDoctorId(){
        Session session = HibernateUtil.getSession();
        session.beginTransaction();
        final Department departmentCheck = new Department(null, "getGuestPatientByDoctorId", 1, 1, null, null);
        session.save(departmentCheck);
        final User user = new User(null, "getGuestPatientByDoctorId", "getGuestPatientByDoctorId", "getGuestPatientByDoctorId", "getGuestPatientByDoctorId", null, null, null, null, null, null);
        session.save(user);
        final MedDoctor doctor = new MedDoctor(null, user, departmentCheck, false, null, null, null, null);
        Long doctorId = (Long) session.save(doctor);
        final Speciality speciality = new Speciality(null, "getGuestPatientByDoctorId", Collections.singletonList(doctor), null);
        session.save(speciality);
        final MedicalService medicalService = new MedicalService(null, "getGuestPatientByDoctorId", speciality, 1L, 1d, null, null);
        session.save(medicalService);
        session.save(new GuestPatient(null, "getGuestPatientByDoctorId","getGuestPatientByDoctorId","getGuestPatientByDoctorId","getGuestPatientByDoctorId", doctor, 1, medicalService, new Date()));
        session.save(new GuestPatient(null, "getGuestPatientByDoctorId2","getGuestPatientByDoctorId2","getGuestPatientByDoctorId2","getGuestPatientByDoctorId2", doctor, 2, medicalService, new Date()));
        session.getTransaction().commit();

        final List<GuestPatient> patients = guestPatientDao.getPatientsByDoctorId(doctorId);
        assertEquals(patients.get(patients.size() - 1).getFirstName(), "getGuestPatientByDoctorId2");
        assertTrue(patients.size() >= 2);
        assertNotNull(patients);
    }

    @Test
    void getLatestCoupon(){
        Session session = HibernateUtil.getSession();
        session.beginTransaction();
        final Department departmentCheck = new Department(null, "getGuestPatientLatestCoupon", 1, 1, null, null);
        session.save(departmentCheck);
        final User user = new User(null, "getGuestPatientLatestCoupon", "getGuestPatientLatestCoupon", "getGuestPatientLatestCoupon", "getGuestPatientLatestCoupon", null, null, null, null, null, null);
        session.save(user);
        final MedDoctor doctor = new MedDoctor(null, user, departmentCheck, false, null, null, null, null);
        Long doctorId = (Long) session.save(doctor);
        final Speciality speciality = new Speciality(null, "getGuestPatientLatestCoupon", Collections.singletonList(doctor), null);
        session.save(speciality);
        final MedicalService medicalService = new MedicalService(null, "getGuestPatientLatestCoupon", speciality, 1L, 1d, null, null);
        session.save(medicalService);
        session.save(new GuestPatient(null, "getGuestPatientLatestCoupon","getGuestPatientLatestCoupon","getGuestPatientLatestCoupon","getGuestPatientLatestCoupon", doctor, 1, medicalService, new Date()));
        session.save(new GuestPatient(null, "getGuestPatientLatestCoupon2","getGuestPatientLatestCoupon2","getGuestPatientLatestCoupon2","getGuestPatientLatestCoupon2", doctor, 20, medicalService, new Date()));
        session.getTransaction().commit();

        assertEquals(guestPatientDao.getLatestCouponToDoctorByDay(doctorId, LocalDate.now().getDayOfMonth()), 20);
    }

   /* @Test
    void getLatestDate(){
        Session session = HibernateUtil.getSession();
        session.beginTransaction();
        final Department departmentCheck = new Department(null, "getGuestPatientLatestDate", 1, 1, null);
        session.save(departmentCheck);
        final User user = new User(null, "getGuestPatientLatestDate", "getGuestPatientLatestDate", "getGuestPatientLatestDate", "getGuestPatientLatestDate", null, null, null, null);
        session.save(user);
        final MedDoctor doctor = new MedDoctor(null, user, departmentCheck, false, null, null);
        Long doctorId = (Long) session.save(doctor);
        final Speciality speciality = new Speciality(null, "getGuestPatientLatestDate", Collections.singletonList(doctor), null);
        session.save(speciality);
        final MedicalService medicalService = new MedicalService(null, "getGuestPatientLatestDate", speciality, 1L, 1d, null);
        session.save(medicalService);
        session.save(new GuestPatient(null, "getGuestPatientLatestDate","getGuestPatientLatestDate","getGuestPatientLatestDate","getGuestPatientLatestDate", doctor, 1, medicalService, new Date()));
        final Date visitDate = new Date();
        session.save(new GuestPatient(null, "getGuestPatientLatestDate2","getGuestPatientLatestDate2","getGuestPatientLatestDate2","getGuestPatientLatestDate2", doctor, 2, medicalService, visitDate));
        session.getTransaction().commit();

        final Date expected = new Date(guestPatientDao.getLatestTimeToDoctorByDay(doctorId, LocalDate.now().getDayOfMonth()).getTime());
        assertEquals(expected.getTime(), visitDate.getTime());
    }*/
}
