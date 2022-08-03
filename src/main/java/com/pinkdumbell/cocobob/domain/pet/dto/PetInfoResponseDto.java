package com.pinkdumbell.cocobob.domain.pet.dto;

import com.pinkdumbell.cocobob.domain.pet.Pet;
import com.pinkdumbell.cocobob.domain.pet.PetSex;
import lombok.AllArgsConstructor;
import lombok.Getter;

import java.time.LocalDate;

@Getter
@AllArgsConstructor
public class PetInfoResponseDto {
    private final Long id;
    private final String name;
    private final String thumbnailPath;
    private final PetSex sex;
    private final Integer age;
    private final LocalDate birthday;
    private final Float bodyWeight;
    private final String breedName;

    public PetInfoResponseDto(Pet entity) {
        this.id = entity.getId();
        this.name = entity.getName();
        this.thumbnailPath = entity.getThumbnailPath();
        this.sex = entity.getSex();
        this.age = entity.getAge();
        this.birthday = entity.getBirthday();
        this.bodyWeight = entity.getBodyWeight();
        this.breedName = entity.getBreed().getName();
    }
}
