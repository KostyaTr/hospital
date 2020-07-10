package com.github.KostyaTr.hospital.dao;

import com.github.KostyaTr.hospital.dao.config.DaoConfig;
import com.github.KostyaTr.hospital.dao.entity.MedicineEntity;
import com.github.KostyaTr.hospital.dao.entity.TreatmentCourseEntity;
import com.github.KostyaTr.hospital.model.*;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.transaction.annotation.Transactional;

import java.util.Collections;

import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(SpringExtension.class)
@ContextConfiguration(classes = DaoConfig.class)
@Transactional
public class DefaultTreatmentCourseDaoTest {

    @Autowired
    private SessionFactory sessionFactory;

    @Autowired
    private  TreatmentCourseDao treatmentCourseDao;

    @Test
    void getTreatmentCourse(){
        Session session = sessionFactory.getCurrentSession();
        MedicineEntity medicine = new MedicineEntity(null,"medicineForTreatment", 1d, 1d, 1, 1d, null);
        TreatmentCourseEntity treatmentCourse = new TreatmentCourseEntity(null, null, 1d, "desc", 1, 1, null);
        medicine.setTreatmentCourses(Collections.singletonList(treatmentCourse));
        treatmentCourse.setMedicine(medicine);
        session.save(medicine);

        assertNotNull(treatmentCourseDao.getTreatmentCourseById(treatmentCourse.getTreatmentCourseId()));
        assertNull(treatmentCourseDao.getTreatmentCourseById(0L));
    }

    @Test
    void addTreatmentCourse(){
        Session session = sessionFactory.getCurrentSession();

        final MedicineEntity medicineToAddTreatment = new MedicineEntity(null, "medicineToAddTreatment", 1d, 1d, 1, 1d, null);
        Long medicineId = (Long) session.save(medicineToAddTreatment);
        session.flush();
        session.clear();
        final Long treatmentCourse = treatmentCourseDao.addTreatmentCourse(new TreatmentCourse(medicineId, "medicineToAddTreatment", 1d, 1d, 1, 1d, null, 1d, "desc", 1, null));
        assertNotNull(treatmentCourse);
    }

    @Test
    void removeTreatmentCourse(){
        Session session = sessionFactory.getCurrentSession();

        final MedicineEntity medicineToRemoveTreatment = new MedicineEntity(null, "medicineToRemoveTreatment", 1d, 1d, 1, 1d, null);
        Long medicineId = (Long) session.save(medicineToRemoveTreatment);
        session.flush();
        session.clear();
        final Long treatmentCourse = treatmentCourseDao.addTreatmentCourse(new TreatmentCourse(medicineId, "medicineToRemoveTreatment", 1d, 1d, 1, 1d, null, 1d, "desc", 1, null));
        assertTrue(treatmentCourseDao.removeTreatmentCourseById(treatmentCourse));
    }
}
