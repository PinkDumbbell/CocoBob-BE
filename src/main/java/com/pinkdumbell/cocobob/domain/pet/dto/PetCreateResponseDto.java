package com.pinkdumbell.cocobob.domain.pet.dto;

import com.pinkdumbell.cocobob.domain.pet.Pet;
import lombok.Getter;

@Getter
public class PetCreateResponseDto {
    private Long petId;

    public PetCreateResponseDto(Pet entity) {
        this.petId = entity.getId();
    }
}
