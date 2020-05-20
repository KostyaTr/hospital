package com.github.KostyaTr.hospital.dao.entity;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
@Getter @Setter
@AllArgsConstructor @NoArgsConstructor
@Table(name = "speciality")
public class SpecialityEntity {
    @Id
    @GeneratedValue(generator = "gen")
    @GenericGenerator(name = "gen", strategy = "increment")
    @Column(name = "id") @Access(AccessType.PROPERTY)
    private Long specialityId;
    @Column(name = "speciality_name") @Access(AccessType.PROPERTY)
    private String speciality;
    @ManyToMany(fetch = FetchType.LAZY,mappedBy = "specialities", cascade = CascadeType.ALL)
    private List<MedDoctorEntity> doctors = new ArrayList<>();

    @OneToOne(fetch = FetchType.LAZY, mappedBy = "speciality")
    private MedicalServiceEntity medicalService;
}
