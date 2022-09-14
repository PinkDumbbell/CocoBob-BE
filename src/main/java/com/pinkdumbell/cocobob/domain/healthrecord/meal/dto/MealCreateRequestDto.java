package com.pinkdumbell.cocobob.domain.healthrecord.meal.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class MealCreateRequestDto {
    private String productName;
    private Double kcal;
    private Integer amount;
    private Long productId;
}
