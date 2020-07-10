package com.github.KostyaTr.hospital.service.impl;

import com.github.KostyaTr.hospital.dao.*;
import com.github.KostyaTr.hospital.model.Inpatient;
import com.github.KostyaTr.hospital.model.Receipt;
import com.github.KostyaTr.hospital.service.PriceCalculationService;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;

import static java.time.temporal.ChronoUnit.DAYS;

public class DefaultPriceCalculationService implements PriceCalculationService {
    private final ChamberDao chamberDao;
    private final CardDao cardDao;

    public DefaultPriceCalculationService(ChamberDao chamberDao, CardDao cardDao) {
        this.chamberDao = chamberDao;
        this.cardDao = cardDao;
    }

    @Override
    @Transactional
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
