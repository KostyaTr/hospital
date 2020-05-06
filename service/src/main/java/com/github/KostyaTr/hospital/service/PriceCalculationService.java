package com.github.KostyaTr.hospital.service;

import com.github.KostyaTr.hospital.model.Inpatient;

public interface PriceCalculationService {
    boolean calculateReceipt(Inpatient inpatient);
}
