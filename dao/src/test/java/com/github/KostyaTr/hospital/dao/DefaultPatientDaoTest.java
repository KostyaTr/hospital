package com.github.KostyaTr.hospital.dao;

import com.github.KostyaTr.hospital.dao.impl.DefaultPatientDao;
import com.github.KostyaTr.hospital.model.*;
import org.hibernate.Session;
import org.junit.jupiter.api.Test;


import java.time.LocalDate;
import java.util.Collections;
import java.util.Date;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

public class DefaultPatientDaoTest {
    private PatientDao patientDao = DefaultPatientDao.getInstance();

    @Test
    void getPatients(){
        Session session = HibernateUtil.getSession();
        session.beginTransaction();
        final Department departmentCheck = new Department(null, "getPatientsCheck", 1, 1, null, null);
        session.save(departmentCheck);
        final User user = new User(null, "getPatientsCheck", "getPatientsCheck", "getPatientsCheck", "getPatientsCheck", null, null, null, null, null, null);
        final User userPatient1 = new User(null, "getPatientsCheck1", "getPatientsCheck1", "getPatientsCheck1", "getPatientsCheck1", null, null, null, null, null, null);
        final User userPatient2 = new User(null, "getPatientsCheck2", "getPatientsCheck2", "getPatientsCheck2", "getPatientsCheck2", null, null, null, null, null, null);
        session.save(user);
        session.save(userPatient1);
        session.save(userPatient2);
        final MedDoctor doctor = new MedDoctor(null, user, departmentCheck, false, null, null, null, null);
        session.save(doctor);
        final Speciality speciality = new Speciality(null, "getPatientsCheck", Collections.singletonList(doctor), null);
        session.save(speciality);
        final MedicalService medicalService = new MedicalService(null, "getPatientsCheck", speciality, 1L, 1d, null, null);
        session.save(medicalService);

        session.save(new Patient(null, userPatient1, doctor, 1, medicalService, new Date()));
        session.save(new Patient(null, userPatient2, doctor, 2, medicalService, new Date()));
        session.getTransaction().commit();

        final List<Patient> patients = patientDao.getPatients();
        assertFalse(patients.isEmpty());
        assertEquals(patients.get(patients.size() - 1).getUser().getFirstName(), "getPatientsCheck2");
    }

    @Test
    void getPatientById(){
        Session session = HibernateUtil.getSession();
        session.beginTransaction();
        final Department departmentCheck = new Department(null, "getPatientById", 1, 1, null, null);
        session.save(departmentCheck);
        final User user = new User(null, "getPatientById", "getPatientById", "getPatientById", "getPatientById", null, null, null, null, null, null);
        final User userPatient1 = new User(null, "getPatientById1", "getPatientById1", "getPatientById1", "getPatientById1", null, null, null, null, null, null);
        session.save(user);
        session.save(userPatient1);
        final MedDoctor doctor = new MedDoctor(null, user, departmentCheck, false, null, null, null, null);
        session.save(doctor);
        final Speciality speciality = new Speciality(null, "getPatientById", Collections.singletonList(doctor), null);
        session.save(speciality);
        final MedicalService medicalService = new MedicalService(null, "getPatientById", speciality, 1L, 1d, null, null);
        session.save(medicalService);

        Long patientId = (Long) session.save(new Patient(null, userPatient1, doctor, 1, medicalService, new Date()));
        session.getTransaction().commit();

        assertNotNull(patientDao.getPatientById(patientId));
        assertNull(patientDao.getPatientById(0L));
        assertEquals(patientDao.getPatientById(patientId).getUser().getFirstName(), "getPatientById1");
    }

    @Test
    void removePatientById(){
        Session session = HibernateUtil.getSession();
        session.beginTransaction();
        final Department departmentCheck = new Department(null, "removePatientById", 1, 1, null, null);
        session.save(departmentCheck);
        final User user = new User(null, "removePatientById", "removePatientById", "removePatientById", "removePatientById", null, null, null, null, null, null);
        final User userPatient1 = new User(null, "removePatientById1", "removePatientById1", "removePatientById1", "removePatientById1", null, null, null, null, null, null);
        session.save(user);
        session.save(userPatient1);
        final MedDoctor doctor = new MedDoctor(null, user, departmentCheck, false, null, null, null, null);
        session.save(doctor);
        final Speciality speciality = new Speciality(null, "removePatientById", Collections.singletonList(doctor), null);
        session.save(speciality);
        final MedicalService medicalService = new MedicalService(null, "removePatientById", speciality, 1L, 1d, null, null);
        session.save(medicalService);

        Long patientId = (Long) session.save(new Patient(null, userPatient1, doctor, 1, medicalService, new Date()));
        session.getTransaction().commit();

        assertNotNull(patientDao.getPatientById(patientId));
        assertTrue(patientDao.removePatientById(patientId));
    }

    @Test
    void addPatient(){
        Session session = HibernateUtil.getSession();
        session.beginTransaction();
        final Department departmentCheck = new Department(null, "addPatient", 1, 1, null, null);
        session.save(departmentCheck);
        final User user = new User(null, "addPatient", "addPatient", "addPatient", "addPatient", null, null, null, null, null, null);
        final User userPatient1 = new User(null, "addPatient1", "addPatient1", "addPatient1", "addPatient1", null, null, null, null, null, null);
        session.save(user);
        session.save(userPatient1);
        final MedDoctor doctor = new MedDoctor(null, user, departmentCheck, false, null, null, null, null);
        session.save(doctor);
        final Speciality speciality = new Speciality(null, "addPatient", Collections.singletonList(doctor), null);
        session.save(speciality);
        final MedicalService medicalService = new MedicalService(null, "addPatient", speciality, 1L, 1d, null, null);
        session.save(medicalService);
        session.getTransaction().commit();

        assertNotNull(patientDao.addPatient(new Patient(null, userPatient1, doctor, 1, medicalService, new Date())));
    }

    @Test
    void getPatientsByDoctorId(){
        Session session = HibernateUtil.getSession();
        session.beginTransaction();
        final Department departmentCheck = new Department(null, "getPatientByDoctorId", 1, 1, null, null);
        session.save(departmentCheck);
        final User user = new User(null, "getPatientByDoctorId", "getPatientByDoctorId", "getPatientByDoctorId", "getPatientByDoctorId", null, null, null, null, null, null);
        session.save(user);
        final MedDoctor doctor = new MedDoctor(null, user, departmentCheck, false, null, null, null, null);
        Long doctorId = (Long) session.save(doctor);
        final Speciality speciality = new Speciality(null, "getPatientByDoctorId", Collections.singletonList(doctor), null);
        session.save(speciality);
        final MedicalService medicalService = new MedicalService(null, "getPatientByDoctorId", speciality, 1L, 1d, null, null);
        session.save(medicalService);
        final User userPatient1 = new User(null, "getPatientByDoctorId1", "getPatientByDoctorId1", "getPatientByDoctorId1", "getPatientByDoctorId1", null, null, null, null, null, null);
        session.save(userPatient1);
        session.save(new Patient(null, userPatient1, doctor, 1, medicalService, new Date()));
        session.getTransaction().commit();

        final List<Patient> patientsByDoctorId = patientDao.getPatientsByDoctorId(doctorId);
        assertFalse(patientsByDoctorId.isEmpty());
        assertTrue(patientDao.getPatientsByDoctorId(0L).isEmpty());
        assertEquals(patientsByDoctorId.get(patientsByDoctorId.size() - 1).getUser().getFirstName(), "getPatientByDoctorId1");
    }

    @Test
    void getLatestCoupon(){
        Session session = HibernateUtil.getSession();
        session.beginTransaction();
        final Department departmentCheck = new Department(null, "getPatientLatestCoupon", 1, 1, null, null);
        session.save(departmentCheck);
        final User user = new User(null, "getPatientLatestCoupon", "getPatientLatestCoupon", "getPatientLatestCoupon", "getPatientLatestCoupon", null, null, null, null, null, null);
        final User userPatient1 = new User(null, "getPatientLatestCoupon1", "getPatientLatestCoupon1", "getPatientLatestCoupon1", "getPatientLatestCoupon1", null, null, null, null, null, null);
        final User userPatient2 = new User(null, "getPatientLatestCoupon2", "getPatientLatestCoupon2", "getPatientLatestCoupon2", "getPatientLatestCoupon2", null, null, null, null, null, null);
        session.save(user);
        session.save(userPatient1);
        session.save(userPatient2);
        final MedDoctor doctor = new MedDoctor(null, user, departmentCheck, false, null, null, null, null);
        Long doctorId = (Long) session.save(doctor);
        final Speciality speciality = new Speciality(null, "getPatientLatestCoupon", Collections.singletonList(doctor), null);
        session.save(speciality);
        final MedicalService medicalService = new MedicalService(null, "getPatientLatestCoupon", speciality, 1L, 1d, null, null);
        session.save(medicalService);
        session.save(new Patient(null, userPatient1, doctor, 1, medicalService, new Date()));
        session.save(new Patient(null, userPatient2, doctor, 20, medicalService, new Date()));
        session.getTransaction().commit();

        assertEquals(patientDao.getLatestCouponToDoctorByDay(doctorId, LocalDate.now().getDayOfMonth()), 20);
    }
}
