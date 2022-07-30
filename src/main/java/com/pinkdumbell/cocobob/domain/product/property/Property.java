package com.pinkdumbell.cocobob.domain.product.property;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum Property {
    PREGNANT("임신과 수유"),
    OBESITY("비만"),
    AGED("노견"),
    GROWING("성장기(대형견)")
    ;

    private final String name;
}
