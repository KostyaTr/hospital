package com.github.KostyaTr.hospital.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter @Setter
@AllArgsConstructor
public class Medicine {
    private Long medicineId;
    private String medicineName;
    private Double normalDose;
    private Double criticalDose;
    private Integer packageAmount;
    private Double price;
}
