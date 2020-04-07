package com.github.KostyaTr.hospital.dao.impl;

import com.github.KostyaTr.hospital.dao.HeadPhysicianDao;
import com.github.KostyaTr.hospital.model.HeadPhysician;

import java.util.ArrayList;
import java.util.List;

public class DefaultHeadPhysicianDao implements HeadPhysicianDao {
    private List<HeadPhysician> headPhysicians;

    public DefaultHeadPhysicianDao() {
        this.headPhysicians = new ArrayList<>();
        this.headPhysicians.add(new HeadPhysician("HeadSurgeon","Sel", "Lock",
                "Surgeon", "118-47-77", "SelLock@google.com", "Surgical department"));
    }


    private static volatile HeadPhysicianDao instance;

    public static HeadPhysicianDao getInstance() {
        HeadPhysicianDao localInstance = instance;
        if (localInstance == null) {
            synchronized (HeadPhysicianDao.class) {
                localInstance = instance;
                if (localInstance == null) {
                    instance = localInstance = new DefaultHeadPhysicianDao();
                }
            }
        }
        return localInstance;
    }


    @Override
    public HeadPhysician getHeadPhysicianByDepartment(String department) {
        for (HeadPhysician headPhysician : headPhysicians) {
            if (department.equals(headPhysician.getDepartment())){
                return headPhysician;
            }
        }
        return null;
    }

    @Override
    public List<HeadPhysician> getHeadPhysicians() {
        return headPhysicians;
    }
}
