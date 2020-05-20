package com.github.KostyaTr.hospital.dao.entity;

import org.hibernate.annotations.GenericGenerator;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import javax.persistence.*;
import java.util.Date;

@Entity
@Getter
@Setter
@NoArgsConstructor @AllArgsConstructor
@Table(name = "guest_patient")

public class GuestPatientEntity {
    @Id
    @GeneratedValue(generator = "gen")
    @GenericGenerator(name = "gen", strategy = "increment")
    @Column(name = "id") @Access(AccessType.PROPERTY)
    private Long patientId;
    @Column(name = "first_name") @Access(AccessType.PROPERTY)
    private String firstName;
    @Column(name = "last_name") @Access(AccessType.PROPERTY)
    private String lastName;
    @Column(name = "phone_number") @Access(AccessType.PROPERTY)
    private String phoneNumber;
    @Column(name = "email") @Access(AccessType.PROPERTY)
    private String email;

    @ManyToOne (fetch = FetchType.LAZY)
    @JoinColumn(name = "doctor_id") @Access(AccessType.PROPERTY)
    private MedDoctorEntity doctor;

    @Column(name = "coupon_num") @Access(AccessType.PROPERTY)
    private int couponNum;

    @ManyToOne
    @JoinColumn(name = "medical_service_id") @Access(AccessType.PROPERTY)
    private MedicalServiceEntity medicalService;

    @Column(name = "visit_date") @Access(AccessType.PROPERTY)
    private Date visitDate;
}
