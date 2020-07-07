package com.github.KostyaTr.hospital.dao;

import com.github.KostyaTr.hospital.dao.converter.ChamberConverter;
import com.github.KostyaTr.hospital.dao.converter.InpatientConverter;
import com.github.KostyaTr.hospital.dao.converter.MedDoctorConverter;
import com.github.KostyaTr.hospital.dao.entity.*;
import com.github.KostyaTr.hospital.dao.impl.DefaultInpatientDao;
import com.github.KostyaTr.hospital.model.*;
import org.hibernate.Session;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

import java.time.LocalDate;
import java.util.Collections;
import java.util.List;

public class DefaultInpatientDaoTest {
    private InpatientDao inpatientDao = DefaultInpatientDao.getInstance();

    @Test
    void getInpatients(){
        Session session = HibernateUtil.getSession();
        session.beginTransaction();
        final DepartmentEntity departmentCheck = new DepartmentEntity(null, "getInpatientsCheck", 1, 1, null, null);
        session.save(departmentCheck);
        final ChamberEntity chamber = new ChamberEntity(null, 1, departmentCheck, true, 5, 1, 5, null);
        session.save (chamber);
        final UserEntity user = new UserEntity(null, "getInpatientsCheck", "getInpatientsCheck", "getInpatientsCheck", "getInpatientsCheck", null, null, null);
        final UserEntity userInpatient1 = new UserEntity(null, "getInpatientsCheck1", "getInpatientsCheck1", "getInpatientsCheck1", "getInpatientsCheck1", null, null, null);
        final UserEntity userInpatient2 = new UserEntity(null, "getInpatientsCheck2", "getInpatientsCheck2", "getInpatientsCheck2", "getInpatientsCheck2", null, null, null);
        session.save(user);
        session.save(userInpatient1);
        session.save(userInpatient2);
        final MedDoctorEntity doctor = new MedDoctorEntity(null, user, departmentCheck, false, null, null, null, null);
        session.save(doctor);
        final SpecialityEntity speciality = new SpecialityEntity(null, "getInpatientsCheck", Collections.singletonList(doctor), null);
        session.save(speciality);
        final MedicalServiceEntity medicalService = new MedicalServiceEntity(null, "getInpatientsCheck", speciality, 1d, null, null);
        session.save(medicalService);

        session.save(new InpatientEntity(null, userInpatient1, doctor, chamber, "", null ,Status.GOOD,  LocalDate.now()));
        session.save(new InpatientEntity(null, userInpatient2, doctor, chamber, "", null, Status.BAD,  LocalDate.now()));
        session.getTransaction().commit();

        final List<Inpatient> patients = inpatientDao.getPatients();
        assertFalse(patients.isEmpty());
        assertEquals(patients.get(patients.size() - 1).getFirstName(), "getInpatientsCheck2");
    }

    @Test
    void removeInpatient(){
        Session session = HibernateUtil.getSession();
        session.beginTransaction();
        final DepartmentEntity departmentCheck = new DepartmentEntity(null, "removeInpatientCheck", 1, 1, null, null);
        session.save(departmentCheck);
        final ChamberEntity chamber = new ChamberEntity(null, 1, departmentCheck, true, 5, 1, 5, null);
        session.save (chamber);
        final UserEntity user = new UserEntity(null, "removeInpatientCheck", "removeInpatientCheck", "removeInpatientCheck", "removePatientCheck", null, null, null);
        final UserEntity userInpatient1 = new UserEntity(null, "removeInpatientCheck1", "removeInpatientCheck1", "removeInpatientCheck1", "removeInpatientCheck1", null, null, null);
        session.save(user);
        session.save(userInpatient1);
        final MedDoctorEntity doctor = new MedDoctorEntity(null, user, departmentCheck, false, null, null, null, null);
        session.save(doctor);
        final SpecialityEntity speciality = new SpecialityEntity(null, "removeInpatientCheck", Collections.singletonList(doctor), null);
        session.save(speciality);
        final MedicalServiceEntity medicalService = new MedicalServiceEntity(null, "removeInpatientCheck", speciality, 1d, null, null);
        session.save(medicalService);

        Long patientId = (Long) session.save(new InpatientEntity(null, userInpatient1, doctor, chamber, "", null, Status.BAD, LocalDate.now()));
        session.getTransaction().commit();

        assertNotNull(inpatientDao.getInpatientById(patientId));
    }

    @Test
    void getInpatientById(){
        Session session = HibernateUtil.getSession();
        session.beginTransaction();
        final DepartmentEntity departmentCheck = new DepartmentEntity(null, "getInpatientCheck", 1, 1, null, null);
        session.save(departmentCheck);
        final ChamberEntity chamber = new ChamberEntity(null, 1, departmentCheck, true, 5, 1, 5, null);
        session.save (chamber);
        final UserEntity user = new UserEntity(null, "getInpatientCheck", "getInpatientCheck", "getInpatientCheck", "getInpatientCheck", null, null, null);
        final UserEntity userInpatient1 = new UserEntity(null, "getInpatientCheck1", "getInpatientCheck1", "getInpatientCheck1", "getInpatientCheck1", null, null, null);
        session.save(user);
        session.save(userInpatient1);
        final MedDoctorEntity doctor = new MedDoctorEntity(null, user, departmentCheck, false, null, null, null, null);
        session.save(doctor);
        final SpecialityEntity speciality = new SpecialityEntity(null, "getInpatientCheck", Collections.singletonList(doctor), null);
        session.save(speciality);
        final MedicalServiceEntity medicalService = new MedicalServiceEntity(null, "getInpatientCheck", speciality, 1d, null, null);
        session.save(medicalService);

        Long patientId = (Long) session.save(new InpatientEntity(null, userInpatient1, doctor, chamber, "", null, Status.BAD, LocalDate.now()));
        session.getTransaction().commit();

        assertNotNull(inpatientDao.getInpatientById(patientId));
        assertEquals(inpatientDao.getInpatientById(patientId).getLastName(), "getInpatientCheck1");
        assertNull(inpatientDao.getInpatientById(0L));
    }

    @Test
    void addInpatient(){
        Session session = HibernateUtil.getSession();
        session.beginTransaction();
        final DepartmentEntity departmentCheck = new DepartmentEntity(null, "addInpatientCheck", 1, 1, null, null);
        session.save(departmentCheck);
        final ChamberEntity chamber = new ChamberEntity(null, 1, departmentCheck, true, 5, 1, 5, null);
        session.save (chamber);
        final UserEntity user = new UserEntity(null, "addInpatientCheck", "addInpatientCheck", "addInpatientCheck", "addInpatientCheck", null, null, null);
        session.save(user);
        final SpecialityEntity speciality = new SpecialityEntity(null, "addInpatientCheck", null, null);
        final MedDoctorEntity doctor = new MedDoctorEntity(null, user, departmentCheck, false, null, null, null, null);
        speciality.setDoctors(Collections.singletonList(doctor));
        doctor.setSpecialities(Collections.singletonList(speciality));
        session.save(doctor);
        session.save(speciality);
        final MedicalServiceEntity medicalService = new MedicalServiceEntity(null, "addInpatientCheck", speciality, 1d, null, null);
        session.save(medicalService);
        final UserEntity user1 = new UserEntity(null, "addInpatientCheck1", "addInpatientCheck1",
                "addInpatientCheck1", "addInpatientCheck1", null, null, null);
        Long userId = (Long) session.save(user1);
        session.getTransaction().commit();

        assertNotNull(inpatientDao.addInpatient(
                new Inpatient(
                        userId, "addInpatientCheck1", "addInpatientCheck1",
                        "addInpatientCheck1", "addInpatientCheck1", null,
                        MedDoctorConverter.fromEntity(doctor), ChamberConverter.fromEntity(chamber),
                        "", null, Status.BAD, LocalDate.now()
                )
                )
        );
    }

    @Test
    void updateInpatient(){
        Session session = HibernateUtil.getSession();
        session.beginTransaction();
        final DepartmentEntity departmentCheck = new DepartmentEntity(null, "updateInpatient", 1, 1, null, null);
        session.save(departmentCheck);
        final ChamberEntity chamber = new ChamberEntity(null, 1, departmentCheck, true, 5, 1, 5, null);
        session.save (chamber);
        final UserEntity user = new UserEntity(null, "updateInpatient", "updateInpatient", "updateInpatient", "updateInpatient", null, null, null);
        final UserEntity userInpatient1 = new UserEntity(null, "updateInpatient1", "updateInpatient1", "updateInpatient1", "updateInpatient1", null, null, null);
        final UserEntity userInpatient2 = new UserEntity(null, "updateInpatient2", "updateInpatient2", "updateInpatient2", "updateInpatient2", null, null, null);
        session.save(user);
        session.save(userInpatient1);
        session.save(userInpatient2);
        final MedDoctorEntity doctor = new MedDoctorEntity(null, user, departmentCheck, false, null, null, null, null);
        final SpecialityEntity speciality = new SpecialityEntity(null, "updateInpatient", null, null);
        speciality.setDoctors(Collections.singletonList(doctor));
        doctor.setSpecialities(Collections.singletonList(speciality));
        session.save(doctor);
        session.save(speciality);
        final MedicalServiceEntity medicalService = new MedicalServiceEntity(null, "updateInpatient", speciality, 1d, null, null);
        session.save(medicalService);

        final InpatientEntity inpatient = new InpatientEntity(null, userInpatient1, doctor, chamber, "", null, Status.BAD, LocalDate.now());
        Long inpatientId = (Long) session.save(inpatient);
        session.getTransaction().commit();

        inpatient.setDiagnose("new Diagnose");
        inpatient.setStatus(Status.GOOD);
        inpatientDao.updateInpatient(InpatientConverter.fromEntity(inpatient));

        assertEquals(inpatientDao.getInpatientById(inpatientId).getDiagnose(), "new Diagnose");
        assertEquals(inpatientDao.getInpatientById(inpatientId).getStatus(), Status.GOOD);
    }
}
