package com.github.KostyaTr.hospital.service.impl;

import com.github.KostyaTr.hospital.dao.MedDoctorDao;
import com.github.KostyaTr.hospital.dao.impl.DefaultMedDoctorDao;
import com.github.KostyaTr.hospital.service.HeadPhysicianService;
import com.github.KostyaTr.hospital.model.MedDoctor;


public class DefaultHeadPhysicianService extends DefaultMedDoctorService implements HeadPhysicianService {
    private MedDoctorDao medDoctorDao = DefaultMedDoctorDao.getInstance();

    private static volatile HeadPhysicianService instance;

    public static HeadPhysicianService getInstance(){
        HeadPhysicianService localInstance = instance;
        if (localInstance == null){
            synchronized (HeadPhysicianService.class){
                localInstance = instance;
                if (localInstance == null){
                    instance = localInstance = new DefaultHeadPhysicianService();
                }
            }
        }
        return localInstance;
    }




    @Override
    public boolean fireDoctor(MedDoctor medDoctor) {
        return medDoctorDao.removeDoctor(medDoctor);
    }
}
