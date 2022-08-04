package com.pinkdumbell.cocobob.domain.pet.dto;

import com.pinkdumbell.cocobob.domain.pet.breed.Breed;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class BreedsInfoResponseDto {

    private Long id;
    private String name;
    private String size;

    public BreedsInfoResponseDto(Breed entity) {
        this.id = entity.getId();
        this.name = entity.getName();
        this.size = entity.getSize().toString();
    }
}
