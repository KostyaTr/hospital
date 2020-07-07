package com.github.KostyaTr.hospital.dao.entity;

import org.hibernate.annotations.GenericGenerator;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import javax.persistence.*;
import java.time.LocalDate;

@Entity
@Getter @Setter
@NoArgsConstructor @AllArgsConstructor
@Table (name = "card")
public class CardEntity {
    @Id
    @GeneratedValue(generator = "gen")
    @GenericGenerator(name = "gen", strategy = "increment")
    @Column(name = "id") @Access(AccessType.PROPERTY)
    private Long cardId;
    @Column(name = "user_id") @Access(AccessType.PROPERTY)
    private Long userId;
    @Column(name = "history", columnDefinition = "text") @Access(AccessType.PROPERTY)
    private String history;
    @Column(name = "address") @Access(AccessType.PROPERTY)
    private String address;
    @Column(name = "date_of_birth") @Access(AccessType.PROPERTY)
    private LocalDate birthday;
    @Column(name = "insurance") @Access(AccessType.PROPERTY)
    private boolean insurance;
}
