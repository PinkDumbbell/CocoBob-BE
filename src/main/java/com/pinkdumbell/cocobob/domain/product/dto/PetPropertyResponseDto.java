package com.pinkdumbell.cocobob.domain.product.dto;

import com.pinkdumbell.cocobob.domain.product.property.PetProperty;
import com.pinkdumbell.cocobob.domain.product.property.PetPropertyRepository;
import com.pinkdumbell.cocobob.domain.product.property.Property;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class PetPropertyResponseDto {

    private Property property;

    public PetPropertyResponseDto(PetProperty property) {
        this.property = property.getProperty();
    }


}
