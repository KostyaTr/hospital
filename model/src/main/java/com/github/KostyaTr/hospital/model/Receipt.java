package com.github.KostyaTr.hospital.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter @Setter
@AllArgsConstructor
public class Receipt {
    private Long userId;
    private double priceForChamber;
    private double priceForMedicine;
}
