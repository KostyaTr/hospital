package com.github.KostyaTr.hospital.dao;

import com.github.KostyaTr.hospital.dao.impl.DefaultInpatientDao;
import com.github.KostyaTr.hospital.model.*;
import org.hibernate.Session;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

import java.util.Collections;
import java.util.Date;
import java.util.List;

public class DefaultInpatientDaoTest {
    private InpatientDao inpatientDao = DefaultInpatientDao.getInstance();

    @Test
    void getInpatients(){
        Session session = HibernateUtil.getSession();
        session.beginTransaction();
        final Department departmentCheck = new Department(null, "getInpatientsCheck", 1, 1, null, null);
        session.save(departmentCheck);
        final Chamber chamber = new Chamber(null, 1, departmentCheck, true, 5, 1, 5, null);
        session.save (chamber);
        final User user = new User(null, "getInpatientsCheck", "getInpatientsCheck", "getInpatientsCheck", "getInpatientsCheck", null, null, null, null, null, null);
        final User userInpatient1 = new User(null, "getInpatientsCheck1", "getInpatientsCheck1", "getInpatientsCheck1", "getInpatientsCheck1", null, null, null, null, null, null);
        final User userInpatient2 = new User(null, "getInpatientsCheck2", "getInpatientsCheck2", "getInpatientsCheck2", "getInpatientsCheck2", null, null, null, null, null, null);
        session.save(user);
        session.save(userInpatient1);
        session.save(userInpatient2);
        final MedDoctor doctor = new MedDoctor(null, user, departmentCheck, false, null, null, null, null);
        session.save(doctor);
        final Speciality speciality = new Speciality(null, "getInpatientsCheck", Collections.singletonList(doctor), null);
        session.save(speciality);
        final MedicalService medicalService = new MedicalService(null, "getInpatientsCheck", speciality, 1L, 1d, null, null);
        session.save(medicalService);

        session.save(new Inpatient(null, userInpatient1, doctor, chamber, "", null, null, Status.BAD, new Date()));
        session.save(new Inpatient(null, userInpatient2, doctor, chamber, "", null, null, Status.BAD, new Date()));
        session.getTransaction().commit();

        final List<Inpatient> patients = inpatientDao.getPatients();
        assertFalse(patients.isEmpty());
        assertEquals(patients.get(patients.size() - 1).getUser().getFirstName(), "getInpatientsCheck2");
    }

    @Test
    void removeInpatient(){
        Session session = HibernateUtil.getSession();
        session.beginTransaction();
        final Department departmentCheck = new Department(null, "removeInpatientCheck", 1, 1, null, null);
        session.save(departmentCheck);
        final Chamber chamber = new Chamber(null, 1, departmentCheck, true, 5, 1, 5, null);
        session.save (chamber);
        final User user = new User(null, "removeInpatientCheck", "removeInpatientCheck", "removeInpatientCheck", "removePatientCheck", null, null, null, null, null, null);
        final User userInpatient1 = new User(null, "removeInpatientCheck1", "removeInpatientCheck1", "removeInpatientCheck1", "removeInpatientCheck1", null, null, null, null, null, null);
        session.save(user);
        session.save(userInpatient1);
        final MedDoctor doctor = new MedDoctor(null, user, departmentCheck, false, null, null, null, null);
        session.save(doctor);
        final Speciality speciality = new Speciality(null, "removeInpatientCheck", Collections.singletonList(doctor), null);
        session.save(speciality);
        final MedicalService medicalService = new MedicalService(null, "removeInpatientCheck", speciality, 1L, 1d, null, null);
        session.save(medicalService);

        Long patientId = (Long) session.save(new Inpatient(null, userInpatient1, doctor, chamber, "", null, null, Status.BAD, new Date()));
        session.getTransaction().commit();

        assertNotNull(inpatientDao.getInpatientById(patientId));
    }

    @Test
    void getInpatientById(){
        Session session = HibernateUtil.getSession();
        session.beginTransaction();
        final Department departmentCheck = new Department(null, "getInpatientCheck", 1, 1, null, null);
        session.save(departmentCheck);
        final Chamber chamber = new Chamber(null, 1, departmentCheck, true, 5, 1, 5, null);
        session.save (chamber);
        final User user = new User(null, "getInpatientCheck", "getInpatientCheck", "getInpatientCheck", "getInpatientCheck", null, null, null, null, null, null);
        final User userInpatient1 = new User(null, "getInpatientCheck1", "getInpatientCheck1", "getInpatientCheck1", "getInpatientCheck1", null, null, null, null, null, null);
        session.save(user);
        session.save(userInpatient1);
        final MedDoctor doctor = new MedDoctor(null, user, departmentCheck, false, null, null, null, null);
        session.save(doctor);
        final Speciality speciality = new Speciality(null, "getInpatientCheck", Collections.singletonList(doctor), null);
        session.save(speciality);
        final MedicalService medicalService = new MedicalService(null, "getInpatientCheck", speciality, 1L, 1d, null, null);
        session.save(medicalService);

        Long patientId = (Long) session.save(new Inpatient(null, userInpatient1, doctor, chamber, "", null, null, Status.BAD, new Date()));
        session.getTransaction().commit();

        assertNotNull(inpatientDao.getInpatientById(patientId));
        assertEquals(inpatientDao.getInpatientById(patientId).getUser().getLastName(), "getInpatientCheck1");
        assertNull(inpatientDao.getInpatientById(0L));
    }

    @Test
    void addInpatient(){
        Session session = HibernateUtil.getSession();
        session.beginTransaction();
        final Department departmentCheck = new Department(null, "addInpatientCheck", 1, 1, null, null);
        session.save(departmentCheck);
        final Chamber chamber = new Chamber(null, 1, departmentCheck, true, 5, 1, 5, null);
        session.save (chamber);
        final User user = new User(null, "addInpatientCheck", "addInpatientCheck", "addInpatientCheck", "addInpatientCheck", null, null, null, null, null, null);
        final User userInpatient1 = new User(null, "addInpatientCheck1", "addInpatientCheck1", "addInpatientCheck1", "addInpatientCheck1", null, null, null, null, null, null);
        session.save(user);
        session.save(userInpatient1);
        final MedDoctor doctor = new MedDoctor(null, user, departmentCheck, false, null, null, null, null);
        session.save(doctor);
        final Speciality speciality = new Speciality(null, "addInpatientCheck", Collections.singletonList(doctor), null);
        session.save(speciality);
        final MedicalService medicalService = new MedicalService(null, "addInpatientCheck", speciality, 1L, 1d, null, null);
        session.save(medicalService);
        session.getTransaction().commit();

        assertNotNull(inpatientDao.addInpatient(new Inpatient(null, userInpatient1, doctor, chamber, "", null, null, Status.BAD, new Date())));
    }

    @Test
    void updateInpatient(){
        Session session = HibernateUtil.getSession();
        session.beginTransaction();
        final Department departmentCheck = new Department(null, "updateInpatient", 1, 1, null, null);
        session.save(departmentCheck);
        final Chamber chamber = new Chamber(null, 1, departmentCheck, true, 5, 1, 5, null);
        session.save (chamber);
        final User user = new User(null, "updateInpatient", "updateInpatient", "updateInpatient", "updateInpatient", null, null, null, null, null, null);
        final User userInpatient1 = new User(null, "updateInpatient1", "updateInpatient1", "updateInpatient1", "updateInpatient1", null, null, null, null, null, null);
        final User userInpatient2 = new User(null, "updateInpatient2", "updateInpatient2", "updateInpatient2", "updateInpatient2", null, null, null, null, null, null);
        session.save(user);
        session.save(userInpatient1);
        session.save(userInpatient2);
        final MedDoctor doctor = new MedDoctor(null, user, departmentCheck, false, null, null, null, null);
        session.save(doctor);
        final Speciality speciality = new Speciality(null, "updateInpatient", Collections.singletonList(doctor), null);
        session.save(speciality);
        final MedicalService medicalService = new MedicalService(null, "updateInpatient", speciality, 1L, 1d, null, null);
        session.save(medicalService);

        final Inpatient inpatient = new Inpatient(null, userInpatient1, doctor, chamber, "", null, null, Status.BAD, new Date());
        Long inpatientId = (Long) session.save(inpatient);
        session.getTransaction().commit();

        final Inpatient inpatient1 = new Inpatient(inpatientId, userInpatient1, doctor, chamber, "new Diagnose", null, null, Status.GOOD, new Date());
        inpatientDao.updateInpatient(inpatient1);

        assertEquals(inpatientDao.getInpatientById(inpatientId).getDiagnose(), "new Diagnose");
        assertEquals(inpatientDao.getInpatientById(inpatientId).getStatus(), Status.GOOD);
    }
}
