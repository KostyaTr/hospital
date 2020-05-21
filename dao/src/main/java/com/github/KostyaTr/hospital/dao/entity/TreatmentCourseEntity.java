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
@Table(name = "treatment_course")
public class TreatmentCourseEntity {
    @Id
    @GeneratedValue(generator = "gen")
    @GenericGenerator(name = "gen", strategy = "increment")
    @Column(name = "id") @Access(AccessType.PROPERTY)
    private Long treatmentCourseId;

    @ManyToOne(fetch = FetchType.EAGER, cascade = {CascadeType.ALL})
    @JoinColumn(name = "medicine_id")
    private MedicineEntity medicine;

    @Column(name = "drug_dose") @Access(AccessType.PROPERTY)
    private Double medicineDose;
    @Column(name = "reception_description") @Access(AccessType.PROPERTY)
    private String receptionDesc;
    @Column(name = "times_a_day") @Access(AccessType.PROPERTY)
    private Integer timesPerDay;
    @Column(name = "duration_in_days") @Access(AccessType.PROPERTY)
    private Integer durationInDays;

    @OneToMany (fetch = FetchType.LAZY, mappedBy = "treatmentCourse")
    private List<InpatientEntity> inpatients;
}
