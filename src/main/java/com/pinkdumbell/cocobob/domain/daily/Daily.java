package com.pinkdumbell.cocobob.domain.daily;

import com.pinkdumbell.cocobob.common.BaseEntity;
import com.pinkdumbell.cocobob.domain.pet.Pet;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.time.LocalDate;

@Getter
@NoArgsConstructor
@Entity
public class Daily extends BaseEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "daily_id")
    private Long id;

    private LocalDate date;

    private Integer feedAmount;

    private Integer walkTotalTime;

    private Float walkDistance;

    @Column(columnDefinition = "TEXT")
    private String walkGps;

    @Column(columnDefinition = "TEXT")
    private String note;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "pet_id")
    private Pet pet;
}
