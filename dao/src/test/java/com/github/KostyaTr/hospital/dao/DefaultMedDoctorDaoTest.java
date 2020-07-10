package com.github.KostyaTr.hospital.dao;

import com.github.KostyaTr.hospital.dao.config.DaoConfig;
import com.github.KostyaTr.hospital.dao.entity.SpecialityEntity;
import com.github.KostyaTr.hospital.dao.entity.DepartmentEntity;
import com.github.KostyaTr.hospital.dao.entity.MedDoctorEntity;
import com.github.KostyaTr.hospital.dao.entity.UserEntity;
import com.github.KostyaTr.hospital.model.MedDoctor;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.transaction.annotation.Transactional;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(SpringExtension.class)
@ContextConfiguration(classes = DaoConfig.class)
@Transactional
public class DefaultMedDoctorDaoTest {
    @Autowired
    private SessionFactory sessionFactory;

    @Autowired
    private MedDoctorDao medDoctorDao;

    @Test
    void getDoctors(){
        Session session = sessionFactory.getCurrentSession();
        final DepartmentEntity departmentCheck = new DepartmentEntity(null, "departmentCheckDoctors", 1, 1, null, null);
        session.save(departmentCheck);
        final UserEntity user = new UserEntity(null, "doctorsGetCheck", "doctorsGetCheck", "doctorsGetCheck", "doctorsGetCheck", null, null, null);
        session.save(user);
        final UserEntity user1 = new UserEntity(null, "doctors1GetCheck", "doctors2GetCheck", "doctors2GetCheck", "doctors2GetCheck", null, null, null);
        session.save(user1);
        MedDoctorEntity medDoctorEntity = new MedDoctorEntity(null, user1, departmentCheck, false, null, null, null, null);

        final SpecialityEntity speciality = new SpecialityEntity(null, "doctors1GetCheck", Collections.singletonList(medDoctorEntity), null);
        session.save(speciality);
        medDoctorEntity.setSpecialities(Collections.singletonList(speciality));
        Long doctorId = (Long) session.save(medDoctorEntity);

        List<MedDoctor> doctors = medDoctorDao.getDoctors();
        assertFalse(doctors.isEmpty());
        assertEquals(doctors.get(doctors.size() - 1).getDoctorId(), doctorId);
    }

    @Test
    public void getDoctorById() {
        Session session = sessionFactory.getCurrentSession();
        final DepartmentEntity departmentCheck = new DepartmentEntity(null, "departmentCheckDoctorById", 1, 1, null, null);
        session.save(departmentCheck);
        final UserEntity user = new UserEntity(null, "doctorGetCheck", "doctorGetCheck", "doctorGetCheck", "doctorGetCheck", null, null, null);
        session.save(user);
        MedDoctorEntity medDoctorEntity = new MedDoctorEntity(null, user, departmentCheck, false, null, null, null, null);
        final SpecialityEntity speciality = new SpecialityEntity(null, "doctorGetCheck", Collections.singletonList(medDoctorEntity), null);
        session.save(speciality);
        medDoctorEntity.setSpecialities(Collections.singletonList(speciality));
        Long doctorId = (Long) session.save(medDoctorEntity);

        final MedDoctor doctorById = medDoctorDao.getDoctorById(doctorId);
        assertNotNull(doctorById);
        assertNull(medDoctorDao.getDoctorById(0L));
    }

    @Test
    public void getDoctorByUserId(){
        Session session = sessionFactory.getCurrentSession();
        final DepartmentEntity departmentCheck = new DepartmentEntity(null, "departmentCheckDoctorByUserId", 1, 1, null, null);
        session.save(departmentCheck);
        final UserEntity user = new UserEntity(null, "doctorGetByUserCheck", "doctorGetByUserCheck", "doctorGetByUserCheck", "doctorGetByUserCheck", null, null, null);
        session.save(user);

        MedDoctorEntity medDoctorEntity = new MedDoctorEntity(null, user, departmentCheck, false, null, null, null, null);
        final SpecialityEntity speciality = new SpecialityEntity(null, "doctorGetByUserCheck", Collections.singletonList(medDoctorEntity), null);
        session.save(speciality);
        medDoctorEntity.setSpecialities(Collections.singletonList(speciality));
        session.save(medDoctorEntity);

        assertNotNull(medDoctorDao.getDoctorByUserId(user.getUserId()));
        assertNull(medDoctorDao.getDoctorByUserId(0L));
    }

    @Test
    void getDoctorsBySpeciality(){
        Session session = sessionFactory.getCurrentSession();
        final DepartmentEntity departmentCheck = new DepartmentEntity(null, "departmentCheckDoctorsBySpeciality", 1, 1, null, null);
        session.save(departmentCheck);
        final UserEntity user = new UserEntity(null, "doctorsGetCheckBySpeciality", "doctorsGetCheckBySpeciality", "doctorsGetCheckBySpeciality", "doctorsGetCheckBySpeciality", null, null, null);
        session.save(user);
        final UserEntity user1 = new UserEntity(null, "doctorsGetCheckBySpeciality2", "doctorsGetCheckBySpeciality2", "doctorsGetCheckBySpeciality2", "doctorsGetCheckBySpeciality2", null, null, null);
        session.save(user1);
        final SpecialityEntity speciality = new SpecialityEntity(null, "doctorsGetCheckBySpeciality", null, null);
        final MedDoctorEntity medDoctorEntity = new MedDoctorEntity(null, user, departmentCheck, false, null, null, null, null);
        final MedDoctorEntity medDoctorEntity1 = new MedDoctorEntity(null, user1, departmentCheck, false, null, null, null, null);
        speciality.setDoctors(Arrays.asList(medDoctorEntity, medDoctorEntity1));
        medDoctorEntity.setSpecialities(Collections.singletonList(speciality));
        medDoctorEntity1.setSpecialities(Collections.singletonList(speciality));
        session.save(medDoctorEntity);
        Long doctorId = (Long) session.save(medDoctorEntity1);
        Long specialityId = (Long) session.save(speciality);

        List<MedDoctor> doctors = medDoctorDao.getDoctorBySpeciality(specialityId);
        assertFalse(doctors.isEmpty());
        assertEquals(doctors.get(doctors.size() - 1).getDoctorId(), doctorId);
    }
}
