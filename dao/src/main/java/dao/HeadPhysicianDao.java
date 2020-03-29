package dao;

import model.HeadPhysician;

import java.util.List;

public interface HeadPhysicianDao {

    HeadPhysician getHeadPhysicianByDepartment(String department);

    List<HeadPhysician>  getHeadPhysicians();
}
