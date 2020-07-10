package com.github.KostyaTr.hospital.dao.impl;

import com.github.KostyaTr.hospital.dao.MedicalServiceDao;
import com.github.KostyaTr.hospital.dao.converter.MedicalServiceConverter;
import com.github.KostyaTr.hospital.dao.entity.MedicalServiceEntity;
import com.github.KostyaTr.hospital.model.MedicalService;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

import javax.persistence.NoResultException;
import java.util.List;
import java.util.stream.Collectors;

public class DefaultMedicalServiceDao implements MedicalServiceDao {
    private static final Logger log = LoggerFactory.getLogger(DefaultMedicineDao.class);

    @Autowired
    private SessionFactory sessionFactory;

    @Override
    public List<MedicalService> getMedicalServices() {
        Session session = sessionFactory.getCurrentSession();
        List<MedicalServiceEntity> medicalServices = session.createQuery("From MedicalServiceEntity", MedicalServiceEntity.class)
                .list();
        return medicalServices.stream()
                .map(MedicalServiceConverter::fromEntity)
                .collect(Collectors.toList());
    }

    @Override
    public MedicalService getMedicalServiceById(Long medicalServiceId) {
        Session session = sessionFactory.getCurrentSession();
        MedicalServiceEntity medicalServiceEntity;
        try {
           medicalServiceEntity = session.get(MedicalServiceEntity.class, medicalServiceId);
        } catch (NoResultException e){
            medicalServiceEntity = null;
            log.info("No medical service was found by {} id", medicalServiceId, e);
        }
        return MedicalServiceConverter.fromEntity(medicalServiceEntity);
    }
}
