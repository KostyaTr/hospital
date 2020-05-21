package com.github.KostyaTr.hospital.dao;

import com.github.KostyaTr.hospital.dao.entity.MedicineEntity;
import com.github.KostyaTr.hospital.dao.entity.TreatmentCourseEntity;
import com.github.KostyaTr.hospital.dao.impl.DefaultTreatmentCourseDao;
import com.github.KostyaTr.hospital.model.*;
import org.hibernate.Session;
import org.junit.jupiter.api.Test;

import java.util.Collections;

import static org.junit.jupiter.api.Assertions.*;

public class DefaultTreatmentCourseDaoTest {
    private TreatmentCourseDao treatmentCourseDao = DefaultTreatmentCourseDao.getInstance();

    @Test
    void getTreatmentCourse(){
        MedicineEntity medicine = new MedicineEntity(null,"medicineForTreatment", 1d, 1d, 1, 1d, null);
        TreatmentCourseEntity treatmentCourse = new TreatmentCourseEntity(null, null, 1d, "desc", 1, 1, null);
        medicine.setTreatmentCourses(Collections.singletonList(treatmentCourse));
        treatmentCourse.setMedicine(medicine);
        Session session = HibernateUtil.getSession();
        session.beginTransaction();
        session.save(medicine);
        session.getTransaction().commit();

        assertNotNull(treatmentCourseDao.getTreatmentCourseById(treatmentCourse.getTreatmentCourseId()));
        assertNull(treatmentCourseDao.getTreatmentCourseById(0L));
    }

    @Test
    void addTreatmentCourse(){
        Session session = HibernateUtil.getSession();
        session.beginTransaction();
        final MedicineEntity medicineToAddTreatment = new MedicineEntity(null, "medicineToAddTreatment", 1d, 1d, 1, 1d, null);
        Long medicineId = (Long) session.save(medicineToAddTreatment);
        session.getTransaction().commit();
        final Long treatmentCourse = treatmentCourseDao.addTreatmentCourse(new TreatmentCourse(medicineId, "medicineToAddTreatment", 1d, 1d, 1, 1d, null, 1d, "desc", 1, null));
        assertNotNull(treatmentCourse);
    }

    @Test
    void removeTreatmentCourse(){
        Session session = HibernateUtil.getSession();
        session.beginTransaction();
        final MedicineEntity medicineToRemoveTreatment = new MedicineEntity(null, "medicineToRemoveTreatment", 1d, 1d, 1, 1d, null);
        Long medicineId = (Long) session.save(medicineToRemoveTreatment);
        session.getTransaction().commit();
        final Long treatmentCourse = treatmentCourseDao.addTreatmentCourse(new TreatmentCourse(medicineId, "medicineToRemoveTreatment", 1d, 1d, 1, 1d, null, 1d, "desc", 1, null));
        assertTrue(treatmentCourseDao.removeTreatmentCourseById(treatmentCourse));
    }
}
