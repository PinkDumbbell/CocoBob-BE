package com.pinkdumbell.cocobob.domain.pet.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class BreedsInfoResponseDto {

    private Long id;
    private String name;
    private String  size;
}
