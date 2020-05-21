package com.github.KostyaTr.hospital.dao.impl;

import com.github.KostyaTr.hospital.dao.ChamberDao;
import com.github.KostyaTr.hospital.dao.HibernateUtil;
import org.hibernate.Session;

import javax.persistence.NoResultException;
import java.util.List;

public class DefaultChamberDao implements ChamberDao {

    private static class SingletonHolder {
        static final ChamberDao HOLDER_INSTANCE = new DefaultChamberDao();
    }

    public static ChamberDao getInstance() {
        return DefaultChamberDao.SingletonHolder.HOLDER_INSTANCE;
    }

    @Override
    public void updateChamberLoad(Long id, int load) {
        Session session = HibernateUtil.getSession();
        session.beginTransaction();
        session.createQuery("update ChamberEntity set chamberLoad = chamberLoad + :load " +
                    " where id = :id")
                    .setParameter("id", id)
                    .setParameter("load", load)
                    .executeUpdate();
        session.getTransaction().commit();
    }

    @Override
    public double getPriceForChamber(Long deptChamberId) {
        Session session = HibernateUtil.getSession();
        session.beginTransaction();
        double priceADay;
        try {
            priceADay = session.createQuery("select priceADay from ChamberEntity" +
                    " where id = :id", Double.class)
                    .setParameter("id", deptChamberId).getSingleResult();
        } catch (NoResultException e){
            priceADay = 0;
        }
        session.getTransaction().commit();
        return priceADay;
    }

    @Override
    public List<Long> getEmptyChambersByDeptId(Long deptId) {
        Session session = HibernateUtil.getSession();
        session.beginTransaction();
        List<Long> chambers = session.createQuery("select id from ChamberEntity c where dept_id = :dept_id " +
                "and chamber_capacity > chamber_load", Long.class)
                .setParameter("dept_id", deptId).list();
        session.getTransaction().commit();
        return chambers;
    }
}
