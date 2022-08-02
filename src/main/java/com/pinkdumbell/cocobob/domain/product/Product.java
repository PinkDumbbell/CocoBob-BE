package com.pinkdumbell.cocobob.domain.product;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Getter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Entity
public class Product {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "product_id")
    private Long id;

    private String code;

    private String category;

    private String name;

    private Integer price;

    @Column(length = 1000)
    private String thumbnail;

    @Column(length = 1000)
    private String productImage;

    @Column(length = 1000)
    private String productDetailImage;

    @Column(columnDefinition = "TEXT")
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

    @Column(name = "is_aafco_satisfied")
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

}
