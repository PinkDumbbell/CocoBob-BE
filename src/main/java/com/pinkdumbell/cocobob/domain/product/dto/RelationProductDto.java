package com.pinkdumbell.cocobob.domain.product.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

@Data
public class RelationProductDto {

    @JsonProperty("name")
    String name;
    @JsonProperty("product_id")
    Long productId;
}
