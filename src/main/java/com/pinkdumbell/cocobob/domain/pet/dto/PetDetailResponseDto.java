package com.pinkdumbell.cocobob.domain.pet.dto;


import com.fasterxml.jackson.annotation.JsonFormat;
import com.pinkdumbell.cocobob.domain.pet.Pet;
import com.pinkdumbell.cocobob.domain.pet.PetSex;
import lombok.AllArgsConstructor;
import lombok.Getter;

import java.time.LocalDate;

@Getter
@AllArgsConstructor
public class PetDetailResponseDto {
    private final Long id;
    private final String name;
    private final String thumbnailPath;
    private final PetSex sex;
    private final Boolean isSpayed;
    private final Integer age;
    @JsonFormat(pattern = "yyyy-MM-dd")
    private final LocalDate birthday;
    private final Float bodyWeight;
    private final Boolean isPregnant;
    private final Integer fatLevel;
    private final Integer activityLevel;
    private final BreedsInfoResponseDto breedInfo;

    public PetDetailResponseDto(Pet entity) {
        this.id = entity.getId();
        this.name = entity.getName();
        this.thumbnailPath = entity.getThumbnailPath();
        this.sex = entity.getSex();
        this.isSpayed = entity.getIsSpayed();
        this.age = entity.getAge();
        this.birthday = entity.getBirthday();
        this.bodyWeight = entity.getBodyWeight();
        this.isPregnant = entity.getIsPregnant();
        this.fatLevel = entity.getFatLevel();
        this.activityLevel = entity.getActivityLevel();
        this.breedInfo = new BreedsInfoResponseDto(entity.getBreed());
    }
}
