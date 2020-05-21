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
        return doctorsEntity.stream()
                .map(MedDoctorConverter::fromEntity)
                .collect(Collectors.toList());
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
        return MedDoctorConverter.fromEntity(doctorEntity);
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
        return MedDoctorConverter.fromEntity(doctorEntity);
    }
}