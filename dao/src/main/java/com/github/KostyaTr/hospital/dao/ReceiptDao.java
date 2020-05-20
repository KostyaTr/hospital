package com.github.KostyaTr.hospital.dao;

import com.github.KostyaTr.hospital.model.Receipt;

public interface ReceiptDao {
    Receipt getReceiptByUserId(Long userId);

    boolean insertOrUpdateReceipt(Receipt receipt);

    //boolean insertReceipt(Receipt receipt);
}
