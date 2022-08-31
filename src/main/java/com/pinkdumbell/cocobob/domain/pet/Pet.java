package com.pinkdumbell.cocobob.domain.pet;

import com.pinkdumbell.cocobob.common.BaseEntity;
import com.pinkdumbell.cocobob.domain.pet.breed.Breed;
import com.pinkdumbell.cocobob.domain.pet.dto.PetCreateRequestDto;
import com.pinkdumbell.cocobob.domain.pet.dto.PetUpdateRequestDto;
import com.pinkdumbell.cocobob.domain.user.User;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.SQLDelete;
import org.hibernate.annotations.Where;

import javax.persistence.*;
import java.time.LocalDate;

@SQLDelete(sql = "update pet set deleted = true where pet_id = ?")
@Where(clause = "deleted=false")
@Getter
@NoArgsConstructor
@Builder
@AllArgsConstructor
@Entity
public class Pet extends BaseEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "pet_id")
    private Long id;

    private String name;

    private String thumbnailPath;
    @Enumerated(EnumType.STRING)
    private PetSex sex;

    private Boolean isSpayed;

    private Integer age;

    private LocalDate birthday;

    private Float bodyWeight;

    private Boolean isPregnant;

    @Column(nullable = true)
    private Integer fatLevel;

    private Integer activityLevel;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id")
    private User user;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "breed_id")
    private Breed breed;

    private boolean deleted = false;

    @Builder
    public Pet(String name,
               PetSex sex,
               Integer age,
               LocalDate birthday,
               Boolean isSpayed,
               Boolean isPregnant,
               Float bodyWeight,
               Integer activityLevel,
               Breed breed,
               User user) {
        this.name = name;
        this.sex = sex;
        this.age = age;
        this.birthday = birthday;
        this.isSpayed = isSpayed;
        this.isPregnant = isPregnant;
        this.bodyWeight = bodyWeight;
        this.activityLevel = activityLevel;
        this.breed = breed;
        this.user = user;
    }

    public void setThumbnailPath(String thumbnailPath) {
        this.thumbnailPath = thumbnailPath;
    }
    public void update(PetUpdateRequestDto requestDto, Breed breed) {
        this.name = requestDto.getName();
        this.sex = requestDto.getSex();
        this.isSpayed = requestDto.getIsSpayed();
        this.isPregnant = requestDto.getIsPregnant();
        this.age = requestDto.getAge().getMonths();
        this.birthday = requestDto.getAge().getBirthday();
        this.bodyWeight = requestDto.getBodyWeight();
        this.activityLevel = requestDto.getActivityLevel();
        this.breed = breed;
    }
    public Pet save(PetCreateRequestDto requestDto, User user, Breed breed) {
        this.name = requestDto.getName();
        this.sex = requestDto.getSex();
        this.age = requestDto.getAge().getMonths();
        this.birthday = requestDto.getAge().getBirthday();
        this.isSpayed = requestDto.getIsSpayed();
        this.isPregnant = requestDto.getIsPregnant();
        this.bodyWeight = requestDto.getBodyWeight();
        this.activityLevel = requestDto.getActivityLevel();
        this.user = user;
        this.breed = breed;

        return this;
    }
}
