package com.pinkdumbell.cocobob.domain.user.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
@Builder
@AllArgsConstructor
public class RepresentativePetUpdateDto {
    private Long representativePetId;
}
