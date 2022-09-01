package com.pinkdumbell.cocobob.domain.product.dto;

import io.swagger.annotations.ApiModelProperty;
import java.util.List;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;


@NoArgsConstructor
@AllArgsConstructor
@Data
@Builder
public class ProductSpecificSearchWithLikeDto {

    @ApiModelProperty(notes = "포함을 원하는 재료명", example = "salmon, hydrolyticDuck")
    private List<String> ingredient;
    @ApiModelProperty(notes = "미포함을 원하는 재료명", example = "pork, chicken")
    private List<String> allergyIngredient;
    @ApiModelProperty(notes = "브랜드", example = "로얄캐닌, 웰츠독")
    private List<String> brands;
    @ApiModelProperty(notes = "유형에 따른 사료", example = "obesity, pregnant")
    private List<String> types;
    @ApiModelProperty(notes = "상품 코드", example = "101202, 101201")
    private List<String> codes;
    @ApiModelProperty(notes = "검색 키워드", example = "로얄캐닌 다이어트")
    private String keyword;
    @ApiModelProperty(notes = "아프코 기준 만족", example = "true")
    private Boolean aafco;
    @ApiModelProperty(notes = "정렬 기준", example = "ID,ASC | PRICE,ASC |LIKE,DESC")
    private String sort;
    @ApiModelProperty(notes = "페이지", example = "페이지 번호(0...N)")
    private int page = 0;
    @ApiModelProperty(notes = "페이지 크기", example = "페이지 사이즈(1...N)")
    private int size = 20;

    public void setSize(Integer size) {

        this.size = (size != null) ? size.intValue() : 20;
    }

    public void setPage(Integer page) {

        this.page = (page != null) ? page.intValue() : 0;
    }

    public void addType(String type) {
        this.types.add(type);
    }

    public int calOffset() {
        return this.page * this.size;
    }

}
