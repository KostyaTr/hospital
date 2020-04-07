package com.github.KostyaTr.hospital.service;

import com.github.KostyaTr.hospital.model.MedDoctor;

public interface HeadPhysicianService extends MedDoctorService {
    boolean fireDoctor(MedDoctor medDoctor);
}
