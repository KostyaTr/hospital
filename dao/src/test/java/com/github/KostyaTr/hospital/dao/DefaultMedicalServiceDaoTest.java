package com.github.KostyaTr.hospital.dao;

import com.github.KostyaTr.hospital.dao.config.DaoConfig;
import com.github.KostyaTr.hospital.dao.entity.MedicalServiceEntity;
import com.github.KostyaTr.hospital.dao.entity.SpecialityEntity;
import com.github.KostyaTr.hospital.dao.impl.DefaultMedicalServiceDao;
import com.github.KostyaTr.hospital.model.MedicalService;
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
public class DefaultMedicalServiceDaoTest {
    @Autowired
    private SessionFactory sessionFactory;

    @Autowired
    private MedicalServiceDao medicalServiceDao;

    @Test
    void getMedicalServiceById() {
        Session session = sessionFactory.getCurrentSession();
        final SpecialityEntity speciality = new SpecialityEntity(null, "getMedicalServicesCheckById", null, null);
        session.save(speciality);
        Long medicalServiceId = (Long) session.save(new MedicalServiceEntity(null, "getMedicalServicesCheckById", speciality, 1d, null, null));

        assertNotNull(medicalServiceDao.getMedicalServiceById(medicalServiceId));
        assertEquals(medicalServiceDao.getMedicalServiceById(medicalServiceId).getServiceName(), "getMedicalServicesCheckById");
        assertNull(medicalServiceDao.getMedicalServiceById(0L));
    }

    @Test
    void getMedicalServices(){
        Session session = sessionFactory.getCurrentSession();
        final SpecialityEntity speciality = new SpecialityEntity(null, "getMedicalServicesCheck", null, null);
        session.save(speciality);
        session.save(new MedicalServiceEntity(null, "getMedicalServicesCheck1", speciality, 1d, null, null));
        session.save(new MedicalServiceEntity(null, "getMedicalServicesCheck2", speciality, 1d, null, null));
        final List<MedicalService> medicalServices = medicalServiceDao.getMedicalServices();
        assertFalse(medicalServices.isEmpty());
        assertEquals(medicalServices.get(medicalServices.size() - 1).getServiceName(), "getMedicalServicesCheck2");
    }
}
