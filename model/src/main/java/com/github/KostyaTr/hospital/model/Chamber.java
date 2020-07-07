package com.github.KostyaTr.hospital.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter @Setter
@AllArgsConstructor
public class Chamber {
    private Long chamberId;
    private int chamberNum;
    private Department department;
    private int chamberLoad;
    private int chamberCapacity;
    private boolean vip;
    private double priceADay;
}
