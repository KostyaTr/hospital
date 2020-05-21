package com.github.KostyaTr.hospital.dao.impl;

import com.github.KostyaTr.hospital.dao.DischargedPatientDao;
import com.github.KostyaTr.hospital.dao.HibernateUtil;
import com.github.KostyaTr.hospital.dao.converter.DischargedPatientConverter;
import com.github.KostyaTr.hospital.dao.entity.DischargedPatientEntity;
import com.github.KostyaTr.hospital.model.DischargedPatient;
import org.hibernate.Session;

import java.util.List;
import java.util.stream.Collectors;

public class DefaultDischargedPatientDao implements DischargedPatientDao {
    private int PAGE_SIZE = 10;

    private static class SingletonHolder {
        static final DischargedPatientDao HOLDER_INSTANCE = new DefaultDischargedPatientDao();
    }

    public static DischargedPatientDao getInstance() {
        return DefaultDischargedPatientDao.SingletonHolder.HOLDER_INSTANCE;
    }

    @Override
    public Long addDischargedPatient(DischargedPatient dischargedPatient) {
        DischargedPatientEntity dischargedPatientEntity = DischargedPatientConverter.toEntity(dischargedPatient);
        Session session = HibernateUtil.getSession();
        session.beginTransaction();
        session.save(dischargedPatientEntity);
        session.getTransaction().commit();
        return dischargedPatientEntity.getDischargedPatientId();
    }

    @Override
    public List<DischargedPatient> getDischargedPatients(int page) {
        Session session = HibernateUtil.getSession();
        session.beginTransaction();
        List<DischargedPatientEntity> dischargedPatients = session.createQuery("select dp from DischargedPatientEntity dp", DischargedPatientEntity.class)
                .setFirstResult((page - 1) * PAGE_SIZE)
                .setMaxResults(PAGE_SIZE)
                .list();
        session.getTransaction().commit();
        return dischargedPatients.stream()
                .map(DischargedPatientConverter::fromEntity)
                .collect(Collectors.toList());
    }

    @Override
    public double getDischargedPatientsCount() {
        Session session = HibernateUtil.getSession();
        session.beginTransaction();
        Long count = session.createQuery("select count(dischargedPatientId) from DischargedPatientEntity", Long.class)
                .getSingleResult();
        session.getTransaction().commit();
        return count;
    }
}
