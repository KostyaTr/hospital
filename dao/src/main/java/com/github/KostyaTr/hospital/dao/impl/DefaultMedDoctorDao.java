package com.github.KostyaTr.hospital.dao.impl;

import com.github.KostyaTr.hospital.dao.HibernateUtil;
import com.github.KostyaTr.hospital.dao.MedDoctorDao;
import com.github.KostyaTr.hospital.dao.converter.MedDoctorConverter;
import com.github.KostyaTr.hospital.dao.entity.MedDoctorEntity;
import com.github.KostyaTr.hospital.model.MedDoctor;
import org.hibernate.Session;

import javax.persistence.NoResultException;
import java.util.List;
import java.util.stream.Collectors;

public class DefaultMedDoctorDao implements MedDoctorDao {

    private static class SingletonHolder {
        static final MedDoctorDao HOLDER_INSTANCE = new DefaultMedDoctorDao();
    }

    public static MedDoctorDao getInstance() {
        return DefaultMedDoctorDao.SingletonHolder.HOLDER_INSTANCE;
    }

    @Override
    public List<MedDoctor> getDoctors() {
        Session session = HibernateUtil.getSession();
        session.beginTransaction();
        List<MedDoctorEntity> doctorsEntity = session.createQuery("From MedDoctorEntity", MedDoctorEntity.class).list();
        session.getTransaction().commit();
        final List<MedDoctor> medDoctors = doctorsEntity.stream()
                .map(MedDoctorConverter::fromEntity)
                .collect(Collectors.toList());
        session.close();
        return medDoctors;
    }

    @Override
    public MedDoctor getDoctorById(Long doctorId) {
        Session session = HibernateUtil.getSession();
        session.beginTransaction();
        MedDoctorEntity doctorEntity;
        try {
            doctorEntity = session.get(MedDoctorEntity.class, doctorId);
        } catch (NoResultException e){
            doctorEntity = null;
        }
        session.getTransaction().commit();
        final MedDoctor medDoctor = MedDoctorConverter.fromEntity(doctorEntity);
        session.close();
        return medDoctor;
    }

    @Override
    public MedDoctor getDoctorByUserId(Long userId) {
        Session session = HibernateUtil.getSession();
        session.beginTransaction();
        MedDoctorEntity doctorEntity;
        try {
            doctorEntity = session.createQuery("From MedDoctorEntity where user_id = :user_id", MedDoctorEntity.class)
                    .setParameter("user_id", userId).getSingleResult();
        } catch (NoResultException e){
            doctorEntity = null;
        }
        session.getTransaction().commit();
        final MedDoctor medDoctor = MedDoctorConverter.fromEntity(doctorEntity);
        session.close();
        return medDoctor;
    }

    @Override
    public List<MedDoctor> getDoctorBySpeciality(Long specialityId) {
        Session session = HibernateUtil.getSession();
        session.beginTransaction();
        List<MedDoctorEntity> doctorEntities = session.createQuery("select doctor from MedDoctorEntity doctor" +
                " join doctor.specialities s " +
                " where s.id = :speciality_id", MedDoctorEntity.class)
                    .setParameter("speciality_id", specialityId).list();
        session.getTransaction().commit();
        final List<MedDoctor> medDoctors = doctorEntities.stream()
                .map(MedDoctorConverter::fromEntity)
                .collect(Collectors.toList());
        session.close();
        return medDoctors;
    }
}