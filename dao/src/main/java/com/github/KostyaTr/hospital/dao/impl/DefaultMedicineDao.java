package com.github.KostyaTr.hospital.dao.impl;

import com.github.KostyaTr.hospital.dao.MedicineDao;
import com.github.KostyaTr.hospital.dao.converter.MedicineConverter;
import com.github.KostyaTr.hospital.dao.entity.MedicineEntity;
import com.github.KostyaTr.hospital.model.Medicine;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

import javax.persistence.NoResultException;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;
import java.util.List;
import java.util.stream.Collectors;

public class DefaultMedicineDao implements MedicineDao {
    private static final Logger log = LoggerFactory.getLogger(DefaultMedicineDao.class);

    @Autowired
    private SessionFactory sessionFactory;

    @Override
    public Medicine getMedicineByName(String medicineName) {
        Session session = sessionFactory.getCurrentSession();
        CriteriaBuilder cb = session.getCriteriaBuilder();
        CriteriaQuery<MedicineEntity> criteria = cb.createQuery(MedicineEntity.class);
        Root<MedicineEntity> med = criteria.from(MedicineEntity.class);
        criteria.select(med).where(cb.equal(med.get("medicineName"), medicineName));
        MedicineEntity medicineEntity;
        try {
            medicineEntity = session.createQuery(criteria).getSingleResult();
        } catch (NoResultException e){
            medicineEntity = null;
            log.info("Medicine {} wasn't found", medicineName, e);
        }
        return MedicineConverter.fromEntity(medicineEntity);
    }

    @Override
    public Medicine getMedicineById(Long medicineId) {
        Session session = sessionFactory.getCurrentSession();
        MedicineEntity medicineEntity;
        try {
            medicineEntity = session.get(MedicineEntity.class, medicineId);
        } catch (NoResultException e){
            medicineEntity = null;
            log.info("Medicine {} wasn't found", medicineId, e);
        }
        return MedicineConverter.fromEntity(medicineEntity);
    }

    @Override
    public List<Medicine> getMedicine() {
        Session session = sessionFactory.getCurrentSession();
        List<MedicineEntity> medicines = session.createQuery("From MedicineEntity").list();
        return medicines.stream()
                .map(MedicineConverter::fromEntity)
                .collect(Collectors.toList());
    }
}
