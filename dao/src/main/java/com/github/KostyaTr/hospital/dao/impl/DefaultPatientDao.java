package com.github.KostyaTr.hospital.dao.impl;

import com.github.KostyaTr.hospital.dao.PatientDao;
import com.github.KostyaTr.hospital.dao.converter.PatientConverter;
import com.github.KostyaTr.hospital.dao.entity.PatientEntity;
import com.github.KostyaTr.hospital.model.Patient;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;

import javax.persistence.NoResultException;
import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

public class DefaultPatientDao implements PatientDao {

    @Autowired
    private SessionFactory sessionFactory;

    @Override
    public List<Patient> getPatientsByDoctorId(Long doctorId) {
        Session session = sessionFactory.getCurrentSession();
        List<PatientEntity> patients = session.createQuery("select p from PatientEntity p where doctor_id = :doctor_id",
                PatientEntity.class)
                .setParameter("doctor_id", doctorId)
                .list();
        return patients.stream()
                .map(PatientConverter::fromEntity)
                .collect(Collectors.toList());
    }

    @Override
    public List<Patient> getPatientsByUserId(Long userId) {
        Session session = sessionFactory.getCurrentSession();
        List<PatientEntity> patients = session.createQuery("select p from PatientEntity p where user_id = :user_id",
                PatientEntity.class)
                .setParameter("user_id", userId)
                .list();
        return patients.stream()
                .map(PatientConverter::fromEntity)
                .collect(Collectors.toList());
    }

    @Override
    public int getLatestCouponToDoctorByDay(Long doctorId, int day) {
        Session session = sessionFactory.getCurrentSession();
        Integer coupon = session.createQuery("select max(couponNum) from PatientEntity " +
                    "where doctor_id = :doctor_id and  day(visit_date) = :day", Integer.class)
                    .setParameter("doctor_id", doctorId).setParameter("day", day).getSingleResult();
        if (coupon == null){
            coupon = 0;
        }
        return coupon;
    }

    @Override
    public List<Patient> getPatients() {
        Session session = sessionFactory.getCurrentSession();
        List<PatientEntity> patients = session.createQuery("select p from PatientEntity p", PatientEntity.class)
                .list();
        return patients.stream()
                .map(PatientConverter::fromEntity)
                .collect(Collectors.toList());
    }

    @Override
    public Long addPatient(Patient patient) {
        PatientEntity patientEntity = PatientConverter.toEntity(patient);
        Session session = sessionFactory.getCurrentSession();
        session.save(patientEntity);
        return patientEntity.getPatientId();
    }

    @Override
    public boolean removePatientById(Long patientId) {
        Session session = sessionFactory.getCurrentSession();
        session.createQuery("delete PatientEntity where id = :id")
                .setParameter("id", patientId)
                .executeUpdate();
        session.clear();
        // can produce bug if is used with in transaction with other operations
        // cause when session is cleared transient hibernate objects might be deleted as well as one that was deleted
        return getPatientById(patientId) == null;
    }

    @Override
    public void removePatientByUserId(Long userId) {
        Session session = sessionFactory.getCurrentSession();
        session.createQuery("delete PatientEntity where user_id = :id")
                .setParameter("id", userId)
                .executeUpdate();
    }

    @Override
    public Patient getPatientById(Long patientId) {
        Session session = sessionFactory.getCurrentSession();
        PatientEntity patientEntity = session.get(PatientEntity.class, patientId);
        return PatientConverter.fromEntity(patientEntity);
    }

    @Override
    public void updateVisitDate(Patient patient) {
        PatientEntity patientEntity = PatientConverter.toEntity(patient);
        Session session = sessionFactory.getCurrentSession();
        session.update(patientEntity);
    }

    @Override
    public LocalDateTime getLatestTimeToDoctorByDay(Long doctorId, int day) {
        Session session = sessionFactory.getCurrentSession();
        LocalDateTime date;
        try {
            date = session.createQuery("select max(visitDate) from PatientEntity " +
                    "where doctor_id = :doctor_id and day(visit_date) = :day", LocalDateTime.class)
                    .setParameter("doctor_id", doctorId).setParameter("day", day).getSingleResult();
        } catch (NoResultException e){
            date = null;
        }
        return date;
    }
}