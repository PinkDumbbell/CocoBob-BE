package com.pinkdumbell.cocobob.domain.product.dto;

import com.pinkdumbell.cocobob.domain.product.Product;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Builder
@AllArgsConstructor
@NoArgsConstructor
@Getter
public class ListProductDto {

    @ApiModelProperty(notes = "상품 Id", example = "1")
    private Long productId;
    @ApiModelProperty(notes = "상품 코드", example = "101202")
    private String code;
    @ApiModelProperty(notes = "상품 이름", example = "[2+1] 하림펫푸드 더리얼 밀 그레인프리 닭고기 60g 6개")
    private String name;
    @ApiModelProperty(notes = "상품 카테고리", example = "화식/습식")
    private String category;
    @ApiModelProperty(notes = "상품 가격", example = "13260")
    private Integer price;
    @ApiModelProperty(notes = "상품 썸네일", example = "이미지 URL")
    private String thumbnail;
    @ApiModelProperty(notes = "상품 설명", example = "펫탈로그의 사료는...")
    private String description;
    @ApiModelProperty(notes = "AAFCO 기준 만족", example = "True")
    private boolean isAAFCO;
    @ApiModelProperty(notes = "노령견 기준 만족", example = "True")
    private boolean aged;
    @ApiModelProperty(notes = "성장기 기준 만족", example = "True")
    private boolean growing;
    @ApiModelProperty(notes = "임신/수유 기준 만족", example = "True")
    private boolean pregnant;
    @ApiModelProperty(notes = "비만견 기준 만족", example = "True")
    private boolean obesity;

    public ListProductDto(Product product) {
        this.productId = product.getId();
        this.name = product.getName();
        this.price = product.getPrice();
        this.code = product.getCode();
        this.category = product.getCategory();
        this.thumbnail = product.getThumbnail();
        this.description = product.getDescription();
        this.isAAFCO = product.getIsAAFCOSatisfied();
        this.aged = product.getAged();
        this.growing = product.getGrowing();
        this.pregnant = product.getPregnant();
        this.obesity = product.getObesity();
    }

}
