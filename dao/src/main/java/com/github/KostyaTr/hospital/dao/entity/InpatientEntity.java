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
@Getter
@Setter
@NoArgsConstructor @AllArgsConstructor
@Table(name = "inpatient")

public class InpatientEntity {
    @Id
    @GeneratedValue(generator = "gen")
    @GenericGenerator(name = "gen", strategy = "increment")
    @Column(name = "id") @Access(AccessType.PROPERTY)
    private Long inpatientId;

    @OneToOne (fetch = FetchType.EAGER)
    @JoinColumn(name = "user_id") @Access(AccessType.PROPERTY)
    private UserEntity user;

    @ManyToOne (fetch = FetchType.LAZY)
    @JoinColumn(name = "doctor_id") @Access(AccessType.PROPERTY)
    private MedDoctorEntity doctor;

    @ManyToOne (fetch = FetchType.LAZY)
    @JoinColumn(name = "dept_chamber_id") @Access(AccessType.PROPERTY)
    private ChamberEntity chamber;

    @Column(name = "diagnose") @Access(AccessType.PROPERTY)
    private String diagnose;

    @ManyToOne (fetch = FetchType.LAZY)
    @JoinColumn(name = "treatment_course_id") @Access(AccessType.PROPERTY)
    private TreatmentCourseEntity treatmentCourse;

    @Enumerated(EnumType.STRING)
    @Column(name = "patient_status") @Access(AccessType.PROPERTY)
    private Status status;
    @Column(name = "enrollment_date") @Access(AccessType.PROPERTY)
    private LocalDate enrollmentDate;
}
