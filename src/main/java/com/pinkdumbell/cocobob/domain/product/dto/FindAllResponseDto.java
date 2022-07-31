package com.pinkdumbell.cocobob.domain.product.dto;

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

}
