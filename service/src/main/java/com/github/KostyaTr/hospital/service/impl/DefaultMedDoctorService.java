package com.github.KostyaTr.hospital.service.impl;

import com.github.KostyaTr.hospital.dao.*;
import com.github.KostyaTr.hospital.dao.impl.*;
import com.github.KostyaTr.hospital.model.*;
import com.github.KostyaTr.hospital.service.MedDoctorService;

import java.util.Date;
import java.util.List;

public class DefaultMedDoctorService implements MedDoctorService {
    private PatientDao patientDao = DefaultPatientDao.getInstance();
    private GuestPatientDao guestPatientDao = DefaultGuestPatientDao.getInstance();
    private InpatientDao inpatientDao = DefaultInpatientDao.getInstance();
    private ChamberDao chamberDao = DefaultChamberDao.getInstance();
    private MedDoctorDao medDoctorDao = DefaultMedDoctorDao.getInstance();
    private CardDao cardDao = DefaultCardDao.getInstance();
    private MedicineDao medicineDao = DefaultMedicineDao.getInstance();
    private TreatmentCourseDao treatmentCourseDao = DefaultTreatmentCourseDao.getInstance();
    private DischargedPatientDao dischargedPatientDao = DefaultDischargedPatientDao.getInstance();
    private com.github.KostyaTr.hospital.dao.display.TreatmentCourseDao treatmentCourseDaoDisp = com.github.KostyaTr.hospital.dao.impl.display.DefaultTreatmentCourseDao.getInstance();
    private com.github.KostyaTr.hospital.dao.display.InpatientDao inpatientDaoDisp = com.github.KostyaTr.hospital.dao.impl.display.DefaultInpatientDao.getInstance();
    private com.github.KostyaTr.hospital.dao.display.PatientDao patientDaoDisp = com.github.KostyaTr.hospital.dao.impl.display.DefaultPatientDao.getInstance();
    private com.github.KostyaTr.hospital.dao.display.GuestPatientDao guestPatientDaoDisp = com.github.KostyaTr.hospital.dao.impl.display.DefaultGuestPatientDao.getInstance();

    private final String PATIENT_CURED = "cured";
    private final int FREE_CHAMBER = 0;
    private final int LOAD = 1;


    private static class SingletonHolder {
        static final MedDoctorService HOLDER_INSTANCE = new DefaultMedDoctorService();
    }

    public static MedDoctorService getInstance() {
        return DefaultMedDoctorService.SingletonHolder.HOLDER_INSTANCE;
    }


    @Override
    public List<com.github.KostyaTr.hospital.model.display.Patient> getPatientsByDoctorId(Long doctorId) {
        return patientDaoDisp.getPatientsByDoctorId(doctorId);
    }

    @Override
    public List<com.github.KostyaTr.hospital.model.display.GuestPatient> getGuestPatientsByDoctorId(Long doctorId) {
        return guestPatientDaoDisp.getPatientsByDoctorId(doctorId);
    }

    @Override
    public boolean takeThePatient(Long patientId, String condition) {
        if (condition.equals("Bad")){
            if(putPatientInHospital(patientId, condition) != null){
                return patientDao.removePatientById(patientId);
            }
        } else {
            return patientDao.removePatientById(patientId);
        }
        return false;
    }


    @Override
    public boolean updateDiagnose(Long patientId, String diagnose) {
        return inpatientDao.updateDiagnose(patientId, diagnose);
    }

    @Override
    public boolean prescribeTreatmentCourse(Long patientId, Long treatmentCourseId) {
        if (inpatientDao.getInpatientById(patientId).getDiagnose() == null){
            return false;
        }
        return inpatientDao.updateTreatmentCourse(patientId, treatmentCourseId);
    }

    @Override
    public boolean takeTheGuestPatient(Long guestPatientId) {
        return guestPatientDao.removePatientById(guestPatientId);
    }

    @Override
    public Card getCardByPatientId(Long patientId) {
        return cardDao.getCardByUserId(patientDao.getPatientById(patientId).getUserId());
    }

    @Override
    public List<Medicine> getMedicine() {
        return medicineDao.getMedicine();
    }

    @Override
    public Long createTreatmentCourse(TreatmentCourse treatmentCourse) {
        return treatmentCourseDao.addTreatmentCourse(treatmentCourse);
    }

    @Override
    public boolean updateTreatmentCourse(TreatmentCourse treatmentCourse) {
        return treatmentCourseDao.updateTreatmentCourseById(treatmentCourse);
    }

    @Override
    public boolean updateStatus(Long patientId, String status) {
        return inpatientDao.updateStatus(patientId, status);
    }

    @Override
    public boolean dischargeInpatient(Long patientId) {
        Inpatient inpatient = inpatientDao.getInpatientById(patientId);
        if(inpatient.getStatus().equals(PATIENT_CURED)){
            chamberDao.updateChamberLoad(inpatient.getDeptChamberId(), -LOAD);
            treatmentCourseDao.removeTreatmentCourseById(inpatient.getTreatmentCourseId());
            cardDao.updateCardHistory( inpatient.getUserId(), inpatient.getDiagnose());
            dischargedPatientDao.addDischargedPatient(new DischargedPatient(
                    null,
                    inpatient.getUserId(),
                    inpatient.getDoctorId(),
                    inpatient.getDeptChamberId(),
                    inpatient.getDiagnose(),
                    inpatient.getTreatmentCourseId(),
                    inpatient.getOperationServiceId(),
                    PATIENT_CURED,
                    inpatient.getEnrollmentDate(),
                    new Date()
            ));
           return inpatientDao.removeInpatientById(patientId);
        }
        return false;
    }

    @Override
    public List<com.github.KostyaTr.hospital.model.display.Inpatient> getInpatientsByDoctorId(Long doctorId) {
        return inpatientDaoDisp.getInpatientsByDoctorId(doctorId);
    }

    @Override
    public List<Inpatient> getUndiagnosedInpatientsByDoctorId(Long doctorId) {
        return inpatientDao.getUndiagnosedInpatientsByDoctor(doctorId);
    }

    private Long putPatientInHospital(Long patientId, String condition) {
        Patient patient = patientDao.getPatientById(patientId);
        final List<Long> chambers = freeChambers(patient);
        if (!chambers.isEmpty()){
            chamberDao.updateChamberLoad(chambers.get(FREE_CHAMBER), LOAD);
            Long id = chambers.get(FREE_CHAMBER);
            Inpatient inpatient = new Inpatient(
                    null, patient.getUserId(), patient.getDoctorId(), id,
                    null, null, null, condition, new Date());
            return inpatientDao.addInpatient(inpatient);
        }
        else {
            return null;
        }
    }

    private List<Long> freeChambers(Patient patient){
        return chamberDao.getEmptyChambersByDeptId(
                medDoctorDao.getDoctorById(patient.getDoctorId()).getDeptNum());
    }

    @Override
    public List<com.github.KostyaTr.hospital.model.display.TreatmentCourse> getTreatmentCourses() {
        return treatmentCourseDaoDisp.getTreatmentCourses();
    }
}
