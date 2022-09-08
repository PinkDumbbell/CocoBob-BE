package com.pinkdumbell.cocobob.domain.product.dto;

import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@NoArgsConstructor
@AllArgsConstructor
@Builder
@Getter
@Setter
public class ProductKeywordDto {

    @ApiModelProperty(notes = "상품 브랜드", example = "로얄 캐닌")
    private String brand;
    @ApiModelProperty(notes = "상품명", example = "더 리얼..")
    private String name;
    @ApiModelProperty(notes = "상품 Id", example = "12")
    private Long productId;

}
