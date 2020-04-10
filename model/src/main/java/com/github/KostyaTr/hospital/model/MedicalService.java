package com.github.KostyaTr.hospital.model;

public class MedicalService {
    private Long medicalServiceId;
    private String serviceName;
    private Long neededSpecialityId;
    private Long neededEquipmentId;
    private Double serviceCost;

    public MedicalService(Long medicalServiceId, String serviceName, Long neededSpecialityId,
                          Long neededEquipmentId, Double serviceCost) {

        this.medicalServiceId = medicalServiceId;
        this.serviceName = serviceName;
        this.neededSpecialityId = neededSpecialityId;
        this.neededEquipmentId = neededEquipmentId;
        this.serviceCost = serviceCost;
    }

    public Long getMedicalServiceId() {
        return medicalServiceId;
    }

    public String getServiceName() {
        return serviceName;
    }

    public Long getNeededSpecialityId() {
        return neededSpecialityId;
    }

    public Long getNeededEquipmentId() {
        return neededEquipmentId;
    }

    public Double getServiceCost() {
        return serviceCost;
    }
}
