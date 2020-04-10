package com.github.KostyaTr.hospital.model;

public class Medicine {
    private Long medicineId;
    private String medicineName;
    private Double normalDose;
    private Double criticalDose;
    private Integer packageAmount;
    private Double price;
    private Integer stockBalance;

    public Medicine(Long medicineId, String medicineName, Double normalDose, Double criticalDose,
                    Integer packageAmount, Double price, Integer stockBalance) {

        this.medicineId = medicineId;
        this.medicineName = medicineName;
        this.normalDose = normalDose;
        this.criticalDose = criticalDose;
        this.packageAmount = packageAmount;
        this.price = price;
        this.stockBalance = stockBalance;
    }

    public Long getMedicineId() {
        return medicineId;
    }

    public String getMedicineName() {
        return medicineName;
    }

    public Double getNormalDose() {
        return normalDose;
    }

    public Double getCriticalDose() {
        return criticalDose;
    }

    public Integer getPackageAmount() {
        return packageAmount;
    }

    public Double getPrice() {
        return price;
    }

    public Integer getStockBalance() {
        return stockBalance;
    }
}
