package com.pinkdumbell.cocobob.domain.pet.dto;


import com.fasterxml.jackson.annotation.JsonFormat;
import com.pinkdumbell.cocobob.domain.pet.Pet;
import com.pinkdumbell.cocobob.domain.pet.PetSex;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Getter;

import java.time.LocalDate;

@Getter
@AllArgsConstructor
public class PetDetailResponseDto {
    @ApiModelProperty(notes = "반려동물 아이디", example = "2")
    private final Long id;
    @ApiModelProperty(notes = "반려동물 이름", example = "코코")
    private final String name;
    @ApiModelProperty(notes = "반려동물 이미지 썸네일 경로", example = "https://...")
    private final String thumbnailPath;
    @ApiModelProperty(notes = "성별", example = "FEMALE")
    private final PetSex sex;
    @ApiModelProperty(notes = "중성화 여부", example = "true")
    private final Boolean isSpayed;
    @ApiModelProperty(notes = "나이(개월 수)", example = "30")
    private final Integer age;
    @ApiModelProperty(notes = "생일", example = "2020-06-01")
    @JsonFormat(pattern = "yyyy-MM-dd")
    private final LocalDate birthday;
    @ApiModelProperty(notes = "몸무게", example = "5.4")
    private final Float bodyWeight;
    @ApiModelProperty(notes = "임신/수유 여부", example = "false")
    private final Boolean isPregnant;
    @ApiModelProperty(notes = "", example = "")
    private final Integer fatLevel;
    @ApiModelProperty(notes = "활동수준", example = "3")
    private final Integer activityLevel;
    @ApiModelProperty(notes = "견종 정보", example = "{ ... }")
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
