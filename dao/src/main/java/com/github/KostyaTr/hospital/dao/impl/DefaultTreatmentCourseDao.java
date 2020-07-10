package com.github.KostyaTr.hospital.dao.impl;

import com.github.KostyaTr.hospital.dao.TreatmentCourseDao;
import com.github.KostyaTr.hospital.dao.converter.TreatmentCourseConverter;
import com.github.KostyaTr.hospital.dao.entity.TreatmentCourseEntity;
import com.github.KostyaTr.hospital.model.TreatmentCourse;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;

import javax.persistence.NoResultException;
import java.util.List;
import java.util.stream.Collectors;

public class DefaultTreatmentCourseDao implements TreatmentCourseDao {

    @Autowired
    private SessionFactory sessionFactory;

    @Override
    public Long addTreatmentCourse(TreatmentCourse treatmentCourse) {
        TreatmentCourseEntity treatmentCourseEntity = TreatmentCourseConverter.toEntity(treatmentCourse);
        Session session = sessionFactory.getCurrentSession();
        session.save(treatmentCourseEntity);
        return treatmentCourseEntity.getTreatmentCourseId();
    }

    @Override
    public List<TreatmentCourse> getTreatmentCourses() {
        Session session = sessionFactory.getCurrentSession();
        List<TreatmentCourseEntity> treatmentCourseEntities = session.createQuery("select tr from TreatmentCourseEntity tr", TreatmentCourseEntity.class)
                .list();
        return treatmentCourseEntities.stream()
                .map(TreatmentCourseConverter::fromEntity)
                .collect(Collectors.toList());
    }

    @Override
    public boolean removeTreatmentCourseById(Long treatmentCourseId) {
        Session session = sessionFactory.getCurrentSession();
        session.createQuery("delete TreatmentCourseEntity where id = :id")
                .setParameter("id", treatmentCourseId).executeUpdate();
        session.clear();
        return getTreatmentCourseById(treatmentCourseId) == null;
    }

    @Override
    public TreatmentCourse getTreatmentCourseById(Long treatmentCourseId) {
        Session session = sessionFactory.getCurrentSession();
        TreatmentCourseEntity treatmentCourseEntity;
        try{
            treatmentCourseEntity = session.get(TreatmentCourseEntity.class, treatmentCourseId);
        } catch (NoResultException e){
            treatmentCourseEntity = null;
        }
        return TreatmentCourseConverter.fromEntity(treatmentCourseEntity);
    }
}
