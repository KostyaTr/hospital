package com.github.KostyaTr.hospital.dao.entity;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;
import java.util.List;

@Entity
@Getter @Setter
@NoArgsConstructor @AllArgsConstructor
@Table(name = "chamber")
public class ChamberEntity {
    @Id
    @GeneratedValue(generator = "gen")
    @GenericGenerator(name = "gen", strategy = "increment")
    @Column(name = "id") @Access(AccessType.PROPERTY)
    private Long id;

    @Column (name = "chamber_id")
    private int chamberNum;

    @ManyToOne (fetch = FetchType.LAZY)
    @JoinColumn (name = "dept_id") @Access(AccessType.PROPERTY)
    private DepartmentEntity department;

    @Column(name = "vip") @Access(AccessType.PROPERTY)
    private boolean vip;
    @Column(name = "chamber_capacity") @Access(AccessType.PROPERTY)
    private int chamberCapacity;
    @Column(name = "chamber_load") @Access(AccessType.PROPERTY)
    private int chamberLoad;
    @Column(name = "price_a_day") @Access(AccessType.PROPERTY)
    private double priceADay;

    @OneToMany (fetch = FetchType.LAZY, mappedBy = "chamber")
    private List<InpatientEntity> inpatients;
}
