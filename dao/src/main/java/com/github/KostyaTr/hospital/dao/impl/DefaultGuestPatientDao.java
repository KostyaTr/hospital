package com.github.KostyaTr.hospital.dao.impl;

import com.github.KostyaTr.hospital.dao.GuestPatientDao;
import com.github.KostyaTr.hospital.dao.HibernateUtil;
import com.github.KostyaTr.hospital.dao.converter.GuestPatientConverter;
import com.github.KostyaTr.hospital.dao.entity.GuestPatientEntity;
import com.github.KostyaTr.hospital.model.GuestPatient;
import org.hibernate.Session;

import javax.persistence.NoResultException;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

public class DefaultGuestPatientDao implements GuestPatientDao {

    private static class SingletonHolder {
        static final GuestPatientDao HOLDER_INSTANCE = new DefaultGuestPatientDao();
    }

    public static GuestPatientDao getInstance() {
        return DefaultGuestPatientDao.SingletonHolder.HOLDER_INSTANCE;
    }


    @Override
    public List<GuestPatient> getPatientsByDoctorId(Long doctorId) {
        Session session = HibernateUtil.getSession();
        session.beginTransaction();
        List<GuestPatientEntity> guestPatients = session.createQuery("select gu from GuestPatientEntity gu where doctor_id = :doctor_id", GuestPatientEntity.class)
                .setParameter("doctor_id", doctorId).list();
        session.getTransaction().commit();
        return guestPatients.stream()
                .map(GuestPatientConverter::fromEntity)
                .collect(Collectors.toList());
    }

    @Override
    public List<GuestPatient> getPatients() {
        Session session = HibernateUtil.getSession();
        session.beginTransaction();
        List<GuestPatientEntity> guestPatients = session.createQuery("select gu from GuestPatientEntity gu", GuestPatientEntity.class)
                .list();
        session.getTransaction().commit();
        return guestPatients.stream()
                .map(GuestPatientConverter::fromEntity)
                .collect(Collectors.toList());
    }

    @Override
    public Long addPatient(GuestPatient guestPatient) {
        GuestPatientEntity guestPatientEntity = GuestPatientConverter.toEntity(guestPatient);
        Session session = HibernateUtil.getSession();
        session.beginTransaction();
        session.save(guestPatientEntity);
        session.getTransaction().commit();
        return guestPatientEntity.getPatientId();
    }

    @Override
    public int getLatestCouponToDoctorByDay(Long doctorId, int day) {
        Session session = HibernateUtil.getSession();
        session.beginTransaction();
        int coupon;
        try {
            coupon = session.createQuery("select max(couponNum) from GuestPatientEntity " +
                    "where doctor_id = :doctor_id and  day(visit_date) = :day", Integer.class)
                    .setParameter("doctor_id", doctorId).setParameter("day", day).getSingleResult();
        } catch (NoResultException e){
            coupon = 0;
        }
        return coupon;
    }

    @Override
    public void removePatientById(Long patientId) {
        final Session session = HibernateUtil.getSession();
        session.beginTransaction();
        session.createQuery("delete GuestPatientEntity where id = :id")
                .setParameter("id", patientId)
                .executeUpdate();
        session.getTransaction().commit();
    }

    @Override
    public Date getLatestTimeToDoctorByDay(Long doctorId, int day) {
        Session session = HibernateUtil.getSession();
        session.beginTransaction();
        Date date;
        try {
            date = session.createQuery("select max(visitDate) from GuestPatientEntity " +
                    "where doctor_id = :doctor_id and day(visit_date) = :day", Date.class)
                    .setParameter("doctor_id", doctorId).setParameter("day", day).getSingleResult();
        } catch (NoResultException e){
            date = null;
        }
        return date;
    }
}
