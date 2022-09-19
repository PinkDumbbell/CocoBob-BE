package com.pinkdumbell.cocobob.domain.healthrecord;

import com.pinkdumbell.cocobob.domain.pet.Pet;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.time.LocalDate;

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
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "pet_id")
    private Pet pet;

    public void update(String note) {
        this.note = note;
    }
}
