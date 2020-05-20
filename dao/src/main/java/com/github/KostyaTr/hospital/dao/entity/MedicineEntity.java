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
@Table(name = "medicine")
public class MedicineEntity {
    @Id
    @GeneratedValue(generator = "gen")
    @GenericGenerator(name = "gen", strategy = "increment")
    @Column(name = "id") @Access(AccessType.PROPERTY)
    private Long medicineId;
    @Column(name = "medicine_name") @Access(AccessType.PROPERTY)
    private String medicineName;
    @Column(name = "normal_dose") @Access(AccessType.PROPERTY)
    private Double normalDose;
    @Column(name = "critical_dose") @Access(AccessType.PROPERTY)
    private Double criticalDose;
    @Column(name = "package_amount") @Access(AccessType.PROPERTY)
    private Integer packageAmount;
    @Column(name = "price") @Access(AccessType.PROPERTY)
    private Double price;

    @OneToMany(fetch = FetchType.LAZY, cascade = {CascadeType.ALL}, mappedBy = "medicine")
    private List<TreatmentCourseEntity> treatmentCourses;
}
