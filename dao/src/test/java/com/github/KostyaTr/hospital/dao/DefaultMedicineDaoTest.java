package com.github.KostyaTr.hospital.dao;

import com.github.KostyaTr.hospital.dao.config.DaoConfig;
import com.github.KostyaTr.hospital.dao.entity.MedicineEntity;
import com.github.KostyaTr.hospital.model.Medicine;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.transaction.annotation.Transactional;


import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(SpringExtension.class)
@ContextConfiguration(classes = DaoConfig.class)
@Transactional
public class DefaultMedicineDaoTest {
    @Autowired
    private SessionFactory sessionFactory;

    @Autowired
    private MedicineDao medicineDao;

    @Test
    void getMedicine(){
        Session session = sessionFactory.getCurrentSession();
        session.save(new MedicineEntity(null,"firstMedicine", 1d, 1d, 1, 1d, null));
        Long medicineId = (Long) session.save(new MedicineEntity(null,"secondMedicine", 1d, 1d, 1, 1d, null));

        final List<Medicine> medicines = medicineDao.getMedicine();
        assertFalse(medicines.isEmpty());
        assertEquals(medicines.get(medicines.size() - 1).getMedicineId(), medicineId);
    }

    @Test
    void getMedicineByName(){
        Session session = sessionFactory.getCurrentSession();

        session.save(new MedicineEntity(null,"medicineName", 1d, 1d, 1, 1d, null));

        assertNotNull(medicineDao.getMedicineByName("medicineName"));
        assertNull(medicineDao.getMedicineByName("nonExistingName"));
        assertEquals(medicineDao.getMedicineByName("medicineName").getMedicineName(), "medicineName");
    }

    @Test
    void getMedicineById(){
        Session session = sessionFactory.getCurrentSession();

        Long medicineId = (Long) session.save(new MedicineEntity(null,"medicine", 1d, 1d, 1, 1d, null));
        session.getTransaction().commit();
        assertNotNull(medicineDao.getMedicineById(medicineId));
        assertNull(medicineDao.getMedicineById(0L));
        assertEquals(medicineDao.getMedicineById(medicineId).getMedicineName(), "medicine");
    }
}
