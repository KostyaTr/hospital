package com.github.KostyaTr.hospital.service.config;

import com.github.KostyaTr.hospital.dao.config.DaoConfig;
import com.github.KostyaTr.hospital.service.*;
import com.github.KostyaTr.hospital.service.impl.*;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

@Configuration
public class ServiceConfig {

    private final DaoConfig daoConfig;

    public ServiceConfig(DaoConfig daoConfig) {
        this.daoConfig = daoConfig;
    }

    @Bean
    public AuthorizationService authorizationService(){
        return new DefaultAuthorizationService(daoConfig.authUserDao(), passwordEncoder());
    }

    @Bean
    public MedDoctorService medDoctorService(){
        return new DefaultMedDoctorService(priceCalculationService(),
                daoConfig.patientDao(), daoConfig.guestPatientDao(), daoConfig.inpatientDao(),
                daoConfig.chamberDao(), daoConfig.cardDao(), daoConfig.medicineDao(),
                daoConfig.userDao(), daoConfig.treatmentCourseDao(), daoConfig.dischargedPatientDao(),
                daoConfig.receiptDao(), daoConfig.medDoctorDao());
    }

    @Bean
    public PriceCalculationService priceCalculationService(){
        return new DefaultPriceCalculationService(daoConfig.chamberDao(), daoConfig.cardDao());
    }

    @Bean
    public QueueService queueService(){
        return new DefaultQueueService(daoConfig.patientDao(), daoConfig.guestPatientDao());
    }

    @Bean
    public RegistrationService registrationService(){
        return new DefaultRegistrationService(daoConfig.userDao(), daoConfig.authUserDao(), daoConfig.cardDao(), passwordEncoder());
    }

    @Bean
    public UserService userService(){
        return new DefaultUserService(daoConfig.patientDao(), daoConfig.medicalServiceDao(),
                daoConfig.medicineDao(), daoConfig.guestPatientDao(),
                daoConfig.medDoctorDao(), daoConfig.userDao(), daoConfig.receiptDao());
    }

    @Bean
    public PasswordEncoder passwordEncoder(){
        return new BCryptPasswordEncoder();
    }
}
