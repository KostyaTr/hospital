package com.github.KostyaTr.hospital.dao.converter;

import com.github.KostyaTr.hospital.dao.entity.DepartmentEntity;
import com.github.KostyaTr.hospital.model.Department;

public class DepartmentConverter {
    public static Department fromEntity(DepartmentEntity departmentEntity) {
        if (departmentEntity == null) {
            return null;
        }
        return new Department(
                departmentEntity.getDepartmentId(),
                departmentEntity.getDepartmentName()
                );
    }

    public static DepartmentEntity toEntity(Department department) {
        if (department == null) {
            return null;
        }
        final DepartmentEntity departmentEntity = new DepartmentEntity();
        departmentEntity.setDepartmentId(department.getDepartmentId());
        departmentEntity.setDepartmentName(department.getDepartmentName());
        return departmentEntity;
    }
}
