package com.github.KostyaTr.hospital.dao.impl;

import com.github.KostyaTr.hospital.dao.HibernateUtil;
import com.github.KostyaTr.hospital.dao.MedicineDao;
import com.github.KostyaTr.hospital.model.Medicine;
import org.hibernate.Session;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.persistence.NoResultException;
import java.util.List;

public class DefaultMedicineDao implements MedicineDao {
    private static final Logger log = LoggerFactory.getLogger(DefaultMedicineDao.class);

    private static class SingletonHolder{
        static final MedicineDao HOLDER_INSTANCE = new DefaultMedicineDao();
    }

    public static MedicineDao getInstance(){
        return DefaultMedicineDao.SingletonHolder.HOLDER_INSTANCE;
    }

    @Override
    public Medicine getMedicineByName(String medicineName) {
        Session session = HibernateUtil.getSession();
        session.beginTransaction();
        Medicine medicine;
        try {
            medicine = session.createQuery("From Medicine where medicine_name = :medicine_name", Medicine.class)
                    .setParameter("medicine_name", medicineName).getSingleResult();
        } catch (NoResultException e){
            medicine = null;
            log.info("Medicine {} wasn't found", medicineName, e);
        }
        session.getTransaction().commit();
        return medicine;
    }

    @Override
    public Medicine getMedicineById(Long medicineId) {
        Session session = HibernateUtil.getSession();
        session.beginTransaction();
        Medicine medicine;
        try {
            medicine = session.get(Medicine.class, medicineId);
        } catch (NoResultException e){
            medicine = null;
            log.info("Medicine {} wasn't found", medicineId, e);
        }
        session.getTransaction().commit();
        return medicine;
    }

    @Override
    public List<Medicine> getMedicine() {
        Session session = HibernateUtil.getSession();
        session.beginTransaction();
        List<Medicine> medicines = session.createQuery("From Medicine").list();
        session.getTransaction().commit();
        return medicines;
    }
}
