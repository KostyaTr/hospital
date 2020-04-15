package com.github.KostyaTr.hospital.dao;

import com.github.KostyaTr.hospital.model.Medicine;

import java.util.List;

public interface MedicineDao {
    List<Medicine> getMedicine();

    Long addMedicine(Medicine medicine);

    Medicine getMedicineByName(String medicineName);

    boolean updateStockBalanceById(Integer stockBalance, Long medicineId);
}
