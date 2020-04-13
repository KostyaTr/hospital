package com.github.KostyaTr.hospital.dao;

import com.github.KostyaTr.hospital.model.Inpatient;
import java.util.List;

public interface InpatientDao {
    List<Inpatient> getPatients();

    Long addInpatient(Inpatient inpatient);
}
