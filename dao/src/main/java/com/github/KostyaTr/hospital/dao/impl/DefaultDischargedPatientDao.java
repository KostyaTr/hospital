package com.github.KostyaTr.hospital.dao.impl;

import com.github.KostyaTr.hospital.dao.DischargedPatientDao;
import com.github.KostyaTr.hospital.dao.converter.DischargedPatientConverter;
import com.github.KostyaTr.hospital.dao.entity.DischargedPatientEntity;
import com.github.KostyaTr.hospital.model.DischargedPatient;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;
import java.util.stream.Collectors;

public class DefaultDischargedPatientDao implements DischargedPatientDao {
    private int PAGE_SIZE = 10;

    @Autowired
    private SessionFactory sessionFactory;

    @Override
    public Long addDischargedPatient(DischargedPatient dischargedPatient) {
        DischargedPatientEntity dischargedPatientEntity = DischargedPatientConverter.toEntity(dischargedPatient);
        Session session = sessionFactory.getCurrentSession();
        session.save(dischargedPatientEntity);
        return dischargedPatientEntity.getDischargedPatientId();
    }

    @Override
    public List<DischargedPatient> getDischargedPatients(int page) {
        Session session = sessionFactory.getCurrentSession();
        List<DischargedPatientEntity> dischargedPatients = session.createQuery("select dp from DischargedPatientEntity dp", DischargedPatientEntity.class)
                .setFirstResult((page - 1) * PAGE_SIZE)
                .setMaxResults(PAGE_SIZE)
                .list();
        return dischargedPatients.stream()
                .map(DischargedPatientConverter::fromEntity)
                .collect(Collectors.toList());
    }

    @Override
    public double getDischargedPatientsCount() {
        Session session = sessionFactory.getCurrentSession();
        return session.createQuery("select count(dischargedPatientId) from DischargedPatientEntity", Long.class)
                .getSingleResult();
    }
}
