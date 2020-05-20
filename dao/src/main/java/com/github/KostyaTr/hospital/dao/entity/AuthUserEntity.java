package com.github.KostyaTr.hospital.dao.entity;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.GenericGenerator;
import com.github.KostyaTr.hospital.model.Role;

import javax.persistence.*;

@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "auth_user")
public class AuthUserEntity {
    @Id
    @GeneratedValue(generator = "gen")
    @GenericGenerator(name = "gen", strategy = "increment")
    @Column(name = "id") @Access(AccessType.PROPERTY)
    private Long authUserId;
    @Column(name = "login") @Access(AccessType.PROPERTY)
    private String login;
    @Column(name = "password") @Access(AccessType.PROPERTY)
    private String password;
    @Enumerated(EnumType.STRING)
    @Column(name = "role") @Access(AccessType.PROPERTY)
    private Role role;

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id") @Access(AccessType.PROPERTY)
    private UserEntity user;
}
