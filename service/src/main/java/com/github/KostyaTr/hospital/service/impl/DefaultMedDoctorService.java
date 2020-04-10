package com.github.KostyaTr.hospital.service.impl;

import com.github.KostyaTr.hospital.dao.*;
import com.github.KostyaTr.hospital.dao.impl.*;
import com.github.KostyaTr.hospital.model.GuestPatient;
import com.github.KostyaTr.hospital.model.Inpatient;
import com.github.KostyaTr.hospital.model.Patient;
import com.github.KostyaTr.hospital.service.MedDoctorService;

import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.util.List;

public class DefaultMedDoctorService implements MedDoctorService {
    private PatientDao patientDao = DefaultPatientDao.getInstance();
    private GuestPatientDao guestPatientDao = DefaultGuestPatientDao.getInstance();
    private InpatientDao inpatientDao = DefaultInpatientDao.getInstance();
    private ChamberDao chamberDao = DefaultChamberDao.getInstance();
    private MedDoctorDao medDoctorDao = DefaultMedDoctorDao.getInstance();


    private static class SingletonHolder {
        static final MedDoctorService HOLDER_INSTANCE = new DefaultMedDoctorService();
    }

    public static MedDoctorService getInstance() {
        return DefaultMedDoctorService.SingletonHolder.HOLDER_INSTANCE;
    }


    @Override
    public List<Patient> getPatientsByDoctorId(Long doctorId) {
        return patientDao.getPatientsByDoctorId(doctorId);
    }

    @Override
    public List<GuestPatient> getGuestPatientsByDoctorId(Long doctorId) {
        return guestPatientDao.getPatientsByDoctorId(doctorId);
    }

    @Override
    public boolean takeThePatient(Long patientId, String condition) {
        if (condition.equals("Bad")){
            chamberDao.getEmptyChambersByDeptId(medDoctorDao.getDepartmentByDoctorId(medDoctorDao.getDoctorByPatientId(patientId).getDoctorId())).get(0);
            // вынести в утилиты
            // поле Inpatient model
            Patient patient = patientDao.getPatientById(patientId);
            chamberDao.putPatientInChamber(new Inpatient(null,
                    patient.getFirstName(),
                    patient.getLastName(),
                    patient.getPhoneNumber(),
                    patient.getEmail(),
                    patient.getCardNum(),
                    patient.getDoctorName(),
                    "patient.departament",
                    chamberDao.getEmptyChambersByDeptId(medDoctorDao.getDepartmentByDoctorId(medDoctorDao.getDoctorByPatientId(patientId).getDoctorId())).get(0),
                    null, null, null, new Timestamp(System.currentTimeMillis())));
            return true;
        } else {
            return patientDao.removePatientById(patientId);
        }
    }

    @Override
    public boolean takeTheGuestPatient(Long guestPatientId, String condition) {
        return false;
    }
}
