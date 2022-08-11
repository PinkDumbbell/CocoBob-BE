package com.pinkdumbell.cocobob.domain.daily;

import com.pinkdumbell.cocobob.common.BaseEntity;
import com.pinkdumbell.cocobob.domain.daily.dto.DailyRecordRegisterRequestDto;
import com.pinkdumbell.cocobob.domain.daily.dto.DailyRecordUpdateRequestDto;
import com.pinkdumbell.cocobob.domain.pet.Pet;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.time.LocalDate;

@Getter
@NoArgsConstructor
@AllArgsConstructor
@Builder
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

    public Daily(DailyRecordRegisterRequestDto dailyRecordRegisterRequestDto, Pet pet) {
        this.date = dailyRecordRegisterRequestDto.getDate();
        this.feedAmount = dailyRecordRegisterRequestDto.getFeedAmount();
        this.walkTotalTime = dailyRecordRegisterRequestDto.getWalkTotalTime();
        this.walkDistance = dailyRecordRegisterRequestDto.getWalkDistance();
        this.walkGps = dailyRecordRegisterRequestDto.getWalkGps();
        this.note = dailyRecordRegisterRequestDto.getNote();
        this.pet = pet;
    }

    public void updateDaily(DailyRecordUpdateRequestDto dailyRecordUpdateRequestDto) {

        this.feedAmount = dailyRecordUpdateRequestDto.getFeedAmount();
        this.note = dailyRecordUpdateRequestDto.getNote();
        this.walkTotalTime = dailyRecordUpdateRequestDto.getWalkTotalTime();
        this.walkDistance = dailyRecordUpdateRequestDto.getWalkDistance();
        this.walkGps = dailyRecordUpdateRequestDto.getWalkGps();
    }
}
