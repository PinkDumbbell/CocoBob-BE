package com.pinkdumbell.cocobob.domain.product.dto;

import java.util.List;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ProductDetailResponseDto {

    private Long productId;

    private String code;

    private String category;

    private String name;

    private Integer price;

    private String productImage;

    private String productDetailImage;

    private String description;

    private Float protein;

    private Double amountOfProteinPerMcal;

    private Float fat;

    private Double amountOfFatPerMcal;

    private Float fiber;

    private Double amountOfFiberPerMcal;

    private Float mineral;

    private Double amountOfMineralPerMcal;

    private Float calcium;

    private Double amountOfCalciumPerMcal;

    private Float phosphorus;

    private Double amountOfPhosphorusPerMcal;

    private Float moisture;

    private Double kcalPerKg;

    private Boolean isAAFCOSatisfied;

    private Boolean beef;

    private Boolean mutton;

    private Boolean chicken;

    private Boolean duck;

    private Boolean turkey;

    private Boolean meat;

    private Boolean salmon;

    private Boolean hydrolyticBeef;

    private Boolean hydrolyticMutton;

    private Boolean hydrolyticChicken;

    private Boolean hydrolyticDuck;

    private Boolean hydrolyticTurkey;

    private Boolean hydrolyticMeat;

    private Boolean hydrolyticSalmon;

    private int likes;

    private List<PetPropertyResponseDto> petProperties;


}
