package com.github.KostyaTr.hospital.dao.entity;


import org.hibernate.annotations.GenericGenerator;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import javax.persistence.*;
import java.util.List;

@Entity
@Getter
@Setter
@NoArgsConstructor @AllArgsConstructor
@Table(name = "medical_service")
public class MedicalServiceEntity {
    @Id
    @GeneratedValue(generator = "gen")
    @GenericGenerator(name = "gen", strategy = "increment")
    @Column(name = "id") @Access(AccessType.PROPERTY)
    private Long medicalServiceId;
    @Column(name = "service_name") @Access(AccessType.PROPERTY)
    private String serviceName;

    @ManyToOne (fetch = FetchType.LAZY)
    @JoinColumn(name = "needed_speciality_id") @Access(AccessType.PROPERTY)
    private SpecialityEntity speciality;

    @Column(name = "service_cost") @Access(AccessType.PROPERTY)
    private Double serviceCost;

    @OneToMany (fetch = FetchType.LAZY, mappedBy = "medicalService")
    private List<GuestPatientEntity> guestPatients;

    @OneToMany (fetch = FetchType.LAZY, mappedBy = "medicalService")
    private List<PatientEntity> patients;
}
