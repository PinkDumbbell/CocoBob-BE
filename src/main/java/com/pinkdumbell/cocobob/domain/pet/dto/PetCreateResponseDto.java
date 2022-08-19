package com.pinkdumbell.cocobob.domain.pet.dto;

import com.pinkdumbell.cocobob.domain.pet.Pet;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

@Builder
@AllArgsConstructor
@Getter
public class PetCreateResponseDto {
    @ApiModelProperty(notes = "반려동물 아이디", example = "3")
    private Long petId;

    public PetCreateResponseDto(Pet entity) {
        this.petId = entity.getId();
    }
}
