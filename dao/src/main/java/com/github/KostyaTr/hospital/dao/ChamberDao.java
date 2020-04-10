package com.github.KostyaTr.hospital.dao;


import java.util.List;

public interface ChamberDao {
    boolean updateChamberCapacity(Long id, int capacity);

    List<Long> getEmptyChambersByDeptId(Long deptId);

    List<Long> getEmptyVipChambersByDeptId(Long deptId);
}
