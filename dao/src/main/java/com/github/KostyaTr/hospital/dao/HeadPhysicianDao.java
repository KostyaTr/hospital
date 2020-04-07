package com.github.KostyaTr.hospital.dao;

import com.github.KostyaTr.hospital.model.HeadPhysician;

import java.util.List;

public interface HeadPhysicianDao {

    HeadPhysician getHeadPhysicianByDepartment(String department);

    List<HeadPhysician>  getHeadPhysicians();
}
