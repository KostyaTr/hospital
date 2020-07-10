package com.github.KostyaTr.hospital.dao;

import com.github.KostyaTr.hospital.dao.config.DaoConfig;
import com.github.KostyaTr.hospital.dao.converter.MedDoctorConverter;
import com.github.KostyaTr.hospital.dao.converter.MedicalServiceConverter;
import com.github.KostyaTr.hospital.dao.entity.*;
import com.github.KostyaTr.hospital.model.*;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.transaction.annotation.Transactional;


import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Collections;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(SpringExtension.class)
@ContextConfiguration(classes = DaoConfig.class)
@Transactional
public class DefaultPatientDaoTest {
    @Autowired
    private SessionFactory sessionFactory;

    @Autowired
    private PatientDao patientDao;

    @Test
    void getPatients(){
        Session session = sessionFactory.getCurrentSession();
        final DepartmentEntity departmentCheck = new DepartmentEntity(null, "getPatientsCheck", 1, 1, null, null);
        session.save(departmentCheck);
        final UserEntity user = new UserEntity(null, "getPatientsCheck", "getPatientsCheck", "getPatientsCheck", "getPatientsCheck", null, null, null);
        final UserEntity userPatient1 = new UserEntity(null, "getPatientsCheck1", "getPatientsCheck1", "getPatientsCheck1", "getPatientsCheck1", null, null, null);
        final UserEntity userPatient2 = new UserEntity(null, "getPatientsCheck2", "getPatientsCheck2", "getPatientsCheck2", "getPatientsCheck2", null, null, null);
        session.save(user);
        session.save(userPatient1);
        session.save(userPatient2);
        final MedDoctorEntity doctor = new MedDoctorEntity(null, user, departmentCheck, false, null, null, null, null);
        final SpecialityEntity speciality = new SpecialityEntity(null, "getPatientsCheck", Collections.singletonList(doctor), null);
        speciality.setDoctors(Collections.singletonList(doctor));
        doctor.setSpecialities(Collections.singletonList(speciality));
        session.save(doctor);
        session.save(speciality);
        final MedicalServiceEntity medicalService = new MedicalServiceEntity(null, "getPatientsCheck", speciality, 1d, null, null);
        session.save(medicalService);

        session.save(new PatientEntity(null, userPatient1, doctor, 1, medicalService, LocalDateTime.now()));
        session.save(new PatientEntity(null, userPatient2, doctor, 2, medicalService, LocalDateTime.now()));

        final List<Patient> patients = patientDao.getPatients();
        assertFalse(patients.isEmpty());
        assertEquals(patients.get(patients.size() - 1).getFirstName(), "getPatientsCheck2");
    }

    @Test
    void getPatientById(){
        Session session = sessionFactory.getCurrentSession();

        final DepartmentEntity departmentCheck = new DepartmentEntity(null, "getPatientById", 1, 1, null, null);
        session.save(departmentCheck);
        final UserEntity user = new UserEntity(null, "getPatientById", "getPatientById", "getPatientById", "getPatientById", null, null, null);
        final UserEntity userPatient1 = new UserEntity(null, "getPatientById1", "getPatientById1", "getPatientById1", "getPatientById1", null, null, null);
        session.save(user);
        session.save(userPatient1);
        final MedDoctorEntity doctor = new MedDoctorEntity(null, user, departmentCheck, false, null, null, null, null);
        final SpecialityEntity speciality = new SpecialityEntity(null, "getPatientById", Collections.singletonList(doctor), null);
        speciality.setDoctors(Collections.singletonList(doctor));
        doctor.setSpecialities(Collections.singletonList(speciality));
        session.save(doctor);
        session.save(speciality);
        final MedicalServiceEntity medicalService = new MedicalServiceEntity(null, "getPatientById", speciality, 1d, null, null);
        session.save(medicalService);

        Long patientId = (Long) session.save(new PatientEntity(null, userPatient1, doctor, 1, medicalService, LocalDateTime.now()));

        assertNotNull(patientDao.getPatientById(patientId));
        assertNull(patientDao.getPatientById(0L));
        assertEquals(patientDao.getPatientById(patientId).getFirstName(), "getPatientById1");
    }

    @Test
    void removePatientById(){
        Session session = sessionFactory.getCurrentSession();

        final DepartmentEntity departmentCheck = new DepartmentEntity(null, "removePatientById", 1, 1, null, null);
        session.save(departmentCheck);
        final UserEntity user = new UserEntity(null, "removePatientById", "removePatientById", "removePatientById", "removePatientById", null, null, null);
        final UserEntity userPatient1 = new UserEntity(null, "removePatientById1", "removePatientById1", "removePatientById1", "removePatientById1", null, null, null);
        session.save(user);
        session.save(userPatient1);
        final MedDoctorEntity doctor = new MedDoctorEntity(null, user, departmentCheck, false, null, null, null, null);
        final SpecialityEntity speciality = new SpecialityEntity(null, "removePatientById", Collections.singletonList(doctor), null);
        speciality.setDoctors(Collections.singletonList(doctor));
        doctor.setSpecialities(Collections.singletonList(speciality));
        session.save(doctor);
        session.save(speciality);
        final MedicalServiceEntity medicalService = new MedicalServiceEntity(null, "removePatientById", speciality, 1d, null, null);
        session.save(medicalService);

        Long patientId = (Long) session.save(new PatientEntity(null, userPatient1, doctor, 1, medicalService, LocalDateTime.now()));

        assertNotNull(patientDao.getPatientById(patientId));
        assertTrue(patientDao.removePatientById(patientId));
    }

    @Test
    void addPatient(){
        Session session = sessionFactory.getCurrentSession();

        final DepartmentEntity departmentCheck = new DepartmentEntity(null, "addPatient", 1, 1, null, null);
        session.save(departmentCheck);
        final UserEntity user = new UserEntity(null, "addPatient", "addPatient", "addPatient", "addPatient", null, null, null);
        final UserEntity userPatient1 = new UserEntity(null, "addPatient1", "addPatient1", "addPatient1", "addPatient1", null, null, null);
        session.save(user);
        Long userId = (Long) session.save(userPatient1);
        final MedDoctorEntity doctor = new MedDoctorEntity(null, user, departmentCheck, false, null, null, null, null);
        final SpecialityEntity speciality = new SpecialityEntity(null, "addPatient", Collections.singletonList(doctor), null);
        speciality.setDoctors(Collections.singletonList(doctor));
        doctor.setSpecialities(Collections.singletonList(speciality));
        session.save(doctor);
        session.save(speciality);
        final MedicalServiceEntity medicalService = new MedicalServiceEntity(null, "addPatient", speciality, 1d, null, null);
        session.save(medicalService);

        assertNotNull(patientDao.addPatient(new Patient(userId,"addPatient1", "addPatient1", "addPatient1", "addPatient1", null,
                MedDoctorConverter.fromEntity(doctor), 1, MedicalServiceConverter.fromEntity(medicalService), LocalDateTime.now())));
    }

    @Test
    void getPatientsByDoctorId(){
        Session session = sessionFactory.getCurrentSession();

        final DepartmentEntity departmentCheck = new DepartmentEntity(null, "getPatientByDoctorId", 1, 1, null, null);
        session.save(departmentCheck);
        final UserEntity user = new UserEntity(null, "getPatientByDoctorId", "getPatientByDoctorId", "getPatientByDoctorId", "getPatientByDoctorId", null, null, null);
        session.save(user);
        final MedDoctorEntity doctor = new MedDoctorEntity(null, user, departmentCheck, false, null, null, null, null);
        final SpecialityEntity speciality = new SpecialityEntity(null, "getPatientByDoctorId", Collections.singletonList(doctor), null);
        speciality.setDoctors(Collections.singletonList(doctor));
        doctor.setSpecialities(Collections.singletonList(speciality));
        Long doctorId = (Long) session.save(doctor);
        session.save(speciality);
        final MedicalServiceEntity medicalService = new MedicalServiceEntity(null, "getPatientByDoctorId", speciality, 1d, null, null);
        session.save(medicalService);
        final UserEntity userPatient1 = new UserEntity(null, "getPatientByDoctorId1", "getPatientByDoctorId1", "getPatientByDoctorId1", "getPatientByDoctorId1", null, null, null);
        session.save(userPatient1);
        session.save(new PatientEntity(null, userPatient1, doctor, 1, medicalService, LocalDateTime.now()));

        final List<Patient> patientsByDoctorId = patientDao.getPatientsByDoctorId(doctorId);
        assertFalse(patientsByDoctorId.isEmpty());
        assertTrue(patientDao.getPatientsByDoctorId(0L).isEmpty());
        assertEquals(patientsByDoctorId.get(patientsByDoctorId.size() - 1).getFirstName(), "getPatientByDoctorId1");
    }

    @Test
    void getLatestCoupon(){
        Session session = sessionFactory.getCurrentSession();

        final DepartmentEntity departmentCheck = new DepartmentEntity(null, "getPatientLatestCoupon", 1, 1, null, null);
        session.save(departmentCheck);
        final UserEntity user = new UserEntity(null, "getPatientLatestCoupon", "getPatientLatestCoupon", "getPatientLatestCoupon", "getPatientLatestCoupon", null, null, null);
        final UserEntity userPatient1 = new UserEntity(null, "getPatientLatestCoupon1", "getPatientLatestCoupon1", "getPatientLatestCoupon1", "getPatientLatestCoupon1", null, null, null);
        final UserEntity userPatient2 = new UserEntity(null, "getPatientLatestCoupon2", "getPatientLatestCoupon2", "getPatientLatestCoupon2", "getPatientLatestCoupon2", null, null, null);
        final UserEntity userPatient3 = new UserEntity(null, "getPatientLatestCoupon2", "getPatientLatestCoupon2", "getPatientLatestCoupon2", "getPatientLatestCoupon2", null, null, null);
        session.save(user);
        session.save(userPatient1);
        session.save(userPatient2);
        session.save(userPatient3);
        final MedDoctorEntity doctor = new MedDoctorEntity(null, user, departmentCheck, false, null, null, null, null);
        final SpecialityEntity speciality = new SpecialityEntity(null, "getPatientLatestCoupon", Collections.singletonList(doctor), null);
        speciality.setDoctors(Collections.singletonList(doctor));
        doctor.setSpecialities(Collections.singletonList(speciality));
        Long doctorId = (Long) session.save(doctor);
        session.save(speciality);
        final MedicalServiceEntity medicalService = new MedicalServiceEntity(null, "getPatientLatestCoupon", speciality, 1d, null, null);
        session.save(medicalService);
        session.save(new PatientEntity(null, userPatient1, doctor, 1, medicalService, LocalDateTime.now()));
        session.save(new PatientEntity(null, userPatient2, doctor, 20, medicalService, LocalDateTime.now()));

        assertEquals(patientDao.getLatestCouponToDoctorByDay(doctorId, LocalDate.now().getDayOfMonth() - 1), 0);
        assertEquals(patientDao.getLatestCouponToDoctorByDay(doctorId, LocalDate.now().getDayOfMonth()), 20);
    }
}
