package com.github.KostyaTr.hospital.dao.impl;

import com.github.KostyaTr.hospital.dao.HibernateUtil;
import com.github.KostyaTr.hospital.dao.InpatientDao;
import com.github.KostyaTr.hospital.dao.converter.InpatientConverter;
import com.github.KostyaTr.hospital.dao.entity.InpatientEntity;
import com.github.KostyaTr.hospital.model.Inpatient;
import org.hibernate.Session;

import java.util.List;
import java.util.stream.Collectors;

public class DefaultInpatientDao implements InpatientDao {

    private static class SingletonHolder {
        static final InpatientDao HOLDER_INSTANCE = new DefaultInpatientDao();
    }

    public static InpatientDao getInstance() {
        return DefaultInpatientDao.SingletonHolder.HOLDER_INSTANCE;
    }

    @Override
    public List<Inpatient> getPatients() {
        Session session = HibernateUtil.getSession();
        session.beginTransaction();
        List<InpatientEntity> inpatients = session.createQuery("select p from InpatientEntity p", InpatientEntity.class)
                .list();
        session.getTransaction().commit();
        final List<Inpatient> inpatientList = inpatients.stream()
                .map(InpatientConverter::fromEntity)
                .collect(Collectors.toList());
        session.close();
        return inpatientList;
    }

    @Override
    public List<Inpatient> getPatientsByDoctorId(Long doctorId) {
        Session session = HibernateUtil.getSession();
        session.beginTransaction();
        List<InpatientEntity> inpatients = session.createQuery("select p from InpatientEntity p " +
                "where doctor_id = :doctor_id", InpatientEntity.class)
                .setParameter("doctor_id", doctorId)
                .list();
        session.getTransaction().commit();
        final List<Inpatient> inpatientList = inpatients.stream()
                .map(InpatientConverter::fromEntity)
                .collect(Collectors.toList());
        session.close();
        return inpatientList;
    }

    @Override
    public Inpatient getInpatientById(Long patientId) {
        Session session = HibernateUtil.getSession();
        session.beginTransaction();
        InpatientEntity inpatientEntity = session.get(InpatientEntity.class, patientId);
        session.getTransaction().commit();
        final Inpatient inpatient = InpatientConverter.fromEntity(inpatientEntity);
        session.close();
        return inpatient;
    }

    @Override
    public Long addInpatient(Inpatient inpatient) {
        InpatientEntity inpatientEntity = InpatientConverter.toEntity(inpatient);
        Session session = HibernateUtil.getSession();
        session.beginTransaction();
        session.save(inpatientEntity);
        session.getTransaction().commit();
        return inpatientEntity.getInpatientId();
    }

    @Override
    public boolean removeInpatientById(Long patientId) {
        final Session session = HibernateUtil.getSession();
        session.beginTransaction();
        session.createQuery("delete InpatientEntity where id = :id")
                .setParameter("id", patientId)
                .executeUpdate();
        session.getTransaction().commit();
        session.close();
        return getInpatientById(patientId) == null;
    }

    @Override
    public void updateInpatient(Inpatient inpatient) {
        InpatientEntity inpatientEntity = InpatientConverter.toEntity(inpatient);
        Session session = HibernateUtil.getSession();
        session.beginTransaction();
        session.update(inpatientEntity);
        session.getTransaction().commit();
        session.close();
    }
}
