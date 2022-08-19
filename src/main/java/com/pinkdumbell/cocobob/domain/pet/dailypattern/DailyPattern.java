package com.pinkdumbell.cocobob.domain.pet.dailypattern;

import com.pinkdumbell.cocobob.domain.pet.Pet;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Getter
@NoArgsConstructor
@Entity
public class DailyPattern {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "pattern_id")
    private Long id;

    private Integer walkFrequency;

    private Float walkDistance;

    private Integer walkTime;

    private Integer feedAmount;

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "pet_id")
    private Pet pet;
}
