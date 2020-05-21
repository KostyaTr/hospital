package com.github.KostyaTr.hospital.dao;

import com.github.KostyaTr.hospital.dao.entity.MedicineEntity;
import com.github.KostyaTr.hospital.dao.impl.DefaultMedicineDao;
import com.github.KostyaTr.hospital.model.Medicine;
import org.hibernate.Session;
import org.junit.jupiter.api.Test;


import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

public class DefaultMedicineDaoTest {
    private MedicineDao medicineDao = DefaultMedicineDao.getInstance();


    @Test
    void getMedicine(){
        Session session = HibernateUtil.getSession();
        session.beginTransaction();
        session.save(new MedicineEntity(null,"firstMedicine", 1d, 1d, 1, 1d, null));
        Long medicineId = (Long) session.save(new MedicineEntity(null,"secondMedicine", 1d, 1d, 1, 1d, null));
        session.getTransaction().commit();
        final List<Medicine> medicines = medicineDao.getMedicine();
        assertFalse(medicines.isEmpty());
        assertEquals(medicines.get(medicines.size() - 1).getMedicineId(), medicineId);
    }

    @Test
    void getMedicineByName(){
        Session session = HibernateUtil.getSession();
        session.beginTransaction();
        session.save(new MedicineEntity(null,"medicineName", 1d, 1d, 1, 1d, null));
        session.getTransaction().commit();
        assertNotNull(medicineDao.getMedicineByName("medicineName"));
        assertNull(medicineDao.getMedicineByName("nonExistingName"));
        assertEquals(medicineDao.getMedicineByName("medicineName").getMedicineName(), "medicineName");
    }

    @Test
    void getMedicineById(){
        Session session = HibernateUtil.getSession();
        session.beginTransaction();
        Long medicineId = (Long) session.save(new MedicineEntity(null,"medicine", 1d, 1d, 1, 1d, null));
        session.getTransaction().commit();
        assertNotNull(medicineDao.getMedicineById(medicineId));
        assertNull(medicineDao.getMedicineById(0L));
        assertEquals(medicineDao.getMedicineById(medicineId).getMedicineName(), "medicine");
    }

}
