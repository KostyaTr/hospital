package com.github.KostyaTr.hospital.dao;

import com.github.KostyaTr.hospital.dao.config.DaoConfig;
import com.github.KostyaTr.hospital.dao.entity.ChamberEntity;
import com.github.KostyaTr.hospital.dao.entity.DepartmentEntity;
import com.github.KostyaTr.hospital.model.Chamber;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(SpringExtension.class)
@ContextConfiguration(classes = DaoConfig.class)
@Transactional
public class DefaultChamberDaoTest {
    @Autowired
    private ChamberDao chamberDao;

    @Autowired
    private SessionFactory sessionFactory;

    @Test
    void updateChamberLoad() {
        Session session = sessionFactory.getCurrentSession();
        final DepartmentEntity department = new DepartmentEntity(null, "updateChamberLoad", 3, 3, null, null);
        session.save(department);
        final ChamberEntity chamber = new ChamberEntity(null, 2, department, true, 5, 1, 5, null);
        Long chamberId = (Long) session.save (chamber);
        chamberDao.updateChamberLoad(chamberId, 1);
        session.refresh(chamber);
        assertEquals(chamber.getChamberLoad(), 2);
    }

    @Test
    void getPriceForChamber() {
        Session session = sessionFactory.getCurrentSession();
        final DepartmentEntity department = new DepartmentEntity(null, "getChamberPrice", 3, 3, null, null);
        session.save(department);
        Long chamberId = (Long) session.save (new ChamberEntity(null, 1, department, true, 5, 1, 5, null));

        assertEquals(chamberDao.getPriceForChamber(chamberId), 5);
        assertEquals(chamberDao.getPriceForChamber(0L), 0);
    }

    @Test
    void getEmptyChambers(){
        Session session = sessionFactory.getCurrentSession();
        final DepartmentEntity department = new DepartmentEntity(null, "getEmptyChambers", 3, 3, null, null);
        Long deptId = (Long) session.save(department);
        session.save (new ChamberEntity(null, 1, department, true, 5, 5, 5, null));
        session.save (new ChamberEntity(null, 2, department, true, 5, 1, 5, null));
        Long chamberId = (Long) session.save (new ChamberEntity(null, 3, department, true, 5, 1, 5, null));

        final List<Chamber> emptyChambersByDeptId = chamberDao.getEmptyChambersByDeptId(deptId);
        assertFalse(emptyChambersByDeptId.isEmpty());
        final Long chamberNum = emptyChambersByDeptId.get(emptyChambersByDeptId.size() - 1).getChamberId();
        assertEquals(chamberId, chamberNum);
    }
}
