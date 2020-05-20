package com.github.KostyaTr.hospital.dao;

import com.github.KostyaTr.hospital.dao.impl.DefaultMedDoctorDao;
import com.github.KostyaTr.hospital.model.Department;
import com.github.KostyaTr.hospital.model.MedDoctor;
import com.github.KostyaTr.hospital.model.User;
import org.hibernate.Session;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

public class DefaultMedDoctorDaoTest {
    private MedDoctorDao medDoctorDao = DefaultMedDoctorDao.getInstance();

    @Test
    void getDoctors(){
        Session session = HibernateUtil.getSession();
        session.beginTransaction();
        final Department departmentCheck = new Department(null, "departmentCheckDoctors", 1, 1, null, null);
        session.save(departmentCheck);
        final User user = new User(null, "doctorsGetCheck", "doctorsGetCheck", "doctorsGetCheck", "doctorsGetCheck", null, null, null, null, null, null);
        session.save(user);
        final User user1 = new User(null, "doctors1GetCheck", "doctors2GetCheck", "doctors2GetCheck", "doctors2GetCheck", null, null, null, null, null, null);
        session.save(user1);
        session.save(new MedDoctor(null, user,departmentCheck,false, null, null, null, null));
        Long doctorId = (Long) session.save(new MedDoctor(null, user1,departmentCheck,false, null, null, null, null));
        session.getTransaction().commit();
        List<MedDoctor> doctors = medDoctorDao.getDoctors();
        assertFalse(doctors.isEmpty());
        assertEquals(doctors.get(doctors.size() - 1).getDoctorId(), doctorId);
    }

    @Test
    public void getDoctorById() {
        Session session = HibernateUtil.getSession();
        session.beginTransaction();
        final Department departmentCheck = new Department(null, "departmentCheckDoctorById", 1, 1, null, null);
        session.save(departmentCheck);
        final User user = new User(null, "doctorGetCheck", "doctorGetCheck", "doctorGetCheck", "doctorGetCheck", null, null, null, null, null, null);
        session.save(user);
        Long doctorId = (Long) session.save(new MedDoctor(null, user,departmentCheck,false, null, null, null, null));
        session.getTransaction().commit();

        assertNotNull(medDoctorDao.getDoctorById(doctorId));
        assertNull(medDoctorDao.getDoctorById(0L));
    }

    @Test
    public void getDoctorByUserId(){
        Session session = HibernateUtil.getSession();
        session.beginTransaction();
        final Department departmentCheck = new Department(null, "departmentCheckDoctorByUserId", 1, 1, null, null);
        session.save(departmentCheck);
        final User user = new User(null, "doctorGetByUserCheck", "doctorGetByUserCheck", "doctorGetByUserCheck", "doctorGetByUserCheck", null, null, null, null, null, null);
        session.save(user);
        session.save(new MedDoctor(null, user,departmentCheck,false, null, null, null, null));
        session.getTransaction().commit();

        assertNotNull(medDoctorDao.getDoctorByUserId(user.getUserId()));
        assertNull(medDoctorDao.getDoctorByUserId(0L));
    }
}
