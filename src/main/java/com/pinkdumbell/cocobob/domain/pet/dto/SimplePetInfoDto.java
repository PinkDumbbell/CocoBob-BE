package com.pinkdumbell.cocobob.domain.pet.dto;

import com.pinkdumbell.cocobob.domain.pet.Pet;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class SimplePetInfoDto {
    private final Long petId;
    private final String name;
    private final String thumbnailPath;

    public SimplePetInfoDto(Pet entity) {
        this.petId = entity.getId();
        this.name = entity.getName();
        this.thumbnailPath = entity.getThumbnailPath();
    }
}
