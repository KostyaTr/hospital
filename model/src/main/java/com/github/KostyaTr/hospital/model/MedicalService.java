package com.github.KostyaTr.hospital.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter @Setter
@AllArgsConstructor
public class MedicalService {
    private Long medicalServiceId;
    private String serviceName;
    private Speciality neededSpeciality;
    private Double serviceCost;
}
