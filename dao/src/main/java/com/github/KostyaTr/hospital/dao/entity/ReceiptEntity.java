package com.github.KostyaTr.hospital.dao.entity;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import javax.persistence.*;
import java.io.Serializable;

@Entity
@Getter @Setter
@NoArgsConstructor @AllArgsConstructor
@Table(name = "receipt")
public class ReceiptEntity implements Serializable {
    @Id
    @Column(name = "user_id") @Access(AccessType.PROPERTY)
    private Long userId;
    @Column(name = "price_for_chamber") @Access(AccessType.PROPERTY)
    private double priceForChamber;
    @Column(name = "price_for_medicine") @Access(AccessType.PROPERTY)
    private double priceForMedicine;
}
