package com.github.KostyaTr.hospital.model;

public class Receipt {
    private Long userId;
    private double priceForChamber;
    private double priceForMedicine;

    public Receipt(Long userId, double priceForChamber, double priceForMedicine) {
        this.userId = userId;
        this.priceForChamber = priceForChamber;
        this.priceForMedicine = priceForMedicine;
    }

    public Long getUserId() {
        return userId;
    }

    public double getPriceForChamber() {
        return priceForChamber;
    }

    public double getPriceForMedicine() {
        return priceForMedicine;
    }
}
