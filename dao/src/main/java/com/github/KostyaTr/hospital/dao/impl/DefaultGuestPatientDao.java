package com.github.KostyaTr.hospital.dao.impl;

import com.github.KostyaTr.hospital.dao.GuestPatientDao;
import com.github.KostyaTr.hospital.dao.HibernateUtil;
import com.github.KostyaTr.hospital.model.GuestPatient;
import org.hibernate.Session;

import javax.persistence.NoResultException;
import java.util.Date;
import java.util.List;

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
        List<GuestPatient> guestPatients = session.createQuery("select gu from GuestPatient gu where doctor_id = :doctor_id", GuestPatient.class)
                .setParameter("doctor_id", doctorId).list();
        session.getTransaction().commit();
        return guestPatients;
    }

    @Override
    public List<GuestPatient> getPatients() {
        Session session = HibernateUtil.getSession();
        session.beginTransaction();
        List<GuestPatient> guestPatients = session.createQuery("select gu from GuestPatient gu", GuestPatient.class)
                .list();
        session.getTransaction().commit();
        return guestPatients;
    }

    @Override
    public Long addPatient(GuestPatient guestPatient) {
        Session session = HibernateUtil.getSession();
        session.beginTransaction();
        session.save(guestPatient);
        session.getTransaction().commit();
        return guestPatient.getPatientId();
    }

    @Override
    public int getLatestCouponToDoctorByDay(Long doctorId, int day) {
        Session session = HibernateUtil.getSession();
        session.beginTransaction();
        int coupon;
        try {
            coupon = session.createQuery("select max(couponNum) from GuestPatient " +
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
        session.createQuery("delete GuestPatient where id = :id")
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
            date = session.createQuery("select max(visitDate) from GuestPatient " +
                    "where doctor_id = :doctor_id and day(visit_date) = :day", Date.class)
                    .setParameter("doctor_id", doctorId).setParameter("day", day).getSingleResult();
        } catch (NoResultException e){
            date = null;
        }
        return date;
    }
}
