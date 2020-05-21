package com.github.KostyaTr.hospital.dao;

import java.util.List;

public interface ChamberDao {
    void updateChamberLoad(Long id, int load);

    List<Long> getEmptyChambersByDeptId(Long deptId);

    double getPriceForChamber(Long deptChamberId);
}
