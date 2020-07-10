package com.github.KostyaTr.hospital.dao.impl;

import com.github.KostyaTr.hospital.dao.MedDoctorDao;
import com.github.KostyaTr.hospital.dao.converter.MedDoctorConverter;
import com.github.KostyaTr.hospital.dao.entity.MedDoctorEntity;
import com.github.KostyaTr.hospital.model.MedDoctor;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;

import javax.persistence.NoResultException;
import java.util.List;
import java.util.stream.Collectors;

public class DefaultMedDoctorDao implements MedDoctorDao {

    @Autowired
    private SessionFactory sessionFactory;

    @Override
    public List<MedDoctor> getDoctors() {
        Session session = sessionFactory.getCurrentSession();
        List<MedDoctorEntity> doctorsEntity = session.createQuery("From MedDoctorEntity", MedDoctorEntity.class).list();
        return doctorsEntity.stream()
                .map(MedDoctorConverter::fromEntity)
                .collect(Collectors.toList());
    }

    @Override
    public MedDoctor getDoctorById(Long doctorId) {
        Session session = sessionFactory.getCurrentSession();
        MedDoctorEntity doctorEntity;
        try {
            doctorEntity = session.get(MedDoctorEntity.class, doctorId);
        } catch (NoResultException e){
            doctorEntity = null;
        }
        return MedDoctorConverter.fromEntity(doctorEntity);
    }

    @Override
    public MedDoctor getDoctorByUserId(Long userId) {
        Session session = sessionFactory.getCurrentSession();
        MedDoctorEntity doctorEntity;
        try {
            doctorEntity = session.createQuery("From MedDoctorEntity where user_id = :user_id", MedDoctorEntity.class)
                    .setParameter("user_id", userId).getSingleResult();
        } catch (NoResultException e){
            doctorEntity = null;
        }
        return MedDoctorConverter.fromEntity(doctorEntity);
    }

    @Override
    public List<MedDoctor> getDoctorBySpeciality(Long specialityId) {
        Session session = sessionFactory.getCurrentSession();
        List<MedDoctorEntity> doctorEntities = session.createQuery("select doctor from MedDoctorEntity doctor" +
                " join doctor.specialities s " +
                " where s.id = :speciality_id", MedDoctorEntity.class)
                    .setParameter("speciality_id", specialityId).list();
        return doctorEntities.stream()
                .map(MedDoctorConverter::fromEntity)
                .collect(Collectors.toList());
    }
}