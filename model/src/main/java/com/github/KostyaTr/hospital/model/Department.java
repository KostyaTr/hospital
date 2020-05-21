package com.github.KostyaTr.hospital.model;

public class Department {
    private Long departmentId;
    private String departmentName;


    public Department(Long departmentId, String departmentName) {
        this.departmentId = departmentId;
        this.departmentName = departmentName;
    }

    public Long getDepartmentId() {
        return departmentId;
    }

    public String getDepartmentName() {
        return departmentName;
    }
}
