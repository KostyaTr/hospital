package com.github.KostyaTr.hospital.impl;

import com.github.KostyaTr.hospital.dao.*;
import com.github.KostyaTr.hospital.model.*;
import com.github.KostyaTr.hospital.service.impl.DefaultMedDoctorService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class DefaultMedDoctorServiceTest {

    @Mock
    InpatientDao inpatientDao;
    @Mock
    PatientDao patientDao;
    @Mock
    TreatmentCourseDao treatmentCourseDao;
    @Mock
    ChamberDao chamberDao;
    @Mock
    MedDoctorDao medDoctorDao;


    @InjectMocks
    DefaultMedDoctorService medDoctorService;

    @Test
    void prescribeTreatmentCourseWithoutDiagnoseTest(){
        when(inpatientDao.getInpatientById(1L)).thenReturn(new Inpatient(null, null,null,null,null,null,null,null,null,null,null,null));
        boolean check = medDoctorService.prescribeTreatmentCourse(1L, 1L);
        assertFalse(check);
    }

    @Test
    void prescribeTreatmentCourseWithDiagnoseTest(){
        final Inpatient inpatient = new Inpatient(null, null,null,null,null,1L,null,null,"",null,null,null);
        when(inpatientDao.getInpatientById(1L)).thenReturn(inpatient);
        boolean check = medDoctorService.prescribeTreatmentCourse(1L, 1L);
        assertTrue(check);
    }

    @Test
    void takeThePatientWithGoodConditionTest(){
        when(patientDao.removePatientById(1L)).thenReturn(true);
        boolean check = medDoctorService.takeThePatient(1L, Status.GOOD);
        assertTrue(check);
    }

    @Test
    void takeThePatientWithBadAndEmptyChamberConditionTest(){
        Patient patient = new Patient(null,null,null,null,null,1L,
                new MedDoctor(null,null,null,null,null,null, new Department(1L, null),null,true),
                1,null, null);
        when(patientDao.getPatientById(1L)).thenReturn(patient);
        List<Chamber> chambers = Collections.singletonList(new Chamber(null,1,null,3,5,false, 20d));
        when(chamberDao.getEmptyChambersByDeptId(1L)).thenReturn(chambers);
        boolean check = medDoctorService.takeThePatient(1L, Status.BAD);
        assertFalse(check);
    }

    @Test
    void takeThePatientWithBadAndFullChamberConditionTest(){
        Patient patient = new Patient(null,null,null,null,null,1L,
                new MedDoctor(null,null,null,null,null,null, new Department(1L, null),null,true),
                1,null, null);
        when(patientDao.getPatientById(1L)).thenReturn(patient);
        when(chamberDao.getEmptyChambersByDeptId(1L)).thenReturn(new ArrayList<>());
        boolean check = medDoctorService.takeThePatient(1L, Status.BAD);
        assertFalse(check);
    }

    @Test
    void dischargeBadInpatientTest(){
        boolean check = medDoctorService.dischargeInpatient(
                new Inpatient(
                        null, null,null,null,null, null,null,null,null,
                        null,Status.BAD,null));
        assertFalse(check);
    }
}
