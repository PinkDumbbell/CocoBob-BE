package com.pinkdumbell.cocobob.domain.record.walk;

import com.pinkdumbell.cocobob.domain.pet.Pet;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.time.LocalDate;
import java.time.LocalTime;

@Builder
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Entity
public class Walk {
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "walk_id")
    private Long id;
    private LocalTime startedAt;
    private LocalTime finishedAt;
    private String photoPath;
    private Double distance;
    private Integer totalTime;
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "pet_id")
    private Pet pet;
    private LocalDate date;
    private Double spentKcal;

    public void setPhotoPath(String photoPath) {
        this.photoPath = photoPath;
    }
}
