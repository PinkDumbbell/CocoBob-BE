package com.pinkdumbell.cocobob.domain.user.dto;

import com.pinkdumbell.cocobob.domain.user.User;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class UserLoginResponseDto {

    @ApiModelProperty(notes = "사용자 Id", example = "234234")
    private final Long userId;
    @ApiModelProperty(notes = "사용자 email", example = "test@test.com")
    private final String email;
    @ApiModelProperty(notes = "사용자 성명", example = "이호용")
    private final String username;
    @ApiModelProperty(notes = "사용자 권한", example = "USER")
    private final String role;
    @ApiModelProperty(notes = "access Toeken", example = "eyJhbGciOiJIUzI1NiJ9.eyJzdWIiOiJ0ZXN0MkB0ZXN0LmNvbSIsImlhdCI6MTY1OTU5NjQ0MywiZXhwIjoxNjU5NTk4MjQzfQ.TDCYI9q3rEKRFAx4zSrwRswl8vESWgmc5ZBEV2tugJ8")
    private final String accessToken;
    @ApiModelProperty(notes = "refreshToken", example = "eyJhbGciOiJIUzI1NiJ9.eyJpYXQiOjE2NTk1OTY0NDMsImV4cCI6MTY2MDIwMTI0M30.7R6fPLbkQ8WiTG2W-p5wBW9RFU3XDvoY_eY0_wS8kAQ")
    private final String refreshToken;

    public UserLoginResponseDto(User entity, String accessToken, String refreshToken) {
        this.userId = entity.getId();
        this.email = entity.getEmail();
        this.username = entity.getUsername();
        this.role = entity.getRole().toString();
        this.accessToken = accessToken;
        this.refreshToken = refreshToken;
    }
}
