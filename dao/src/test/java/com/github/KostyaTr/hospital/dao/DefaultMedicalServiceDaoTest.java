package com.github.KostyaTr.hospital.dao;

import com.github.KostyaTr.hospital.dao.entity.MedicalServiceEntity;
import com.github.KostyaTr.hospital.dao.entity.SpecialityEntity;
import com.github.KostyaTr.hospital.dao.impl.DefaultMedicalServiceDao;
import com.github.KostyaTr.hospital.model.MedicalService;
import org.hibernate.Session;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

public class DefaultMedicalServiceDaoTest {
    private MedicalServiceDao medicalServiceDao = DefaultMedicalServiceDao.getInstance();

    @Test
    void getMedicalServiceById() {
        Session session = HibernateUtil.getSession();
        session.beginTransaction();
        final SpecialityEntity speciality = new SpecialityEntity(null, "getMedicalServicesCheckById", null, null);
        session.save(speciality);
        Long medicalServiceId = (Long) session.save(new MedicalServiceEntity(null, "getMedicalServicesCheckById", speciality, 1d, null, null));
        session.getTransaction().commit();
        assertNotNull(medicalServiceDao.getMedicalServiceById(medicalServiceId));
        assertEquals(medicalServiceDao.getMedicalServiceById(medicalServiceId).getServiceName(), "getMedicalServicesCheckById");
        assertNull(medicalServiceDao.getMedicalServiceById(0L));
    }

    @Test
    void getMedicalServices(){
        Session session = HibernateUtil.getSession();
        session.beginTransaction();
        final SpecialityEntity speciality = new SpecialityEntity(null, "getMedicalServicesCheck", null, null);
        session.save(speciality);
        session.save(new MedicalServiceEntity(null, "getMedicalServicesCheck1", speciality, 1d, null, null));
        session.save(new MedicalServiceEntity(null, "getMedicalServicesCheck2", speciality, 1d, null, null));
        session.getTransaction().commit();
        final List<MedicalService> medicalServices = medicalServiceDao.getMedicalServices();
        assertFalse(medicalServices.isEmpty());
        assertEquals(medicalServices.get(medicalServices.size() - 1).getServiceName(), "getMedicalServicesCheck2");
    }
}
