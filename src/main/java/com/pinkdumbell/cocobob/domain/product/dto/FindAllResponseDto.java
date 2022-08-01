package com.pinkdumbell.cocobob.domain.product.dto;

import com.pinkdumbell.cocobob.domain.product.Product;
import javax.persistence.Column;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Builder
@AllArgsConstructor
@NoArgsConstructor
@Getter
public class FindAllResponseDto {

    private Long productId;

    private String code;

    private String name;

    private String category;

    private Integer price;

    private String thumbnail;

    private String description;

    public FindAllResponseDto(Product product) {
        this.productId = product.getId();
        this.name = product.getName();
        this.price = product.getPrice();
        this.code = product.getCode();
        this.category = product.getCategory();
        this.thumbnail = product.getThumbnail();
        this.description = product.getDescription();
    }

}
