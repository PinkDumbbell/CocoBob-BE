package com.pinkdumbell.cocobob.domain.pet.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.pinkdumbell.cocobob.domain.pet.Pet;
import com.pinkdumbell.cocobob.domain.pet.PetSex;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Getter;
import org.springframework.format.annotation.DateTimeFormat;

import java.time.LocalDate;

@Getter
@AllArgsConstructor
public class PetInfoResponseDto {

    @ApiModelProperty(notes = "반려동물 아이디", example = "1")
    private final Long id;
    @ApiModelProperty(notes = "반려동물 이름", example = "코코")
    private final String name;
    @ApiModelProperty(notes = "반려동물 사진 썸네일 이미지 저장 경로", example = "https://...")
    private final String thumbnailPath;
    @ApiModelProperty(notes = "성별", example = "FEMALE")
    private final PetSex sex;
    @ApiModelProperty(notes = "나이(개월 수)", example = "30")
    private final Integer age;
    @ApiModelProperty(notes = "생일", example = "2022-06-01")
    @JsonFormat(pattern = "yyyy-MM-dd")
    private final LocalDate birthday;
    @ApiModelProperty(notes = "몸무게", example = "3.4")
    private final Float bodyWeight;
    @ApiModelProperty(notes = "견종명", example = "진돗개")
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
