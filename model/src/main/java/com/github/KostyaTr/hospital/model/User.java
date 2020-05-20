package com.github.KostyaTr.hospital.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import org.hibernate.annotations.GenericGenerator;

import lombok.NoArgsConstructor;

import javax.persistence.*;

@Entity
@Data @AllArgsConstructor @NoArgsConstructor
@Table(name = "user")
public class User {
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
}
