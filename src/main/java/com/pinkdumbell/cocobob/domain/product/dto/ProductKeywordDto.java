package com.pinkdumbell.cocobob.domain.product.dto;

import io.swagger.annotations.ApiModelProperty;
import java.util.List;
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

    @ApiModelProperty(notes = "상품 리스트", example = "[펫 더 리얼, 리얼, ...]")
    private List<String> names;

}
