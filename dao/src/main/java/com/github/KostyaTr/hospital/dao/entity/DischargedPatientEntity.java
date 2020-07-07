package com.github.KostyaTr.hospital.dao.entity;

import org.hibernate.annotations.GenericGenerator;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import javax.persistence.*;
import java.time.LocalDate;
import com.github.KostyaTr.hospital.model.Status;

@Entity
@Getter @Setter
@NoArgsConstructor @AllArgsConstructor
@Table(name = "discharged_patient")
public class DischargedPatientEntity {
    @Id
    @GeneratedValue(generator = "gen")
    @GenericGenerator(name = "gen", strategy = "increment")
    @Column(name = "id") @Access(AccessType.PROPERTY)
    private Long dischargedPatientId;
    @Column(name = "patient_name") @Access(AccessType.PROPERTY)
    private String patientName;
    @Column(name = "doctor_name") @Access(AccessType.PROPERTY)
    private String doctorName;
    @Column(name = "diagnose") @Access(AccessType.PROPERTY)
    private String diagnose;
    @Column(name = "card_history", columnDefinition = "text") @Access(AccessType.PROPERTY)
    private String cardHistory;
    @Column(name = "treatment_course") @Access(AccessType.PROPERTY)
    private String treatmentCourse;
    @Enumerated(EnumType.STRING)
    @Column(name = "patient_status") @Access(AccessType.PROPERTY)
    private Status status;
    @Column(name = "enrollment_date") @Access(AccessType.PROPERTY)
    private LocalDate enrollmentDate;
    @Column(name = "discharge_date") @Access(AccessType.PROPERTY)
    private LocalDate dischargeDate;
}
