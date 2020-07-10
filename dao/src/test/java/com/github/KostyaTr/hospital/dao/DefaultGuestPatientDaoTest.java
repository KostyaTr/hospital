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
import static org.junit.jupiter.api.Assertions.assertFalse;

@ExtendWith(SpringExtension.class)
@ContextConfiguration(classes = DaoConfig.class)
@Transactional
public class DefaultGuestPatientDaoTest {
    @Autowired
    private SessionFactory sessionFactory;

    @Autowired
    private GuestPatientDao guestPatientDao;

    @Test
    void getPatients() {
        Session session = sessionFactory.getCurrentSession();
        final DepartmentEntity departmentCheck = new DepartmentEntity(null, "getGuestPatientsCheck", 1, 1, null, null);
        session.save(departmentCheck);
        final UserEntity user = new UserEntity(null, "getGuestPatientsCheck", "getGuestPatientsCheck", "getGuestPatientsCheck", "getGuestPatientsCheck", null, null, null);
        session.save(user);
        final MedDoctorEntity doctor = new MedDoctorEntity(null, user, departmentCheck, false, null, null, null, null);
        final SpecialityEntity speciality = new SpecialityEntity(null, "getGuestPatientsCheck", Collections.singletonList(doctor), null);
        session.save(speciality);
        doctor.setSpecialities(Collections.singletonList(speciality));
        session.save(doctor);
        final MedicalServiceEntity medicalService = new MedicalServiceEntity(null, "getGuestPatientsCheck", speciality, 1d, null, null);
        session.save(medicalService);

        session.save(new GuestPatientEntity(null, "getGuestPatients1", "getGuestPatients1", "getGuestPatients1", "getGuestPatients1", doctor, 1, medicalService, LocalDateTime.now()));
        session.save(new GuestPatientEntity(null, "getGuestPatients2", "getGuestPatients2", "getGuestPatients2", "getGuestPatients2", doctor, 2, medicalService, LocalDateTime.now()));

        final List<GuestPatient> patients = guestPatientDao.getPatients();
        assertFalse(patients.isEmpty());
        assertEquals(patients.get(patients.size() - 1).getCouponNum(), 2);
    }

    @Test
    void addPatient() {
        Session session = sessionFactory.getCurrentSession();
        final DepartmentEntity departmentCheck = new DepartmentEntity(null, "addGuestPatients", 1, 1, null, null);
        session.save(departmentCheck);
        final UserEntity userEntity = new UserEntity(null, "addGuestPatients", "addGuestPatients", "addGuestPatients", "addGuestPatients", null, null, null);
        session.save(userEntity);
        final MedDoctorEntity doctorEntity = new MedDoctorEntity(null, userEntity, departmentCheck, false, null, null, null, null);
        final SpecialityEntity speciality = new SpecialityEntity(null, "addGuestPatients", Collections.singletonList(doctorEntity), null);
        session.save(doctorEntity);
        speciality.setDoctors(Collections.singletonList(doctorEntity));
        doctorEntity.setSpecialities(Collections.singletonList(speciality));
        session.save(speciality);
        final MedicalServiceEntity medicalServiceEntity = new MedicalServiceEntity(null, "addGuestPatients", speciality, 1d, null, null);
        session.save(medicalServiceEntity);


        assertNotNull(guestPatientDao.addPatient(new GuestPatient(null, "addGuestPatients",
                "addGuestPatients", "addGuestPatients", "addGuestPatients",
                MedDoctorConverter.fromEntity(doctorEntity), 2,
                MedicalServiceConverter.fromEntity(medicalServiceEntity), LocalDateTime.now())));
    }

    @Test
    void getPatientsByDoctorId() {
        Session session = sessionFactory.getCurrentSession();

        final DepartmentEntity departmentCheck = new DepartmentEntity(null, "getGuestPatientByDoctorId", 1, 1, null, null);
        session.save(departmentCheck);
        final UserEntity user = new UserEntity(null, "getGuestPatientByDoctorId", "getGuestPatientByDoctorId", "getGuestPatientByDoctorId", "getGuestPatientByDoctorId", null, null, null);
        session.save(user);
        final MedDoctorEntity doctor = new MedDoctorEntity(null, user, departmentCheck, false, null, null, null, null);
        final SpecialityEntity speciality = new SpecialityEntity(null, "getGuestPatientByDoctorId", Collections.singletonList(doctor), null);
        session.save(speciality);
        doctor.setSpecialities(Collections.singletonList(speciality));
        Long doctorId = (Long) session.save(doctor);
        final MedicalServiceEntity medicalService = new MedicalServiceEntity(null, "getGuestPatientByDoctorId", speciality, 1d, null, null);
        session.save(medicalService);
        session.save(new GuestPatientEntity(null, "getGuestPatientByDoctorId", "getGuestPatientByDoctorId", "getGuestPatientByDoctorId", "getGuestPatientByDoctorId", doctor, 1, medicalService, LocalDateTime.now()));
        session.save(new GuestPatientEntity(null, "getGuestPatientByDoctorId2", "getGuestPatientByDoctorId2", "getGuestPatientByDoctorId2", "getGuestPatientByDoctorId2", doctor, 2, medicalService, LocalDateTime.now()));

        final List<GuestPatient> patients = guestPatientDao.getPatientsByDoctorId(doctorId);
        assertEquals(patients.get(patients.size() - 1).getFirstName(), "getGuestPatientByDoctorId2");
        assertTrue(patients.size() >= 2);
        assertNotNull(patients);
    }

    @Test
    void getLatestCoupon() {
        Session session = sessionFactory.getCurrentSession();

        final DepartmentEntity departmentCheck = new DepartmentEntity(null, "getGuestPatientLatestCoupon", 1, 1, null, null);
        session.save(departmentCheck);
        final UserEntity user = new UserEntity(null, "getGuestPatientLatestCoupon", "getGuestPatientLatestCoupon", "getGuestPatientLatestCoupon", "getGuestPatientLatestCoupon", null, null, null);
        session.save(user);
        final MedDoctorEntity doctor = new MedDoctorEntity(null, user, departmentCheck, false, null, null, null, null);
        final SpecialityEntity speciality = new SpecialityEntity(null, "getGuestPatientLatestCoupon", Collections.singletonList(doctor), null);
        Long doctorId = (Long) session.save(doctor);
        session.save(speciality);
        final MedicalServiceEntity medicalService = new MedicalServiceEntity(null, "getGuestPatientLatestCoupon", speciality, 1d, null, null);
        session.save(medicalService);
        session.save(new GuestPatientEntity(null, "getGuestPatientLatestCoupon", "getGuestPatientLatestCoupon", "getGuestPatientLatestCoupon", "getGuestPatientLatestCoupon", doctor, 1, medicalService, LocalDateTime.now()));
        session.save(new GuestPatientEntity(null, "getGuestPatientLatestCoupon2", "getGuestPatientLatestCoupon2", "getGuestPatientLatestCoupon2", "getGuestPatientLatestCoupon2", doctor, 20, medicalService, LocalDateTime.now()));

        assertEquals(guestPatientDao.getLatestCouponToDoctorByDay(doctorId, LocalDate.now().getDayOfMonth() - 1), 0);
        assertEquals(guestPatientDao.getLatestCouponToDoctorByDay(doctorId, LocalDate.now().getDayOfMonth()), 20);
    }

    @Test
    void getLatestDate() {
        Session session = sessionFactory.getCurrentSession();

        final DepartmentEntity departmentCheck = new DepartmentEntity(null, "getGuestPatientLatestDate", 1, 1, null, null);
        session.save(departmentCheck);
        final UserEntity user = new UserEntity(null, "getGuestPatientLatestDate", "getGuestPatientLatestDate", "getGuestPatientLatestDate", "getGuestPatientLatestDate", null, null, null);
        session.save(user);
        final MedDoctorEntity doctor = new MedDoctorEntity(null, user, departmentCheck, false, null, null, null, null);
        Long doctorId = (Long) session.save(doctor);
        final SpecialityEntity speciality = new SpecialityEntity(null, "getGuestPatientLatestDate", Collections.singletonList(doctor), null);
        session.save(speciality);
        final MedicalServiceEntity medicalService = new MedicalServiceEntity(null, "getGuestPatientLatestDate", speciality,  1d, null, null);
        session.save(medicalService);
        session.save(new GuestPatientEntity(null, "getGuestPatientLatestDate", "getGuestPatientLatestDate", "getGuestPatientLatestDate", "getGuestPatientLatestDate", doctor, 1, medicalService, LocalDateTime.now()));
        final LocalDateTime visitDate = LocalDateTime.now();
        session.save(new GuestPatientEntity(null, "getGuestPatientLatestDate2", "getGuestPatientLatestDate2", "getGuestPatientLatestDate2", "getGuestPatientLatestDate2", doctor, 2, medicalService, visitDate));

        final LocalDateTime expected = guestPatientDao.getLatestTimeToDoctorByDay(doctorId, LocalDate.now().getDayOfMonth());

        assertEquals(expected.withNano(0), visitDate.withNano(0));
    }


}
