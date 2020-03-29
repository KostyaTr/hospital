package service.impl;

import dao.MedDoctorDao;
import dao.impl.DefaultMedDoctorDao;
import model.MedDoctor;
import service.HeadPhysicianService;


public class DefaultHeadPhysicianService extends DefaultMedDoctorService implements HeadPhysicianService{
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
