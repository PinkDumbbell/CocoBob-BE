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
public class ProductSimpleResponseDto {

    @ApiModelProperty(notes = "상품 Id", example = "1")
    private Long productId;
    @ApiModelProperty(notes = "상품 코드", example = "101202")
    private String code;
    @ApiModelProperty(notes = "상품 이름", example = "하림펫푸드 더리얼 밀 그레인프리 닭고기 60g 6개")
    private String name;
    @ApiModelProperty(notes = "상품 브랜드", example = "하림펫푸드")
    private String brand;
    @ApiModelProperty(notes = "상품 카테고리", example = "화식/습식")
    private String category;
    @ApiModelProperty(notes = "상품 가격", example = "13260")
    private Integer price;
    @ApiModelProperty(notes = "상품 썸네일", example = "이미지 URL")
    private String thumbnail;
    @ApiModelProperty(notes = "상품 설명", example = "펫탈로그의 사료는...")
    private String description;
    @ApiModelProperty(notes = "AAFCO 기준 만족", example = "true")
    private Boolean isAAFCOSatisfied;
    @ApiModelProperty(notes = "노령견 기준 만족", example = "true")
    private boolean aged;
    @ApiModelProperty(notes = "성장기 기준 만족", example = "true")
    private boolean growing;
    @ApiModelProperty(notes = "임신/수유 기준 만족", example = "true")
    private boolean pregnant;
    @ApiModelProperty(notes = "비만견 기준 만족", example = "true")
    private boolean obesity;
    @ApiModelProperty(notes = "좋아요 수", example = "12312")
    private Long likes;
    @ApiModelProperty(notes = "사용자가 좋아요 누른 게시물", example = "false")
    private boolean isUserLike;

    public ProductSimpleResponseDto(Product product) {
        this.productId = product.getId();
        this.name = product.getName();
        this.price = product.getPrice();
        this.code = product.getCode();
        this.category = product.getCategory();
        this.thumbnail = product.getThumbnail();
        this.description = product.getDescription();
        this.isAAFCOSatisfied = product.getIsAAFCOSatisfied();
        this.aged = product.getAged();
        this.growing = product.getGrowing();
        this.pregnant = product.getPregnant();
        this.obesity = product.getObesity();
        this.brand = product.getBrand();
    }


}
