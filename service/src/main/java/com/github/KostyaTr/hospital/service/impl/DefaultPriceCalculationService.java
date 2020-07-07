package com.github.KostyaTr.hospital.service.impl;

import com.github.KostyaTr.hospital.dao.*;
import com.github.KostyaTr.hospital.dao.impl.*;
import com.github.KostyaTr.hospital.model.Inpatient;
import com.github.KostyaTr.hospital.model.Receipt;
import com.github.KostyaTr.hospital.service.PriceCalculationService;

import java.time.LocalDate;

import static java.time.temporal.ChronoUnit.DAYS;

public class DefaultPriceCalculationService implements PriceCalculationService {
    private ChamberDao chamberDao = DefaultChamberDao.getInstance();
    private CardDao cardDao = DefaultCardDao.getInstance();

    public static PriceCalculationService getInstance() {
        return DefaultPriceCalculationService.SingletonHolder.HOLDER_INSTANCE;
    }

    private static class SingletonHolder {
        static final PriceCalculationService HOLDER_INSTANCE = new DefaultPriceCalculationService();
    }

    @Override
    public Receipt calculateReceipt(Inpatient inpatient) {
        if (insuranceCheck(inpatient)){
          return null;
        }
        LocalDate enrollmentDate = inpatient.getEnrollmentDate();

        long timeAtChamber = DAYS.between(enrollmentDate, LocalDate.now());
        // get price a day from database to have actual price for chamber at the moment
        double priceForChamber = chamberDao.getPriceForChamber(inpatient.getChamber().getChamberId()) * timeAtChamber;
        // get price from object because medicine was by that price and not actual at the moment
        // *1d to convert from int to double
        double medicineAmount = Math.ceil(inpatient.getTreatmentCourse().getDurationInDays()
                * inpatient.getTreatmentCourse().getTimesPerDay() * 1d / inpatient.getTreatmentCourse().getPackageAmount());
        double priceForMedicine = inpatient.getTreatmentCourse().getPrice() * medicineAmount;

        return new Receipt(inpatient.getUserId(), priceForChamber, priceForMedicine);

    }

    private boolean insuranceCheck(Inpatient inpatient){
        return cardDao.getCardByUserId(inpatient.getUserId()).isInsurance();
    }
}
