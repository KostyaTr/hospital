package com.github.KostyaTr.hospital.model;

public class MedicalService {
    private Long medicalServiceId;
    private String serviceName;
    private Speciality neededSpeciality;
    private Double serviceCost;

    public MedicalService(Long medicalServiceId, String serviceName,
                          Speciality neededSpeciality, Double serviceCost) {

        this.medicalServiceId = medicalServiceId;
        this.serviceName = serviceName;
        this.neededSpeciality = neededSpeciality;
        this.serviceCost = serviceCost;
    }

    public Long getMedicalServiceId() {
        return medicalServiceId;
    }

    public String getServiceName() {
        return serviceName;
    }

    public Speciality getNeededSpeciality() {
        return neededSpeciality;
    }

    public Double getServiceCost() {
        return serviceCost;
    }
}
