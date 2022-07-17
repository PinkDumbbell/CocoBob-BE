package com.pinkdumbell.cocobob.domain.user;

import com.pinkdumbell.cocobob.domain.user.dto.UserCreateRequestDto;
import com.pinkdumbell.cocobob.domain.user.dto.UserCreateResponseDto;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;

@ApiOperation("User API")
@RequestMapping("/v1/users")
@RequiredArgsConstructor
@RestController
public class UserController {
    private final UserService userService;

    @ApiOperation(value = "signup", notes = "서비스 자체 회원가입")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "", response = UserCreateResponseDto.class),
            @ApiResponse(code = 409, message = "해당 이메일을 가진 사용자가 존재합니다.")
    })
    @PostMapping("/new")
    public ResponseEntity<UserCreateResponseDto> signup(@RequestBody @Valid UserCreateRequestDto requestDto) {
        return ResponseEntity.ok(userService.signup(requestDto));
    }
}
