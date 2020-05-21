package com.github.KostyaTr.hospital.dao.impl;

import com.github.KostyaTr.hospital.dao.HibernateUtil;
import com.github.KostyaTr.hospital.dao.MedicineDao;
import com.github.KostyaTr.hospital.dao.converter.MedicineConverter;
import com.github.KostyaTr.hospital.dao.entity.MedicineEntity;
import com.github.KostyaTr.hospital.model.Medicine;
import org.hibernate.Session;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.persistence.NoResultException;
import java.util.List;
import java.util.stream.Collectors;

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
        MedicineEntity medicineEntity;
        try {
            medicineEntity = session.createQuery("From MedicineEntity where medicine_name = :medicine_name", MedicineEntity.class)
                    .setParameter("medicine_name", medicineName).getSingleResult();
        } catch (NoResultException e){
            medicineEntity = null;
            log.info("Medicine {} wasn't found", medicineName, e);
        }
        session.getTransaction().commit();
        return MedicineConverter.fromEntity(medicineEntity);
    }

    @Override
    public Medicine getMedicineById(Long medicineId) {
        Session session = HibernateUtil.getSession();
        session.beginTransaction();
        MedicineEntity medicineEntity;
        try {
            medicineEntity = session.get(MedicineEntity.class, medicineId);
        } catch (NoResultException e){
            medicineEntity = null;
            log.info("Medicine {} wasn't found", medicineId, e);
        }
        session.getTransaction().commit();
        return MedicineConverter.fromEntity(medicineEntity);
    }

    @Override
    public List<Medicine> getMedicine() {
        Session session = HibernateUtil.getSession();
        session.beginTransaction();
        List<MedicineEntity> medicines = session.createQuery("From MedicineEntity").list();
        session.getTransaction().commit();
        return medicines.stream()
                .map(MedicineConverter::fromEntity)
                .collect(Collectors.toList());
    }
}
