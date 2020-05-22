package com.github.KostyaTr.hospital.service.impl;

import com.github.KostyaTr.hospital.dao.*;
import com.github.KostyaTr.hospital.dao.impl.*;
import com.github.KostyaTr.hospital.model.*;
import com.github.KostyaTr.hospital.service.MedDoctorService;
import com.github.KostyaTr.hospital.service.PriceCalculationService;

import java.util.Date;
import java.util.List;

public class DefaultMedDoctorService implements MedDoctorService {
    private PriceCalculationService priceCalculationService = DefaultPriceCalculationService.getInstance();

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
    private ReceiptDao receiptDao = DefaultReceiptDao.getInstance();

    private final int FREE_CHAMBER = 0;
    private final int LOAD = 1;


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
    public boolean takeThePatient(Long patientId, Status status) {
        if (status.equals(Status.BAD)){
            if(putPatientInHospital(patientId) != null){
                return patientDao.removePatientById(patientId);
            }
        } else {
            return patientDao.removePatientById(patientId);
        }
        return false;
    }


    @Override
    public void updateDiagnose(Long inpatientId, String diagnose) {
        final Inpatient inpatient = inpatientDao.getInpatientById(inpatientId);
        inpatient.setDiagnose(diagnose);
        inpatientDao.updateInpatient(inpatient);
    }

    @Override
    public boolean prescribeTreatmentCourse(Long inpatientId, Long treatmentCourseId) {
        if (inpatientDao.getInpatientById(inpatientId).getDiagnose() == null){
            return false;
        }
        final Inpatient inpatient = inpatientDao.getInpatientById(inpatientId);
        inpatient.setTreatmentCourse(treatmentCourseDao.getTreatmentCourseById(treatmentCourseId));
        inpatientDao.updateInpatient(inpatient);
        return true;
    }

    @Override
    public void takeTheGuestPatient(Long guestPatientId) {
        guestPatientDao.removePatientById(guestPatientId);
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
    public void updateStatus(Long inpatientId, Status status) {
        final Inpatient inpatient = inpatientDao.getInpatientById(inpatientId);
        inpatient.setStatus(status);
        inpatientDao.updateInpatient(inpatient);
    }

    @Override
    public boolean dischargeInpatient(Inpatient inpatient) {
        if(!inpatient.getStatus().equals(Status.BAD)){
            final Receipt receipt = priceCalculationService.calculateReceipt(inpatient);
            if (receipt != null){
                receiptDao.insertOrUpdateReceipt(receipt);
            }
            chamberDao.updateChamberLoad(inpatient.getChamber().getChamberId(), -LOAD);
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
            if (inpatient.getTreatmentCourse() == null){
                treatmentCourse = "No treatment Course";
            } else {
                treatmentCourseDao.removeTreatmentCourseById(inpatient.getTreatmentCourse().getTreatmentCourseId());
                treatmentCourse = inpatient.getTreatmentCourse().getMedicineName() +" "+ inpatient.getTreatmentCourse().getMedicineDose();
            }
            final DischargedPatient dischargedPatient = new DischargedPatient(
                    null,
                    inpatient.getFirstName() + " " + inpatient.getLastName(),
                    inpatient.getDoctor().getFirstName() + " " + inpatient.getDoctor().getLastName(),
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
    public List<Inpatient> getInpatientsByDoctorId(Long doctorId) {
        return inpatientDao.getPatientsByDoctorId(doctorId);
    }

    private Long putPatientInHospital(Long patientId) {
        Patient patient = patientDao.getPatientById(patientId);
        final List<Chamber> chambers = freeChambers(patient);
        if (!chambers.isEmpty()){
            Chamber chamber = new Chamber(
                    chambers.get(FREE_CHAMBER).getChamberId(),
                    chambers.get(FREE_CHAMBER).getChamberNum(),
                    chambers.get(FREE_CHAMBER).getDepartment(),
                    chambers.get(FREE_CHAMBER).getChamberLoad(),
                    chambers.get(FREE_CHAMBER).getChamberCapacity(),
                    chambers.get(FREE_CHAMBER).isVip(),
                    chambers.get(FREE_CHAMBER).getPriceADay()
            );
            chamberDao.updateChamberLoad(chamber.getChamberId(), LOAD);
            Inpatient inpatient = new Inpatient(
                    patient.getUserId(),
                    patient.getFirstName(),
                    patient.getLastName(),
                    patient.getPhoneNumber(),
                    patient.getEmail(),
                    null,
                    patient.getDoctor(),
                    chamber,
                    "",
                    null,
                    Status.BAD,
                    new Date()
            );
            return inpatientDao.addInpatient(inpatient);
        }
        else {
            return null;
        }
    }

    private List<Chamber> freeChambers(Patient patient){
        return chamberDao.getEmptyChambersByDeptId(patient.getDoctor().getDepartment().getDepartmentId());
    }

    @Override
    public List<TreatmentCourse> getTreatmentCourses() {
        return treatmentCourseDao.getTreatmentCourses();
    }

    @Override
    public Card getCardInfo(Long inpatientId) {
        Inpatient inpatient = inpatientDao.getInpatientById(inpatientId);
        return cardDao.getCardByUserId(inpatient.getUserId());
    }
}
