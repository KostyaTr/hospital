package com.github.KostyaTr.hospital.dao.converter;

import com.github.KostyaTr.hospital.dao.entity.ReceiptEntity;
import com.github.KostyaTr.hospital.model.Receipt;

public class ReceiptConverter {
    public static Receipt fromEntity(ReceiptEntity receiptEntity) {
        if (receiptEntity == null) {
            return null;
        }
        return new Receipt(
                receiptEntity.getUserId(),
                receiptEntity.getPriceForChamber(),
                receiptEntity.getPriceForMedicine()
        );
    }

    public static ReceiptEntity toEntity(Receipt receipt) {
        if (receipt == null) {
            return null;
        }
        final ReceiptEntity receiptEntity = new ReceiptEntity();
        receiptEntity.setUserId(receipt.getUserId());
        receiptEntity.setPriceForChamber(receipt.getPriceForChamber());
        receiptEntity.setPriceForMedicine(receipt.getPriceForMedicine());
        return receiptEntity;
    }
}
