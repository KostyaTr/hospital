package com.github.KostyaTr.hospital.dao.converter;

import com.github.KostyaTr.hospital.dao.entity.MedicineEntity;
import com.github.KostyaTr.hospital.model.Medicine;

public class MedicineConverter {
    public static Medicine fromEntity(MedicineEntity medicineEntity) {
        if (medicineEntity == null) {
            return null;
        }
        return new Medicine(
                medicineEntity.getMedicineId(),
                medicineEntity.getMedicineName(),
                medicineEntity.getNormalDose(),
                medicineEntity.getCriticalDose(),
                medicineEntity.getPackageAmount(),
                medicineEntity.getPrice());
    }

    public static MedicineEntity toEntity(Medicine medicine) {
        if (medicine == null) {
            return null;
        }
        final MedicineEntity medicineEntity = new MedicineEntity();
        medicineEntity.setMedicineId(medicine.getMedicineId());
        medicineEntity.setMedicineName(medicine.getMedicineName());
        medicineEntity.setNormalDose(medicine.getNormalDose());
        medicineEntity.setCriticalDose(medicine.getCriticalDose());
        medicineEntity.setPackageAmount(medicine.getPackageAmount());
        medicineEntity.setPrice(medicine.getPrice());
        return medicineEntity;
    }
}
