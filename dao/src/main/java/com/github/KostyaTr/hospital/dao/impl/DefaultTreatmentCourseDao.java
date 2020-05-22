package com.github.KostyaTr.hospital.dao.impl;

import com.github.KostyaTr.hospital.dao.HibernateUtil;
import com.github.KostyaTr.hospital.dao.TreatmentCourseDao;
import com.github.KostyaTr.hospital.dao.converter.InpatientConverter;
import com.github.KostyaTr.hospital.dao.converter.TreatmentCourseConverter;
import com.github.KostyaTr.hospital.dao.entity.InpatientEntity;
import com.github.KostyaTr.hospital.dao.entity.TreatmentCourseEntity;
import com.github.KostyaTr.hospital.model.Inpatient;
import com.github.KostyaTr.hospital.model.TreatmentCourse;
import org.hibernate.Session;

import javax.persistence.NoResultException;
import java.util.List;
import java.util.stream.Collectors;

public class DefaultTreatmentCourseDao implements TreatmentCourseDao {

    private static class SingletonHolder{
        final static TreatmentCourseDao INSTANCE_HOLDER = new DefaultTreatmentCourseDao();
    }

    public static TreatmentCourseDao getInstance(){return DefaultTreatmentCourseDao.SingletonHolder.INSTANCE_HOLDER;}

    @Override
    public Long addTreatmentCourse(TreatmentCourse treatmentCourse) {
        TreatmentCourseEntity treatmentCourseEntity = TreatmentCourseConverter.toEntity(treatmentCourse);
        Session session = HibernateUtil.getSession();
        session.beginTransaction();
        session.save(treatmentCourseEntity);
        session.getTransaction().commit();
        return treatmentCourseEntity.getTreatmentCourseId();
    }

    @Override
    public List<TreatmentCourse> getTreatmentCourses() {
        Session session = HibernateUtil.getSession();
        session.beginTransaction();
        List<TreatmentCourseEntity> treatmentCourseEntities = session.createQuery("select tr from TreatmentCourseEntity tr", TreatmentCourseEntity.class)
                .list();
        session.getTransaction().commit();
        return treatmentCourseEntities.stream()
                .map(TreatmentCourseConverter::fromEntity)
                .collect(Collectors.toList());
    }

    @Override
    public boolean removeTreatmentCourseById(Long treatmentCourseId) {
        Session session = HibernateUtil.getSession();
        session.beginTransaction();
        session.createQuery("delete TreatmentCourseEntity where id = :id")
                .setParameter("id", treatmentCourseId).executeUpdate();
        session.getTransaction().commit();
        return getTreatmentCourseById(treatmentCourseId) == null;
    }

    @Override
    public TreatmentCourse getTreatmentCourseById(Long treatmentCourseId) {
        Session session = HibernateUtil.getSession();
        session.beginTransaction();
        TreatmentCourseEntity treatmentCourseEntity;
        try{
            treatmentCourseEntity = session.get(TreatmentCourseEntity.class, treatmentCourseId);
        } catch (NoResultException e){
            treatmentCourseEntity = null;
        }
        session.getTransaction().commit();
        return TreatmentCourseConverter.fromEntity(treatmentCourseEntity);
    }
}
