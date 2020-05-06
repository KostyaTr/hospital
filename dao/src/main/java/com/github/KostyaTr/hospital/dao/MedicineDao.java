package com.github.KostyaTr.hospital.dao;

import com.github.KostyaTr.hospital.model.Medicine;

import java.util.List;

public interface MedicineDao {
    List<Medicine> getMedicine();

    Medicine getMedicineByName(String medicineName);

    Medicine getMedicineById(Long medicineId);
}
