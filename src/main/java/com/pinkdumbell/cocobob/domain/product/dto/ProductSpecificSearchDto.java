package com.pinkdumbell.cocobob.domain.product.dto;

import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ProductSpecificSearchDto {

    @ApiModelProperty(notes = "상품 코드", example = "101202")
    private String code;
    @ApiModelProperty(notes = "상품 이름", example = "더리얼 밀 닭고기 200g")
    private String name;
    @ApiModelProperty(notes = "상품 브랜드", example = "하림펫푸드")
    private String brand;
    @ApiModelProperty(notes = "상품 내용", example = "눈물 자국")
    private String description;
    @ApiModelProperty(notes = "AFFCO 기준 충족", example = "true")
    private Boolean aafco;
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
    private Boolean pork;
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
    private Boolean hydrolyticPork;
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
    @ApiModelProperty(notes = "정렬 기준", example = "PRICE,ASC | LIKE,DESC ")
    private String sortCriteria;

}
