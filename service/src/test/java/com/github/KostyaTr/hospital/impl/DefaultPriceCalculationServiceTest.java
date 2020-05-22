package com.github.KostyaTr.hospital.impl;
import com.github.KostyaTr.hospital.dao.CardDao;
import com.github.KostyaTr.hospital.dao.ChamberDao;
import com.github.KostyaTr.hospital.model.*;
import com.github.KostyaTr.hospital.service.impl.DefaultPriceCalculationService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDate;
import java.time.ZoneId;
import java.util.Date;

import static java.util.Date.from;
import static org.mockito.Mockito.*;

import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(MockitoExtension.class)
public class DefaultPriceCalculationServiceTest {

    @Mock
    ChamberDao chamberDao;

    @Mock
    CardDao cardDao;

    @InjectMocks
    DefaultPriceCalculationService defaultPriceCalculationService;

    @Test
    void withInsurance(){
        Inpatient inpatient = new Inpatient(1L, null,null,null,null,null,null,null,null,null,null,null);

        when(cardDao.getCardByUserId(1L)).thenReturn(new Card(null, null,null, null,null, true));

        assertNull(defaultPriceCalculationService.calculateReceipt(inpatient));
    }

    @Test
    void withoutInsurance(){
        LocalDate date = LocalDate.now().plusDays(-2);
        Date enrollmentDate = from(date.atStartOfDay().atZone(ZoneId.systemDefault()).toInstant());
        Inpatient inpatient = new Inpatient(1L, null,null,null,null,null,
                null, new Chamber(1L, 1,null,1,1, true, 1d),
                null,
                new TreatmentCourse(null, null,null,null,2, 20d, null, 10d,null, 1,3),
                null,enrollmentDate);

        when(cardDao.getCardByUserId(1L)).thenReturn(new Card(null, null,null, null,null, false));
        when(chamberDao.getPriceForChamber(1L)).thenReturn(200d);
        final Receipt receipt = defaultPriceCalculationService.calculateReceipt(inpatient);

        assertNotNull(receipt);
        assertEquals((long) receipt.getUserId(), 1L);
        assertEquals(receipt.getPriceForChamber(), 400d);
        assertEquals(receipt.getPriceForMedicine(), 40d);
    }
}
