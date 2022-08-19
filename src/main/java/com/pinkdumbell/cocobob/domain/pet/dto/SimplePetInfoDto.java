package com.pinkdumbell.cocobob.domain.pet.dto;

import com.pinkdumbell.cocobob.domain.pet.Pet;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class SimplePetInfoDto {
    @ApiModelProperty(notes = "반려동물 아이디", example = "1")
    private final Long petId;
    @ApiModelProperty(notes = "반려동물 이름", example = "coco")
    private final String name;
    @ApiModelProperty(notes = "반려동물 프로필 사진의 썸네일 이미지 경로", example = "https://...")
    private final String thumbnailPath;

    public SimplePetInfoDto(Pet entity) {
        this.petId = entity.getId();
        this.name = entity.getName();
        this.thumbnailPath = entity.getThumbnailPath();
    }
}
