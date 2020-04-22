package com.github.KostyaTr.hospital.impl;

import com.github.KostyaTr.hospital.dao.ChamberDao;
import com.github.KostyaTr.hospital.dao.InpatientDao;
import com.github.KostyaTr.hospital.dao.MedDoctorDao;
import com.github.KostyaTr.hospital.dao.PatientDao;
import com.github.KostyaTr.hospital.model.Inpatient;
import com.github.KostyaTr.hospital.model.MedDoctor;
import com.github.KostyaTr.hospital.model.Patient;
import com.github.KostyaTr.hospital.model.Status;
import com.github.KostyaTr.hospital.service.impl.DefaultMedDoctorService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.ArrayList;
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
    ChamberDao chamberDao;
    @Mock
    MedDoctorDao medDoctorDao;


    @InjectMocks
    DefaultMedDoctorService medDoctorService;

    @Test
    void prescribeTreatmentCourseWithoutDiagnoseTest(){
        when(inpatientDao.getInpatientById(1L)).thenReturn(new Inpatient(null, null,null,null,null,null,null,null,null));
        boolean check = medDoctorService.prescribeTreatmentCourse(1L, 1L);
        assertFalse(check);
    }

    @Test
    void prescribeTreatmentCourseWithDiagnoseTest(){
        when(inpatientDao.getInpatientById(1L)).thenReturn(new Inpatient(null, null,null,null,"diagnose",null,null,null,null));
        when(inpatientDao.updateTreatmentCourse(1L, 1L)).thenReturn(true);
        boolean check = medDoctorService.prescribeTreatmentCourse(1L, 1L);
        assertTrue(check);
    }

    @Test
    void takeThePatientWithGoodConditionTest(){
        when(patientDao.removePatientById(1L)).thenReturn(true);
        boolean check = medDoctorService.takeThePatient(1L, Status.GOOD);
        assertTrue(check);
    }

    /*@Test
    void takeThePatientWithBadAndEmptyChamberConditionTest(){
        when(patientDao.getPatientById(1L)).thenReturn(new Patient(null,1L,1L,null,null, null));
        when(medDoctorDao.getDoctorById(1L)).thenReturn(new MedDoctor(1L, null, 1L, false));
        List<Long> list = new ArrayList<>();
        list.add(1L);
        when(chamberDao.getEmptyChambersByDeptId(1L)).thenReturn(list);
        *//*doReturn(1L).when(inpatientDao).addInpatient(argThat(new Inpatient(null, 1L, 1L, 1L, null,
                null, null, "Bad",  new Date()))); question about comparing objects using mockito*//*
         boolean check = medDoctorService.takeThePatient(1L, "Bad");
        assertTrue(check);
    }
*/
    @Test
    void takeThePatientWithBadAndFullChamberConditionTest(){
        when(patientDao.getPatientById(1L)).thenReturn(new Patient(null,1L,1L,null,null, null));
        when(medDoctorDao.getDoctorById(1L)).thenReturn(new MedDoctor(1L, null, 1L, false));
        List<Long> list = new ArrayList<>();
        when(chamberDao.getEmptyChambersByDeptId(1L)).thenReturn(list);
        boolean check = medDoctorService.takeThePatient(1L, Status.BAD);
        assertFalse(check);
    }

    @Test
    void dischargeBadInpatientTest(){
        boolean check = medDoctorService.dischargeInpatient(
                new com.github.KostyaTr.hospital.model.display.Inpatient(
                        null, null,null,null,null,
                        null,Status.BAD,null));
        assertFalse(check);
    }

   /* @Test
    void dischargeGoodInpatientTest(){
        boolean check = medDoctorService.dischargeInpatient(
                new com.github.KostyaTr.hospital.model.display.Inpatient(
                        1L, null,null,null,null,
                        null,"Good",null));
        assertTrue(check);
    }*/
}
