package com.github.KostyaTr.hospital.dao;

import com.github.KostyaTr.hospital.dao.config.DaoConfig;
import com.github.KostyaTr.hospital.dao.entity.DischargedPatientEntity;
import com.github.KostyaTr.hospital.model.DischargedPatient;
import com.github.KostyaTr.hospital.model.Status;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(SpringExtension.class)
@ContextConfiguration(classes = DaoConfig.class)
@Transactional
public class DefaultDischargedPatientDaoTest {
    @Autowired
    private DischargedPatientDao dischargedPatientDao;

    @Autowired
    private SessionFactory sessionFactory;

    @BeforeEach
    void delete() {
        Session session = sessionFactory.getCurrentSession();
        session.createQuery("delete DischargedPatientEntity").executeUpdate();
    }

    @Test
    void addDischargedPatient(){
        final DischargedPatient dischargedPatient = new DischargedPatient(null, "addDischargedPatient", "addDischargedPatient", "addDischargedPatient", "addDischargedPatient", "addDischargedPatient", Status.CURED, LocalDate.now(), LocalDate.now());
        assertNotNull(dischargedPatientDao.addDischargedPatient(dischargedPatient));
    }

    @Test
    void getDischargedPatients(){
        Session session = sessionFactory.getCurrentSession();
        for (int i = 0; i < 21; i++) {
            session.save(new DischargedPatientEntity(null,  "#" + i, "DischargedPatient", "DischargedPatient", "DischargedPatient", "DischargedPatient", Status.CURED, LocalDate.now(), LocalDate.now()));
        }
        final List<DischargedPatient> firstTen = dischargedPatientDao.getDischargedPatients(1);
        final List<DischargedPatient> secondTen = dischargedPatientDao.getDischargedPatients(2);
        final List<DischargedPatient> thirdTen = dischargedPatientDao.getDischargedPatients(3);

        assertNotNull(firstTen);
        assertNotNull(secondTen);
        assertNotNull(thirdTen);

        assertEquals(firstTen.size(), 10);
        assertEquals(secondTen.size(), 10);
        assertEquals(thirdTen.size(), 1);

        assertEquals(firstTen.get(0).getPatientName(), "#0");
        assertEquals(firstTen.get(9).getPatientName(), "#9");
        assertEquals(secondTen.get(0).getPatientName(), "#10");
        assertEquals(secondTen.get(9).getPatientName(), "#19");
        assertEquals(thirdTen.get(thirdTen.size() - 1).getPatientName(), "#20");
    }

    @Test
    void getDischargedPatientCount(){
        Session session = sessionFactory.getCurrentSession();
        for (int i = 0; i < 21; i++) {
            session.save(new DischargedPatientEntity(null,  "#" + i, "DischargedPatient", "DischargedPatient", "DischargedPatient", "DischargedPatient", Status.CURED, LocalDate.now(), LocalDate.now()));
        }
        assertEquals(dischargedPatientDao.getDischargedPatientsCount(), 21);
    }
}
