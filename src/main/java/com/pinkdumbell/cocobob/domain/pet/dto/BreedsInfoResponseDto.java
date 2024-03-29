package com.pinkdumbell.cocobob.domain.pet.dto;

import io.swagger.annotations.ApiModelProperty;
import com.pinkdumbell.cocobob.domain.pet.breed.Breed;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class BreedsInfoResponseDto {

    @ApiModelProperty(notes = "종류 Id", example = "1")
    private Long id;
    @ApiModelProperty(notes = "견종 이름", example = "러시안 토이")
    private String name;
    @ApiModelProperty(notes = "견종 사이즈", example = "초소형")
    private String size;

    public BreedsInfoResponseDto(Breed entity) {
        this.id = entity.getId();
        this.name = entity.getName();
        this.size = entity.getSize().toString();
    }
}
