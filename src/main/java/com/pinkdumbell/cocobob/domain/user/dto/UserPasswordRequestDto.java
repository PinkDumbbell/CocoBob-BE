package com.pinkdumbell.cocobob.domain.user.dto;

import io.swagger.annotations.ApiModelProperty;
import lombok.*;

@Getter
@RequiredArgsConstructor
@Builder
public class UserPasswordRequestDto {
    @ApiModelProperty(notes = "변경할 password", example = "password")
    private final String password;
}
