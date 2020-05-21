package com.github.KostyaTr.hospital.model;

public class Speciality {
    private Long specialityId;
    private String specialityName;

    public Speciality(Long specialityId, String specialityName) {
        this.specialityId = specialityId;
        this.specialityName = specialityName;
    }

    public Long getSpecialityId() {
        return specialityId;
    }

    public String getSpecialityName() {
        return specialityName;
    }
}
