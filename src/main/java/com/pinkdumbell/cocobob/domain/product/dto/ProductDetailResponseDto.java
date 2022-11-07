package com.pinkdumbell.cocobob.domain.product.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.pinkdumbell.cocobob.domain.product.Product;
import com.pinkdumbell.cocobob.domain.product.like.Like;
import io.swagger.annotations.ApiModelProperty;
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

    @ApiModelProperty(notes = "상품 Id", example = "1")
    private Long productId;
    @ApiModelProperty(notes = "상품 코드", example = "101202")
    private String code;
    @ApiModelProperty(notes = "상품 카테고리", example = "101202")
    private String category;
    @ApiModelProperty(notes = "상품 이름", example = "더리얼 밀 그레인프리 닭고기 60g")
    private String name;
    @ApiModelProperty(notes = "상품 브랜드", example = "하림펫푸드")
    private String brand;
    @ApiModelProperty(notes = "상품 브랜드 이미지", example = "상품 브랜드 이미지 URL")
    private String brandImage;
    @ApiModelProperty(notes = "상품 가격", example = "13260")
    private Integer price;
    @ApiModelProperty(notes = "상품 이미지", example = "상품 이미지 URL")
    private String productImage;
    @ApiModelProperty(notes = "상품 상세 이미지", example = "상품 상세 이미지 URL")
    private String productDetailImage;
    @ApiModelProperty(notes = "상품 상세 이미지 webp", example = "상품 상세 이미지 URL")
    private String productDetailImageWebp;
    @ApiModelProperty(notes = "상품 설명", example = "펫탈로그의 사료는...")
    private String description;
    @ApiModelProperty(notes = "조단백 함량", example = "24")
    private Float protein;
    @ApiModelProperty(notes = "1000Kcal당 조단백 양", example = "71.55")
    private Double amountOfProteinPerMcal;
    @ApiModelProperty(notes = "조지방 함량", example = "10")
    private Float fat;
    @ApiModelProperty(notes = "1000Kcal당 조지방 양", example = "29.81")
    private Double amountOfFatPerMcal;
    @ApiModelProperty(notes = "조섬유 함량", example = "8")
    private Float fiber;
    @ApiModelProperty(notes = "1000Kcal당 조섬유 양", example = "23.85")
    private Double amountOfFiberPerMcal;
    @ApiModelProperty(notes = "조회분 함량", example = "10")
    private Float mineral;
    @ApiModelProperty(notes = "1000Kcal당 조회분 양", example = "29.81")
    private Double amountOfMineralPerMcal;
    @ApiModelProperty(notes = "칼슘 함량", example = "0.8")
    private Float calcium;
    @ApiModelProperty(notes = "1000Kcal당 칼슘 양", example = "2.38")
    private Double amountOfCalciumPerMcal;
    @ApiModelProperty(notes = "인 함량", example = "0.6")
    private Float phosphorus;
    @ApiModelProperty(notes = "1000Kcal당 인 양", example = "1.78")
    private Double amountOfPhosphorusPerMcal;
    @ApiModelProperty(notes = "수분", example = "11")
    private Float moisture;
    @ApiModelProperty(notes = "Kg딩 총 칼로리", example = "3353.93")
    private Double kcalPerKg;
    @ApiModelProperty(notes = "AAFCO 기준 충족", example = "true")
    private Boolean isAAFCOSatisfied;
    @ApiModelProperty(notes = "소고기 햠유", example = "ture")
    private Boolean beef;
    @ApiModelProperty(notes = "양고기 햠유", example = "false")
    private Boolean mutton;
    @ApiModelProperty(notes = "닭고기 햠유", example = "ture")
    private Boolean chicken;
    @ApiModelProperty(notes = "오리고기 햠유", example = "false")
    private Boolean duck;
    @ApiModelProperty(notes = "칠면조 햠유", example = "false")
    private Boolean turkey;
    @ApiModelProperty(notes = "돼지고기 햠유", example = "ture")
    private Boolean meat;
    @ApiModelProperty(notes = "연어 햠유", example = "false")
    private Boolean salmon;
    @ApiModelProperty(notes = "가수분해 소고기 햠유", example = "false")
    private Boolean hydrolyticBeef;
    @ApiModelProperty(notes = "가수분해 양고기 햠유", example = "false")
    private Boolean hydrolyticMutton;
    @ApiModelProperty(notes = "가수분해 닭고기 햠유", example = "false")
    private Boolean hydrolyticChicken;
    @ApiModelProperty(notes = "가수분해 오리고기 햠유", example = "false")
    private Boolean hydrolyticDuck;
    @ApiModelProperty(notes = "가수분해 칠면조 햠유", example = "false")
    private Boolean hydrolyticTurkey;
    @ApiModelProperty(notes = "가수분해 돼지고기 햠유", example = "false")
    private Boolean hydrolyticMeat;
    @ApiModelProperty(notes = "가수분해 연어고기 햠유", example = "false")
    private Boolean hydrolyticSalmon;
    @ApiModelProperty(notes = "노령견 기준 충족", example = "false")
    private Boolean aged;
    @ApiModelProperty(notes = "성장기 기준 충족", example = "true")
    private Boolean growing;
    @ApiModelProperty(notes = "임신/수유 기준 충족", example = "false")
    private Boolean pregnant;
    @ApiModelProperty(notes = "비만견 기준 충족", example = "false")
    private Boolean obesity;
    // 추후 추가예정
    @ApiModelProperty(notes = "상품 좋아요 수", example = "100")
    private Long likes;
    @ApiModelProperty(notes = "유저가 좋아한 상품인지", example = "false")
    @JsonProperty(value = "isLiked")
    private Boolean isLiked;

    public ProductDetailResponseDto(Product product, Long likes, boolean isLiked) {

        this.productId = product.getId();
        this.code = product.getCode();
        this.category = product.getCategory();
        this.name = product.getName();
        this.brand = product.getBrand();
        this.brandImage = product.getBrandImage();
        this.price = product.getPrice();
        this.productImage = product.getProductImage();
        this.productDetailImage = product.getProductDetailImage();
        this.productDetailImageWebp = product.getProductDetailImageWebp();
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
        this.likes = likes;
        this.isLiked = isLiked;
        this.aged = product.getAged();
        this.growing = product.getGrowing();
        this.pregnant = product.getPregnant();
        this.obesity = product.getObesity();
    }
}