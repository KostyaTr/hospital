package com.github.KostyaTr.hospital.dao;

import com.github.KostyaTr.hospital.dao.impl.DefaultChamberDao;
import com.github.KostyaTr.hospital.model.Chamber;
import com.github.KostyaTr.hospital.model.Department;
import org.hibernate.Session;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

public class DefaultChamberDaoTest {
    private ChamberDao chamberDao = DefaultChamberDao.getInstance();

    @Test
    void updateChamberLoad() {
        Session session = HibernateUtil.getSession();
        session.beginTransaction();
        final Department department = new Department(null, "updateChamberLoad", 3, 3, null, null);
        session.save(department);
        final Chamber chamber = new Chamber(null, 2, department, true, 5, 1, 5, null);
        Long chamberId = (Long) session.save (chamber);
        session.getTransaction().commit();

        chamberDao.updateChamberLoad(chamberId, 1);

        session.beginTransaction();
        session.refresh(chamber);
        session.getTransaction().commit();
        assertEquals(chamber.getChamberLoad(), 2);
    }

    @Test
    void getPriceForChamber() {
        Session session = HibernateUtil.getSession();
        session.beginTransaction();
        final Department department = new Department(null, "getChamberPrice", 3, 3, null, null);
        session.save(department);
        Long chamberId = (Long) session.save (new Chamber(null, 1, department, true, 5, 1, 5, null));
        session.getTransaction().commit();

        assertEquals(chamberDao.getPriceForChamber(chamberId), 5);
        assertEquals(chamberDao.getPriceForChamber(0L), 0);
    }

    @Test
    void getEmptyChambers(){
        Session session = HibernateUtil.getSession();
        session.beginTransaction();
        final Department department = new Department(null, "getEmptyChambers", 3, 3, null, null);
        Long deptId = (Long) session.save(department);
        session.save (new Chamber(null, 1, department, true, 5, 5, 5, null));
        session.save (new Chamber(null, 2, department, true, 5, 1, 5, null));
        session.save (new Chamber(null, 3, department, true, 5, 1, 5, null));
        session.getTransaction().commit();

        final List<Chamber> emptyChambersByDeptId = chamberDao.getEmptyChambersByDeptId(deptId);
        assertFalse(emptyChambersByDeptId.isEmpty());
        assertEquals(emptyChambersByDeptId.get(emptyChambersByDeptId.size() - 1).getChamberNum(), 3);
    }
}
