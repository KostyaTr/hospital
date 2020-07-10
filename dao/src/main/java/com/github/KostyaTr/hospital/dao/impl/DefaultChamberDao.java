package com.github.KostyaTr.hospital.dao.impl;

import com.github.KostyaTr.hospital.dao.ChamberDao;
import com.github.KostyaTr.hospital.dao.converter.ChamberConverter;
import com.github.KostyaTr.hospital.dao.entity.ChamberEntity;
import com.github.KostyaTr.hospital.model.Chamber;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;

import javax.persistence.NoResultException;
import java.util.List;
import java.util.stream.Collectors;

public class DefaultChamberDao implements ChamberDao {
    @Autowired
    private SessionFactory sessionFactory;

    @Override
    public void updateChamberLoad(Long id, int load) {
        Session session = sessionFactory.getCurrentSession();
        session.createQuery("update ChamberEntity set chamberLoad = chamberLoad + :load " +
                    " where id = :id")
                    .setParameter("id", id)
                    .setParameter("load", load)
                    .executeUpdate();
    }

    @Override
    public double getPriceForChamber(Long deptChamberId) {
        Session session = sessionFactory.getCurrentSession();
        double priceADay;
        try {
            priceADay = session.createQuery("select priceADay from ChamberEntity" +
                    " where id = :id", Double.class)
                    .setParameter("id", deptChamberId).getSingleResult();
        } catch (NoResultException e){
            priceADay = 0;
        }
        return priceADay;
    }

    @Override
    public List<Chamber> getEmptyChambersByDeptId(Long deptId) {
        Session session = sessionFactory.getCurrentSession();
        List<ChamberEntity> chambers = session.createQuery("select c from ChamberEntity c where dept_id = :dept_id " +
                "and chamber_capacity > chamber_load", ChamberEntity.class)
                .setParameter("dept_id", deptId).list();
        return chambers.stream()
                .map(ChamberConverter::fromEntity)
                .collect(Collectors.toList());
    }
}
