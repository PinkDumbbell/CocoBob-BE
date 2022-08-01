package com.pinkdumbell.cocobob.domain.product.dto;

import com.pinkdumbell.cocobob.domain.product.Product;

import java.util.List;

import java.util.stream.Collectors;

import lombok.Getter;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;


@Getter
public class FindAllResponseDto {

    int totalPages;
    Long totalElements;
    int pageSize;
    int pageNumber;
    boolean isFirst;
    boolean isLast;
    boolean isEmpty;
    List<ListProductDto> productList;

    public FindAllResponseDto(Page<Product> pages) {
        this.productList = pages.getContent().stream().map(ListProductDto::new).collect(
            Collectors.toList());
        this.totalPages = pages.getTotalPages();
        this.totalElements = pages.getTotalElements();
        this.pageSize = pages.getSize();
        this.pageNumber = pages.getNumber();
        this.isFirst = pages.isFirst();
        this.isLast = pages.isLast();
        this.isEmpty = pages.isEmpty();
    }
}
