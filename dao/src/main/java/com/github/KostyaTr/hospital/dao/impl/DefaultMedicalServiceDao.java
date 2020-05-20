package com.github.KostyaTr.hospital.dao.impl;

import com.github.KostyaTr.hospital.dao.HibernateUtil;
import com.github.KostyaTr.hospital.dao.MedicalServiceDao;
import com.github.KostyaTr.hospital.model.MedicalService;
import org.hibernate.Session;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.persistence.NoResultException;
import java.util.List;

public class DefaultMedicalServiceDao implements MedicalServiceDao {
    private static final Logger log = LoggerFactory.getLogger(DefaultMedicineDao.class);

    private static class SingletonHolder {
        static final MedicalServiceDao HOLDER_INSTANCE = new DefaultMedicalServiceDao();
    }

    public static MedicalServiceDao getInstance() {
        return DefaultMedicalServiceDao.SingletonHolder.HOLDER_INSTANCE;
    }


    @Override
    public List<MedicalService> getMedicalServices() {
        Session session = HibernateUtil.getSession();
        session.beginTransaction();
        List<MedicalService> medicalServices = session.createQuery("From MedicalService", MedicalService.class).list();
        session.getTransaction().commit();
        return medicalServices;
    }

    @Override
    public MedicalService getMedicalServiceById(Long medicalServiceId) {
        Session session = HibernateUtil.getSession();
        session.beginTransaction();
        MedicalService medicalService;
        try {
           medicalService = session.get(MedicalService.class, medicalServiceId);
        } catch (NoResultException e){
            medicalService = null;
            log.info("No medical service was found by {} id", medicalServiceId, e);
        }
        session.getTransaction().commit();
        return medicalService;
    }
}
