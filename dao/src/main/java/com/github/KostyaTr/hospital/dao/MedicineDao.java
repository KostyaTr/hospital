package com.github.KostyaTr.hospital.dao;

import com.github.KostyaTr.hospital.model.Medicine;

public interface MedicineDao {
    Long addMedicine(Medicine medicine);

    boolean updateStockBalanceById(Integer stockBalance, Long medicineId);
}
