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
    private UserDao userDao = DefaultUserDao.getInstance();
    private TreatmentCourseDao treatmentCourseDao = DefaultTreatmentCourseDao.getInstance();
    private DischargedPatientDao dischargedPatientDao = DefaultDischargedPatientDao.getInstance();
    private com.github.KostyaTr.hospital.dao.display.TreatmentCourseDao treatmentCourseDaoDisp = com.github.KostyaTr.hospital.dao.impl.display.DefaultTreatmentCourseDao.getInstance();
    private com.github.KostyaTr.hospital.dao.display.InpatientDao inpatientDaoDisp = com.github.KostyaTr.hospital.dao.impl.display.DefaultInpatientDao.getInstance();
    private com.github.KostyaTr.hospital.dao.display.PatientDao patientDaoDisp = com.github.KostyaTr.hospital.dao.impl.display.DefaultPatientDao.getInstance();
    private com.github.KostyaTr.hospital.dao.display.GuestPatientDao guestPatientDaoDisp = com.github.KostyaTr.hospital.dao.impl.display.DefaultGuestPatientDao.getInstance();

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
    public boolean takeThePatient(Long patientId, Status status) {
        if (status.equals(Status.BAD)){
            if(putPatientInHospital(patientId, status) != null){
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
    public List<Medicine> getMedicine() {
        return medicineDao.getMedicine();
    }

    @Override
    public Long createTreatmentCourse(TreatmentCourse treatmentCourse) {
        return treatmentCourseDao.addTreatmentCourse(treatmentCourse);
    }

    @Override
    public boolean updateStatus(Long patientId, Status status) {
        return inpatientDao.updateStatus(patientId, status);
    }

    @Override
    public boolean dischargeInpatient(com.github.KostyaTr.hospital.model.display.Inpatient inpatientDisp) {
        if(!inpatientDisp.getStatus().equals(Status.BAD)){
            Inpatient inpatient = inpatientDao.getInpatientById(inpatientDisp.getInpatientId());
            chamberDao.updateChamberLoad(inpatient.getDeptChamberId(), -LOAD);
            String diagnose;
            if (inpatient.getDiagnose() == null){
                cardDao.updateCardHistory(inpatient.getUserId(), "No diagnose" + inpatient.getStatus());
                diagnose = "No diagnose";
            } else {
                diagnose = inpatient.getDiagnose();
                cardDao.updateCardHistory(inpatient.getUserId(), inpatient.getDiagnose() +" "+ inpatient.getStatus());
            }
            inpatientDao.removeInpatientById(inpatient.getInpatientId());
            String treatmentCourse;
            if (inpatientDisp.getMedicineName() == null){
                treatmentCourse = "No treatment Course";
            } else {
                treatmentCourseDao.removeTreatmentCourseById(inpatient.getTreatmentCourseId());
                treatmentCourse = inpatientDisp.getMedicineName() +" "+ inpatientDisp.getMedicineDose();
            }
            final DischargedPatient dischargedPatient = new DischargedPatient(
                    null,
                    inpatientDisp.getPatientName(),
                    getDoctorName(inpatient.getDoctorId()),
                    diagnose,
                    cardDao.getCardByUserId(inpatient.getUserId()).getHistory(),
                    treatmentCourse,
                    inpatient.getStatus(),
                    inpatient.getEnrollmentDate(),
                    new Date());
            if (inpatient.getStatus().equals(Status.DEAD)){
                userDao.removeUser(inpatient.getUserId()); //cascade delete
            }
            return dischargedPatientDao.addDischargedPatient(dischargedPatient) != null;
        }
        return false;
    }

    private String getDoctorName(Long doctorId){
        return userDao.getUserById(medDoctorDao.getDoctorById(doctorId).getUserId()).getFirstName() +
                " " + userDao.getUserById(medDoctorDao.getDoctorById(doctorId).getUserId()).getLastName();
    }

    @Override
    public List<com.github.KostyaTr.hospital.model.display.Inpatient> getInpatientsByDoctorId(Long doctorId) {
        return inpatientDaoDisp.getInpatientsByDoctorId(doctorId);
    }

    private Long putPatientInHospital(Long patientId, Status status) {
        Patient patient = patientDao.getPatientById(patientId);
        final List<Long> chambers = freeChambers(patient);
        if (!chambers.isEmpty()){
            chamberDao.updateChamberLoad(chambers.get(FREE_CHAMBER), LOAD);
            Long id = chambers.get(FREE_CHAMBER);
            Inpatient inpatient = new Inpatient(
                    null, patient.getUserId(), patient.getDoctorId(), id,
                    null, null, null, status, new Date());
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

    @Override
    public Card getCardInfo(Long inpatientId) {
        Inpatient inpatient = inpatientDao.getInpatientById(inpatientId);
        return cardDao.getCardByUserId(inpatient.getUserId());
    }
}
