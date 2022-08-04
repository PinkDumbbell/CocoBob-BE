package com.pinkdumbell.cocobob.domain.product.dto;

import com.pinkdumbell.cocobob.domain.product.Product;
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

    private Boolean aged;

    private Boolean growing;

    private Boolean pregnant;

    private Boolean obesity;


    private int likeCount;

    private List<PetPropertyResponseDto> petProperties;

    public ProductDetailResponseDto(Product product) {

        this.productId = product.getId();
        this.code = product.getCode();
        this.category = product.getCategory();
        this.name = product.getName();
        this.price = product.getPrice();
        this.productImage = product.getProductImage();
        this.productDetailImage = product.getProductDetailImage();
        this.description = product.getDescription();
        this.protein = product.getProtein();
        this.amountOfProteinPerMcal = product.getAmountOfProteinPerMcal();
        this.fat = product.getFat();
        this.amountOfFatPerMcal = product.getAmountOfFatPerMcal();
        this.fiber = product.getFiber();
        this.amountOfFiberPerMcal = product.getAmountOfFiberPerMcal();
        this.mineral = product.getMineral();
        this.amountOfMineralPerMcal = product.getAmountOfMineralPerMcal();
        this.calcium = product.getCalcium();
        this.amountOfCalciumPerMcal = product.getAmountOfCalciumPerMcal();
        this.phosphorus = product.getPhosphorus();
        this.amountOfPhosphorusPerMcal = product.getAmountOfPhosphorusPerMcal();
        this.moisture = product.getMoisture();
        this.kcalPerKg = product.getKcalPerKg();
        this.isAAFCOSatisfied = product.getIsAAFCOSatisfied();
        this.beef = product.getBeef();
        this.mutton = product.getMutton();
        this.chicken = product.getChicken();
        this.duck = product.getDuck();
        this.turkey = product.getTurkey();
        this.meat = product.getMeat();
        this.salmon = product.getSalmon();
        this.hydrolyticBeef = product.getHydrolyticBeef();
        this.hydrolyticMutton = product.getHydrolyticMutton();
        this.hydrolyticChicken = product.getHydrolyticChicken();
        this.hydrolyticDuck = product.getHydrolyticDuck();
        this.hydrolyticTurkey = product.getHydrolyticTurkey();
        this.hydrolyticMeat = product.getHydrolyticMeat();
        this.hydrolyticSalmon = product.getHydrolyticSalmon();
        //추후 추가 예정
        //this.likeCount;
        this.aged = product.getAged();
        this.growing = product.getGrowing();
        this.pregnant = product.getPregnant();
        this.obesity = product.getObesity();
    }
}