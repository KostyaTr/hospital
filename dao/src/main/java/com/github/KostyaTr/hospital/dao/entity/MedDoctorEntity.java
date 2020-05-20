package com.github.KostyaTr.hospital.dao.entity;


import org.hibernate.annotations.GenericGenerator;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
@Getter
@Setter
@NoArgsConstructor @AllArgsConstructor
@Table(name = "doctor")
public class MedDoctorEntity {
    @Id
    @GeneratedValue(generator = "gen")
    @GenericGenerator(name = "gen", strategy = "increment")
    @Column(name = "id") @Access(AccessType.PROPERTY)
    private Long doctorId;

    @OneToOne (fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id")
    @Access(AccessType.PROPERTY)
    private UserEntity user;

    @ManyToOne (fetch = FetchType.LAZY)
    @JoinColumn(name = "dept_id") @Access(AccessType.PROPERTY)
    private DepartmentEntity department;

    @Column(name = "head_of_dept") @Access(AccessType.PROPERTY)
    private boolean headOfDept;

    @ManyToMany (fetch = FetchType.LAZY)
    @JoinTable(name = "doctor_speciality", joinColumns = {@JoinColumn(name = "doctor_id")},
            inverseJoinColumns = {@JoinColumn (name = "speciality_id")})
    private List<SpecialityEntity> specialities = new ArrayList<>();

    @OneToMany (fetch = FetchType.LAZY, mappedBy = "doctor")
    private List<GuestPatientEntity> guestPatients;

    @OneToMany (fetch = FetchType.LAZY, mappedBy = "doctor")
    private List<PatientEntity> patients;

    @OneToMany (fetch = FetchType.LAZY, mappedBy = "doctor")
    private List<InpatientEntity> inpatients;
}
