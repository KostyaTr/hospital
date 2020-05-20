package com.github.KostyaTr.hospital.dao.impl;

import com.github.KostyaTr.hospital.dao.HibernateUtil;
import com.github.KostyaTr.hospital.dao.TreatmentCourseDao;
import com.github.KostyaTr.hospital.model.TreatmentCourse;
import org.hibernate.Session;

import javax.persistence.NoResultException;

public class DefaultTreatmentCourseDao implements TreatmentCourseDao {

    private static class SingletonHolder{
        final static TreatmentCourseDao INSTANCE_HOLDER = new DefaultTreatmentCourseDao();
    }

    public static TreatmentCourseDao getInstance(){return DefaultTreatmentCourseDao.SingletonHolder.INSTANCE_HOLDER;}

    @Override
    public Long addTreatmentCourse(TreatmentCourse treatmentCourse) {
        Session session = HibernateUtil.getSession();
        session.beginTransaction();
        session.save(treatmentCourse);
        session.getTransaction().commit();
        return treatmentCourse.getTreatmentCourseId();
    }

    @Override
    public boolean removeTreatmentCourseById(Long treatmentCourseId) {
        Session session = HibernateUtil.getSession();
        session.beginTransaction();
        session.createQuery("delete TreatmentCourse where id = :id")
                .setParameter("id", treatmentCourseId).executeUpdate();
        session.getTransaction().commit();
        return getTreatmentCourseById(treatmentCourseId) == null;
    }

    @Override
    public TreatmentCourse getTreatmentCourseById(Long treatmentCourseId) {
        Session session = HibernateUtil.getSession();
        session.beginTransaction();
        TreatmentCourse treatmentCourse;
        try{
            treatmentCourse = session.get(TreatmentCourse.class, treatmentCourseId);
        } catch (NoResultException e){
            treatmentCourse = null;
        }
        session.getTransaction().commit();
        return treatmentCourse;
    }
}
