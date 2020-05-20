package com.github.KostyaTr.hospital.dao;

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
        Medicine medicine = new Medicine(null,"medicineForTreatment", 1d, 1d, 1, 1d, null);
        TreatmentCourse treatmentCourse = new TreatmentCourse(null, null, 1d, "desc", 1, 1, null);
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
        final Medicine medicineToAddTreatment = new Medicine(null, "medicineToAddTreatment", 1d, 1d, 1, 1d, null);
        session.save(medicineToAddTreatment);
        session.getTransaction().commit();
        final Long treatmentCourse = treatmentCourseDao.addTreatmentCourse(new TreatmentCourse(null, medicineToAddTreatment, 1d, "desc", 1, 1, null));
        assertNotNull(treatmentCourse);
    }

    @Test
    void removeTreatmentCourse(){
        Session session = HibernateUtil.getSession();
        session.beginTransaction();
        final Medicine medicineToRemoveTreatment = new Medicine(null, "medicineToRemoveTreatment", 1d, 1d, 1, 1d, null);
        session.saveOrUpdate(medicineToRemoveTreatment);
        session.getTransaction().commit();
        final Long treatmentCourse = treatmentCourseDao.addTreatmentCourse(new TreatmentCourse(null, medicineToRemoveTreatment, 1d, "desc", 1, 1, null));
        assertTrue(treatmentCourseDao.removeTreatmentCourseById(treatmentCourse));
    }
}
