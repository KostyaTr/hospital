package com.github.KostyaTr.hospital.service.impl;

import com.github.KostyaTr.hospital.dao.*;
import com.github.KostyaTr.hospital.dao.impl.*;
import com.github.KostyaTr.hospital.model.Inpatient;
import com.github.KostyaTr.hospital.model.Medicine;
import com.github.KostyaTr.hospital.model.Receipt;
import com.github.KostyaTr.hospital.model.TreatmentCourse;
import com.github.KostyaTr.hospital.service.PriceCalculationService;

import java.time.LocalDate;
import java.time.ZoneId;
import java.util.Date;

import static java.time.temporal.ChronoUnit.DAYS;

public class DefaultPriceCalculationService implements PriceCalculationService {
    private ChamberDao chamberDao = DefaultChamberDao.getInstance();
    private TreatmentCourseDao treatmentCourseDao = DefaultTreatmentCourseDao.getInstance();
    private MedicineDao medicineDao = DefaultMedicineDao.getInstance();
    private ReceiptDao receiptDao = DefaultReceiptDao.getInstance();
    private CardDao cardDao = DefaultCardDao.getInstance();

    public static PriceCalculationService getInstance() {
        return DefaultPriceCalculationService.SingletonHolder.HOLDER_INSTANCE;
    }

    private static class SingletonHolder {
        static final PriceCalculationService HOLDER_INSTANCE = new DefaultPriceCalculationService();
    }

    @Override
    public boolean calculateReceipt(Inpatient inpatient) {
        if (insuranceCheck(inpatient)){
          return false;
        }
        LocalDate enrollmentDate;


        enrollmentDate = new Date(inpatient.getEnrollmentDate().getTime())
                    .toInstant().atZone(ZoneId.systemDefault()).toLocalDate();

        long timeAtChamber = DAYS.between(enrollmentDate, LocalDate.now());
        double priceForChamber = chamberDao.getPriceForChamber(inpatient.getDeptChamberId()) * timeAtChamber;
        TreatmentCourse treatmentCourse = treatmentCourseDao.getTreatmentCourseById(inpatient.getTreatmentCourseId());
        Medicine medicine = medicineDao.getMedicineById(treatmentCourse.getMedicineId());

        double medicineAmount = Math.ceil(treatmentCourse.getDurationInDays()
                * treatmentCourse.getTimesPerDay()
                * treatmentCourse.getMedicineDose() / medicine.getPackageAmount());
        double priceForMedicine = medicine.getPrice() * medicineAmount;

        if (receiptDao.getReceiptByUserId(inpatient.getUserId()) != null){
            return receiptDao.updateReceipt(new Receipt(inpatient.getUserId(), priceForChamber, priceForMedicine));
        } else {
            return receiptDao.insertReceipt(new Receipt(inpatient.getUserId(), priceForChamber, priceForMedicine));
        }
    }

    private boolean insuranceCheck(Inpatient inpatient){
        return cardDao.getCardByUserId(inpatient.getUserId()).isInsurance();
    }
}
