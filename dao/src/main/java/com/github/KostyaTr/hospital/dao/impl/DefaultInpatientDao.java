package com.github.KostyaTr.hospital.dao.impl;

import com.github.KostyaTr.hospital.dao.InpatientDao;
import com.github.KostyaTr.hospital.dao.converter.InpatientConverter;
import com.github.KostyaTr.hospital.dao.entity.InpatientEntity;
import com.github.KostyaTr.hospital.model.Inpatient;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;
import java.util.stream.Collectors;

public class DefaultInpatientDao implements InpatientDao {

    @Autowired
    private SessionFactory sessionFactory;

    @Override
    public List<Inpatient> getPatients() {
        Session session = sessionFactory.getCurrentSession();
        List<InpatientEntity> inpatients = session.createQuery("select p from InpatientEntity p", InpatientEntity.class)
                .list();
        return inpatients.stream()
                .map(InpatientConverter::fromEntity)
                .collect(Collectors.toList());
    }

    @Override
    public List<Inpatient> getPatientsByDoctorId(Long doctorId) {
        Session session = sessionFactory.getCurrentSession();List<InpatientEntity> inpatients = session.createQuery("select p from InpatientEntity p " +
                "where doctor_id = :doctor_id", InpatientEntity.class)
                .setParameter("doctor_id", doctorId)
                .list();
        return inpatients.stream()
                .map(InpatientConverter::fromEntity)
                .collect(Collectors.toList());
    }

    @Override
    public Inpatient getInpatientById(Long patientId) {
        Session session = sessionFactory.getCurrentSession();
        InpatientEntity inpatientEntity = session.get(InpatientEntity.class, patientId);
        return InpatientConverter.fromEntity(inpatientEntity);
    }

    @Override
    public Long addInpatient(Inpatient inpatient) {
        InpatientEntity inpatientEntity = InpatientConverter.toEntity(inpatient);
        Session session = sessionFactory.getCurrentSession();
        session.save(inpatientEntity);
        return inpatientEntity.getInpatientId();
    }

    @Override
    public boolean removeInpatientById(Long patientId) {
        Session session = sessionFactory.getCurrentSession();
        session.createQuery("delete InpatientEntity where id = :id")
                .setParameter("id", patientId)
                .executeUpdate();
        return getInpatientById(patientId) == null;
    }

    @Override
    public void updateInpatient(Inpatient inpatient) {
        InpatientEntity inpatientEntity = InpatientConverter.toEntity(inpatient);
        Session session = sessionFactory.getCurrentSession();
        session.merge(inpatientEntity);
    }
}
