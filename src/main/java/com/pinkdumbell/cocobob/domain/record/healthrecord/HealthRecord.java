package com.pinkdumbell.cocobob.domain.record.healthrecord;

import com.pinkdumbell.cocobob.domain.pet.Pet;
import com.pinkdumbell.cocobob.domain.record.healthrecord.abnormal.HealthRecordAbnormal;
import com.pinkdumbell.cocobob.domain.record.healthrecord.dto.HealthRecordUpdateRequestDto;
import com.pinkdumbell.cocobob.domain.record.healthrecord.meal.Meal;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.time.LocalDate;
import java.util.List;

@Builder
@AllArgsConstructor
@Getter
@NoArgsConstructor
@Entity
public class HealthRecord {
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "health_record_id")
    private Long id;
    private LocalDate date;
    private String note;
    private Double bodyWeight;
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "pet_id")
    private Pet pet;
    @OneToMany(mappedBy = "healthRecord")
    private List<Meal> meals;
    @OneToMany(mappedBy = "healthRecord")
    private List<HealthRecordAbnormal> healthRecordAbnormals;

    public void update(HealthRecordUpdateRequestDto requestDto) {
        this.note = requestDto.getNote();
        this.bodyWeight = requestDto.getBodyWeight();
    }
}
