package com.github.KostyaTr.hospital.service.impl;

import com.github.KostyaTr.hospital.dao.*;
import com.github.KostyaTr.hospital.model.*;
import com.github.KostyaTr.hospital.service.MedDoctorService;
import com.github.KostyaTr.hospital.service.PriceCalculationService;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

public class DefaultMedDoctorService implements MedDoctorService {

    private final PriceCalculationService priceCalculationService;
    private final PatientDao patientDao;
    private final GuestPatientDao guestPatientDao;
    private final InpatientDao inpatientDao;
    private final ChamberDao chamberDao;
    private final CardDao cardDao;
    private final MedicineDao medicineDao;
    private final UserDao userDao;
    private final TreatmentCourseDao treatmentCourseDao;
    private final DischargedPatientDao dischargedPatientDao;
    private final ReceiptDao receiptDao;
    private final MedDoctorDao medDoctorDao;

    private final int FREE_CHAMBER = 0;
    private final int LOAD = 1;

    public DefaultMedDoctorService(PriceCalculationService priceCalculationService, PatientDao patientDao,
                                   GuestPatientDao guestPatientDao, InpatientDao inpatientDao,
                                   ChamberDao chamberDao, CardDao cardDao, MedicineDao medicineDao,
                                   UserDao userDao, TreatmentCourseDao treatmentCourseDao,
                                   DischargedPatientDao dischargedPatientDao, ReceiptDao receiptDao,
                                   MedDoctorDao medDoctorDao) {

        this.priceCalculationService = priceCalculationService;
        this.patientDao = patientDao;
        this.guestPatientDao = guestPatientDao;
        this.inpatientDao = inpatientDao;
        this.chamberDao = chamberDao;
        this.cardDao = cardDao;
        this.medicineDao = medicineDao;
        this.userDao = userDao;
        this.treatmentCourseDao = treatmentCourseDao;
        this.dischargedPatientDao = dischargedPatientDao;
        this.receiptDao = receiptDao;
        this.medDoctorDao = medDoctorDao;
    }

    @Override
    @Transactional
    public List<Patient> getPatientsByDoctorId(Long doctorId) {
        return patientDao.getPatientsByDoctorId(doctorId);
    }

    @Override
    @Transactional
    public List<GuestPatient> getGuestPatientsByDoctorId(Long doctorId) {
        return guestPatientDao.getPatientsByDoctorId(doctorId);
    }

    @Override
    @Transactional
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
    @Transactional
    public void updateDiagnose(Long inpatientId, String diagnose) {
        final Inpatient inpatient = inpatientDao.getInpatientById(inpatientId);
        inpatient.setDiagnose(diagnose);
        inpatientDao.updateInpatient(inpatient);
    }

    @Override
    @Transactional
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
    @Transactional
    public void takeTheGuestPatient(Long guestPatientId) {
        guestPatientDao.removePatientById(guestPatientId);
    }

    @Override
    @Transactional
    public List<Medicine> getMedicine() {
        return medicineDao.getMedicine();
    }

    @Override
    @Transactional
    public Long createTreatmentCourse(TreatmentCourse treatmentCourse) {
        return treatmentCourseDao.addTreatmentCourse(treatmentCourse);
    }

    @Override
    @Transactional
    public void updateStatus(Long inpatientId, Status status) {
        final Inpatient inpatient = inpatientDao.getInpatientById(inpatientId);
        inpatient.setStatus(status);
        inpatientDao.updateInpatient(inpatient);
    }

    @Override
    @Transactional
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
                cardDao.updateCardHistory(inpatient.getUserId(),  diagnose +" "+ inpatient.getStatus() + " at " + LocalDateTime.now());
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
                    LocalDate.now());
            if (inpatient.getStatus().equals(Status.DEAD)){
                userDao.removeUser(inpatient.getUserId()); //cascade delete
            }
            return dischargedPatientDao.addDischargedPatient(dischargedPatient) != null;
        }
        return false;
    }

    @Override
    @Transactional
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
            patientDao.removePatientByUserId(patient.getUserId()); //removing appointments
            Inpatient inpatient = new Inpatient(
                    patient.getUserId(),
                    patient.getFirstName(),
                    patient.getLastName(),
                    patient.getPhoneNumber(),
                    patient.getEmail(),
                    null,
                    patient.getDoctor(),
                    chamber,
                    null,
                    null,
                    Status.BAD,
                    LocalDate.now()
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
    @Transactional
    public List<TreatmentCourse> getTreatmentCourses() {
        return treatmentCourseDao.getTreatmentCourses();
    }

    @Override
    @Transactional
    public Card getCardInfo(Long inpatientId) {
        Inpatient inpatient = inpatientDao.getInpatientById(inpatientId);
        return cardDao.getCardByUserId(inpatient.getUserId());
    }

    @Override
    @Transactional
    public double getDischargedPatientsCount() {
        return dischargedPatientDao.getDischargedPatientsCount();
    }

    @Override
    @Transactional
    public List<DischargedPatient> getDischargedPatients(int page) {
        return dischargedPatientDao.getDischargedPatients(page);
    }

    @Override
    @Transactional
    public MedDoctor getDoctorByUserId(Long userId) {
        return medDoctorDao.getDoctorByUserId(userId);
    }

    @Override
    @Transactional
    public Medicine getMedicineByName(String medicineName) {
        return medicineDao.getMedicineByName(medicineName);
    }

    @Override
    @Transactional
    public Inpatient getInpatientById(Long inpatientId) {
        return inpatientDao.getInpatientById(inpatientId);
    }

    @Override
    @Transactional
    public Patient getPatientById(Long patientId) {
        return patientDao.getPatientById(patientId);
    }
}
