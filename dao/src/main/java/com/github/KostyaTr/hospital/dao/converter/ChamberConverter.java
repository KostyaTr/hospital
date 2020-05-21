package com.github.KostyaTr.hospital.dao.converter;

import com.github.KostyaTr.hospital.dao.entity.ChamberEntity;
import com.github.KostyaTr.hospital.model.Chamber;

public class ChamberConverter {
    public static Chamber fromEntity(ChamberEntity chamberEntity) {
        if (chamberEntity == null) {
            return null;
        }
        return new Chamber(
                chamberEntity.getId(),
                chamberEntity.getChamberNum(),
                DepartmentConverter.fromEntity(chamberEntity.getDepartment()),
                chamberEntity.getChamberLoad(),
                chamberEntity.getChamberCapacity(),
                chamberEntity.isVip(),
                chamberEntity.getPriceADay());
    }

    public static ChamberEntity toEntity(Chamber chamber) {
        if (chamber == null) {
            return null;
        }
        final ChamberEntity chamberEntity = new ChamberEntity();
        chamberEntity.setId(chamber.getChamberId());
        chamberEntity.setChamberNum(chamber.getChamberNum());
        chamberEntity.setDepartment(DepartmentConverter.toEntity(chamber.getDepartment()));
        chamberEntity.setChamberLoad(chamber.getChamberLoad());
        chamberEntity.setChamberCapacity(chamber.getChamberCapacity());
        chamberEntity.setVip(chamber.isVip());
        chamberEntity.setPriceADay(chamber.getPriceADay());
        return chamberEntity;
    }
}
