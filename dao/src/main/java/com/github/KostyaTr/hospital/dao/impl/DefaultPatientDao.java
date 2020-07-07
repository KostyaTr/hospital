package com.github.KostyaTr.hospital.dao.impl;

import com.github.KostyaTr.hospital.dao.HibernateUtil;
import com.github.KostyaTr.hospital.dao.PatientDao;
import com.github.KostyaTr.hospital.dao.converter.PatientConverter;
import com.github.KostyaTr.hospital.dao.entity.PatientEntity;
import com.github.KostyaTr.hospital.model.Patient;
import org.hibernate.Session;

import javax.persistence.NoResultException;
import java.time.LocalDateTime;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

public class DefaultPatientDao implements PatientDao {

    private static class SingletonHolder{
        static final PatientDao HOLDER_INSTANCE = new DefaultPatientDao();
    }

    public static PatientDao getInstance(){
        return DefaultPatientDao.SingletonHolder.HOLDER_INSTANCE;
    }


    @Override
    public List<Patient> getPatientsByDoctorId(Long doctorId) {
        Session session = HibernateUtil.getSession();
        session.beginTransaction();
        List<PatientEntity> patients = session.createQuery("select p from PatientEntity p where doctor_id = :doctor_id",
                PatientEntity.class)
                .setParameter("doctor_id", doctorId)
                .list();
        session.getTransaction().commit();
        final List<Patient> patientList = patients.stream()
                .map(PatientConverter::fromEntity)
                .collect(Collectors.toList());
        session.close();
        return patientList;
    }

    @Override
    public List<Patient> getPatientsByUserId(Long userId) {
        Session session = HibernateUtil.getSession();
        session.beginTransaction();
        List<PatientEntity> patients = session.createQuery("select p from PatientEntity p where user_id = :user_id",
                PatientEntity.class)
                .setParameter("user_id", userId)
                .list();
        session.getTransaction().commit();
        final List<Patient> patientList = patients.stream()
                .map(PatientConverter::fromEntity)
                .collect(Collectors.toList());
        session.close();
        return patientList;
    }

    @Override
    public int getLatestCouponToDoctorByDay(Long doctorId, int day) {
        Session session = HibernateUtil.getSession();
        session.beginTransaction();
        Integer coupon = session.createQuery("select max(couponNum) from PatientEntity " +
                    "where doctor_id = :doctor_id and  day(visit_date) = :day", Integer.class)
                    .setParameter("doctor_id", doctorId).setParameter("day", day).getSingleResult();
        if (coupon == null){
            coupon = 0;
        }
        session.getTransaction().commit();
        session.close();
        return coupon;
    }

    @Override
    public List<Patient> getPatients() {
        Session session = HibernateUtil.getSession();
        session.beginTransaction();
        List<PatientEntity> patients = session.createQuery("select p from PatientEntity p", PatientEntity.class)
                .list();
        session.getTransaction().commit();
        final List<Patient> patientList = patients.stream()
                .map(PatientConverter::fromEntity)
                .collect(Collectors.toList());
        session.close();
        return patientList;
    }

    @Override
    public Long addPatient(Patient patient) {
        PatientEntity patientEntity = PatientConverter.toEntity(patient);
        Session session = HibernateUtil.getSession();
        session.beginTransaction();
        session.save(patientEntity);
        session.getTransaction().commit();
        return patientEntity.getPatientId();
    }

    @Override
    public boolean removePatientById(Long patientId) {
        final Session session = HibernateUtil.getSession();
        session.beginTransaction();
        session.createQuery("delete PatientEntity where id = :id")
                .setParameter("id", patientId)
                .executeUpdate();
        session.getTransaction().commit();
        session.close();
        return getPatientById(patientId) == null;
    }

    @Override
    public void removePatientByUserId(Long userId) {
        final Session session = HibernateUtil.getSession();
        session.beginTransaction();
        session.createQuery("delete PatientEntity where user_id = :id")
                .setParameter("id", userId)
                .executeUpdate();
        session.getTransaction().commit();
        session.close();
    }

    @Override
    public Patient getPatientById(Long patientId) {
        Session session = HibernateUtil.getSession();
        session.beginTransaction();
        PatientEntity patientEntity = session.get(PatientEntity.class, patientId);
        session.getTransaction().commit();
        final Patient patient = PatientConverter.fromEntity(patientEntity);
        session.close();
        return patient;
    }

    @Override
    public void updateVisitDate(Patient patient) {
        PatientEntity patientEntity = PatientConverter.toEntity(patient);
        Session session = HibernateUtil.getSession();
        session.beginTransaction();
        session.update(patientEntity);
        session.getTransaction().commit();
        session.close();
    }

    @Override
    public LocalDateTime getLatestTimeToDoctorByDay(Long doctorId, int day) {
        Session session = HibernateUtil.getSession();
        session.beginTransaction();
        LocalDateTime date;
        try {
            date = session.createQuery("select max(visitDate) from PatientEntity " +
                    "where doctor_id = :doctor_id and day(visit_date) = :day", LocalDateTime.class)
                    .setParameter("doctor_id", doctorId).setParameter("day", day).getSingleResult();
        } catch (NoResultException e){
            date = null;
        }
        session.getTransaction().commit();
        session.close();
        return date;
    }
}