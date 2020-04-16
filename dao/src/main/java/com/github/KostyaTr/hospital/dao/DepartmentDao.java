package com.github.KostyaTr.hospital.dao;

import java.util.List;

public interface DepartmentDao {
    List<String> getDepartments();

    String getDepartmentById(Long id);
}
