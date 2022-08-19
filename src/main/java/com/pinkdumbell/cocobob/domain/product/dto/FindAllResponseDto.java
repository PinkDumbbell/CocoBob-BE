package com.pinkdumbell.cocobob.domain.product.dto;

import com.pinkdumbell.cocobob.domain.product.Product;

import io.swagger.annotations.ApiModelProperty;
import java.util.List;

import java.util.stream.Collectors;

import lombok.Getter;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;


@Getter
public class FindAllResponseDto {
    @ApiModelProperty(notes = "전체 페이지 수", example = "41")
    int totalPages;
    @ApiModelProperty(notes = "전체 상품 수", example = "803")
    Long totalElements;
    @ApiModelProperty(notes = "페이지 당 상품 수", example = "20")
    int pageSize;
    @ApiModelProperty(notes = "현재 페이지", example = "0")
    int pageNumber;
    @ApiModelProperty(notes = "첫 페이지", example = "true")
    boolean isFirst;
    @ApiModelProperty(notes = "마지막 페이지", example = "false")
    boolean isLast;
    @ApiModelProperty(notes = "마지막 페이지", example = "false")
    boolean isEmpty;
    @ApiModelProperty(notes = "검색 상품 목록", example = "[{},{}...]")
    List<ProductSimpleResponseDto> productList;


    public FindAllResponseDto(Page<Product> pages) {
        this.productList = pages.getContent().stream().map(ProductSimpleResponseDto::new).collect(
            Collectors.toList());
        this.totalPages = pages.getTotalPages();
        this.totalElements = pages.getTotalElements();
        this.pageSize = pages.getSize();
        this.pageNumber = pages.getNumber();
        this.isFirst = pages.isFirst();
        this.isLast = pages.isLast();
        this.isEmpty = pages.isEmpty();
    }

    public FindAllResponseDto(PageImpl<ProductSimpleResponseDto> pages) {
        this.productList = pages.getContent();
        this.totalPages = pages.getTotalPages();
        this.totalElements = pages.getTotalElements();
        this.pageSize = pages.getSize();
        this.pageNumber = pages.getNumber();
        this.isFirst = pages.isFirst();
        this.isLast = pages.isLast();
        this.isEmpty = pages.isEmpty();

    }
}
