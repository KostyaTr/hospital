package com.github.KostyaTr.hospital.dao.entity;

import org.hibernate.annotations.GenericGenerator;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import javax.persistence.*;
import java.util.List;

@Entity
@Getter @Setter
@AllArgsConstructor @NoArgsConstructor
@Table(name = "user")
public class UserEntity {
    @Id
    @GeneratedValue(generator = "gen")
    @GenericGenerator(name = "gen", strategy = "increment")
    @Column(name = "id") @Access(AccessType.PROPERTY)
    private Long userId;
    @Column(name = "first_name") @Access(AccessType.PROPERTY)
    private String firstName;
    @Column(name = "last_name") @Access(AccessType.PROPERTY)
    private String lastName;
    @Column(name = "phone_number") @Access(AccessType.PROPERTY)
    private String phoneNumber;
    @Column(name = "email") @Access(AccessType.PROPERTY)
    private String email;

    @OneToOne(mappedBy = "user", fetch = FetchType.LAZY, cascade = {CascadeType.ALL})
    private MedDoctorEntity doctor;

    @OneToMany (mappedBy = "user", fetch = FetchType.LAZY, cascade = {CascadeType.ALL})
    private List<PatientEntity> patient;

    @OneToOne (mappedBy = "user", fetch = FetchType.LAZY, cascade = {CascadeType.ALL})
    private InpatientEntity inpatient;
}
