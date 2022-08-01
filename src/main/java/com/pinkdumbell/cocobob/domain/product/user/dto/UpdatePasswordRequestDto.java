package com.pinkdumbell.cocobob.domain.product.user.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class UpdatePasswordRequestDto {

    private String email;
    private String password;
}
