package com.github.KostyaTr.hospital.dao.entity;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;
import java.util.List;

@Entity
@Setter @Getter
@AllArgsConstructor @NoArgsConstructor
@Table(name = "department")
public class DepartmentEntity {
    @Id
    @GeneratedValue(generator = "gen")
    @GenericGenerator(name = "gen", strategy = "increment")
    @Column(name = "id") @Access(AccessType.PROPERTY)
    private Long departmentId;
    @Column(name = "department_name") @Access(AccessType.PROPERTY)
    private String departmentName;
    @Column(name = "chamber_amount") @Access(AccessType.PROPERTY)
    private Integer chambersAmount;
    @Column(name = "vipChamber_amount") @Access(AccessType.PROPERTY)
    private Integer vipChambersAmount;

    @OneToMany (fetch = FetchType.LAZY, cascade = {CascadeType.ALL}, mappedBy = "department")
    private List<MedDoctorEntity> doctors;

    @OneToMany (fetch = FetchType.LAZY, cascade = {CascadeType.ALL}, mappedBy = "department")
    private List<ChamberEntity> chambers;
}
