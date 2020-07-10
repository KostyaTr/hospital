package com.github.KostyaTr.hospital.dao.impl;

import com.github.KostyaTr.hospital.dao.GuestPatientDao;
import com.github.KostyaTr.hospital.dao.converter.GuestPatientConverter;
import com.github.KostyaTr.hospital.dao.entity.GuestPatientEntity;
import com.github.KostyaTr.hospital.model.GuestPatient;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;

import javax.persistence.NoResultException;
import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

public class DefaultGuestPatientDao implements GuestPatientDao {

    @Autowired
    private SessionFactory sessionFactory;

    @Override
    public List<GuestPatient> getPatientsByDoctorId(Long doctorId) {
        Session session = sessionFactory.getCurrentSession();
        List<GuestPatientEntity> guestPatients = session.createQuery("select gu from GuestPatientEntity gu where doctor_id = :doctor_id", GuestPatientEntity.class)
                .setParameter("doctor_id", doctorId).list();
        return guestPatients.stream()
                .map(GuestPatientConverter::fromEntity)
                .collect(Collectors.toList());
    }

    @Override
    public List<GuestPatient> getPatients() {
        Session session = sessionFactory.getCurrentSession();List<GuestPatientEntity> guestPatients = session.createQuery("select gu from GuestPatientEntity gu", GuestPatientEntity.class)
                .list();
        return guestPatients.stream()
                .map(GuestPatientConverter::fromEntity)
                .collect(Collectors.toList());
    }

    @Override
    public Long addPatient(GuestPatient guestPatient) {
        GuestPatientEntity guestPatientEntity = GuestPatientConverter.toEntity(guestPatient);
        Session session = sessionFactory.getCurrentSession();session.save(guestPatientEntity);
        return guestPatientEntity.getPatientId();
    }

    @Override
    public int getLatestCouponToDoctorByDay(Long doctorId, int day) {
        Session session = sessionFactory.getCurrentSession();
        Integer coupon = session.createQuery("select max(couponNum) from GuestPatientEntity " +
                    "where doctor_id = :doctor_id and  day(visit_date) = :day", Integer.class)
                    .setParameter("doctor_id", doctorId).setParameter("day", day).getSingleResult();
        if (coupon == null){
            coupon = 0;
        }
        return coupon;
    }

    @Override
    public void removePatientById(Long patientId) {
        Session session = sessionFactory.getCurrentSession();session.createQuery("delete GuestPatientEntity where id = :id")
                .setParameter("id", patientId)
                .executeUpdate();
    }

    @Override
    public LocalDateTime getLatestTimeToDoctorByDay(Long doctorId, int day) {
        Session session = sessionFactory.getCurrentSession();
        LocalDateTime date;
        try {
            date = session.createQuery("select max(visitDate) from GuestPatientEntity " +
                    "where doctor_id = :doctor_id and day(visit_date) = :day", LocalDateTime.class)
                    .setParameter("doctor_id", doctorId).setParameter("day", day).getSingleResult();
        } catch (NoResultException e){
            date = null;
        }
        return date;
    }
}
