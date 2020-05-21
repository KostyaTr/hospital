package com.github.KostyaTr.hospital.model;

import java.util.List;

public class MedDoctor extends User {
    private Long doctorId;
    private Department department;
    private List<Speciality> specialityList;
    private boolean headOfDept;

    public MedDoctor(Long userId, String firstName, String lastName,
                     String phoneNumber, String email, Long doctorId,
                     Department department, List<Speciality> specialityList,
                     boolean headOfDept) {

        super(userId, firstName, lastName, phoneNumber, email);
        this.doctorId = doctorId;
        this.department = department;
        this.specialityList = specialityList;
        this.headOfDept = headOfDept;
    }

    public Long getDoctorId() {
        return doctorId;
    }

    public Department getDeptNum() {
        return department;
    }

    public List<Speciality> getSpecialityList() {
        return specialityList;
    }

    public boolean isHeadOfDept() {
        return headOfDept;
    }
}
