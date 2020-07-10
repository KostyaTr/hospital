package com.github.KostyaTr.hospital.dao.config;

import com.github.KostyaTr.hospital.dao.*;
import com.github.KostyaTr.hospital.dao.impl.*;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;
import org.springframework.transaction.annotation.EnableTransactionManagement;

@Configuration
@Import(DataSourceConfig.class)
@EnableTransactionManagement
public class DaoConfig {
    @Bean
    public AuthUserDao authUserDao() {
        return new DefaultAuthUserDao();
    }

    @Bean
    public UserDao userDao() {
        return new DefaultUserDao();
    }

    @Bean
    public CardDao cardDao() {
        return new DefaultCardDao();
    }

    @Bean
    public ChamberDao chamberDao(){
        return new DefaultChamberDao();
    }

    @Bean
    public DischargedPatientDao dischargedPatientDao(){
        return new DefaultDischargedPatientDao();
    }

    @Bean
    public GuestPatientDao guestPatientDao(){
        return new DefaultGuestPatientDao();
    }

    @Bean
    public InpatientDao inpatientDao(){
        return new DefaultInpatientDao();
    }

    @Bean
    public MedDoctorDao medDoctorDao(){
        return new DefaultMedDoctorDao();
    }

    @Bean
    public MedicalServiceDao medicalServiceDao(){
        return new DefaultMedicalServiceDao();
    }

    @Bean
    public MedicineDao medicineDao(){
        return new DefaultMedicineDao();
    }

    @Bean
    public PatientDao patientDao() {
        return new DefaultPatientDao();
    }

    @Bean
    public ReceiptDao receiptDao(){
        return new DefaultReceiptDao();
    }

    @Bean
    public TreatmentCourseDao treatmentCourseDao(){
        return new DefaultTreatmentCourseDao();
    }
}
