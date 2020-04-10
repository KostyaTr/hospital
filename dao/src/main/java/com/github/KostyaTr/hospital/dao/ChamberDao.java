package com.github.KostyaTr.hospital.dao;

import com.github.KostyaTr.hospital.model.Inpatient;

import java.util.List;

public interface ChamberDao {
    Integer getChamberCapacity(Long id);

    Integer getChamberLoad(Long id);

    Long putPatientInChamber(Inpatient inpatientId);

    boolean dischargeFromChamber(Long patientId);

    List<Long> getFullChambersByDeptId(Long deptId);

    List<Long> getEmptyChambersByDeptId(Long deptId);
}
