package com.github.KostyaTr.hospital.dao;

import com.github.KostyaTr.hospital.model.Chamber;

import java.util.List;

public interface ChamberDao {
    void updateChamberLoad(Long id, int load);

    List<Chamber> getEmptyChambersByDeptId(Long deptId);

    double getPriceForChamber(Long deptChamberId);
}
