package com.github.KostyaTr.hospital.model;

public class Chamber {
    private Long chamberId;
    private int chamberNum;
    private Department department;
    private int chamberLoad;
    private int chamberCapacity;
    private boolean vip;
    private double priceADay;

    public Chamber(Long chamberId, int chamberNum, Department department,
                   int chamberLoad, int chamberCapacity, boolean vip, double priceADay) {

        this.chamberId = chamberId;
        this.chamberNum = chamberNum;
        this.department = department;
        this.chamberLoad = chamberLoad;
        this.chamberCapacity = chamberCapacity;
        this.vip = vip;
        this.priceADay = priceADay;
    }

    public Long getChamberId() {
        return chamberId;
    }

    public int getChamberNum() {
        return chamberNum;
    }

    public Department getDepartment() {
        return department;
    }

    public int getChamberLoad() {
        return chamberLoad;
    }

    public int getChamberCapacity() {
        return chamberCapacity;
    }

    public boolean isVip() {
        return vip;
    }

    public double getPriceADay() {
        return priceADay;
    }
}
