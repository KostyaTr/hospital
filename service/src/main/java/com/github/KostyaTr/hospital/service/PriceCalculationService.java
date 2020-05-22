package com.github.KostyaTr.hospital.service;

import com.github.KostyaTr.hospital.model.Inpatient;
import com.github.KostyaTr.hospital.model.Receipt;

public interface PriceCalculationService {
    Receipt calculateReceipt(Inpatient inpatient);
}
