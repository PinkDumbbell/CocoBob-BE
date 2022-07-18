package com.pinkdumbell.cocobob.domain.user;

import com.pinkdumbell.cocobob.domain.user.dto.EmailDuplicationCheckResponseDto;
import com.pinkdumbell.cocobob.domain.user.dto.UserCreateRequestDto;
import com.pinkdumbell.cocobob.domain.user.dto.UserCreateResponseDto;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.pinkdumbell.cocobob.domain.user.dto.UserLoginRequestDto;
import com.pinkdumbell.cocobob.domain.user.dto.UserLoginResponseDto;

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
        @ApiResponse(code = 500, message = "INTERNAL SERVER ERROR")
    })
    @PostMapping("/new")
    public ResponseEntity<UserCreateResponseDto> signup(
        @RequestBody @Valid UserCreateRequestDto requestDto) {
        return ResponseEntity.ok(userService.signup(requestDto));
    }


    @ApiOperation(value = "check email duplicated", notes = "이메일 중복 확인")
    @ApiResponses(value = {
        @ApiResponse(code = 200, message = "해당 이메일은 사용할 수 있습니다."),
        @ApiResponse(code = 409, message = "해당 이메일을 가진 사용자가 존재합니다")
    })
    @GetMapping("/email")
    public ResponseEntity<EmailDuplicationCheckResponseDto> checkEmailDuplicated(
        @RequestParam("email") String email) {
        return ResponseEntity.ok(userService.checkEmailDuplicated(email));
    }

    @PostMapping("")
    public ResponseEntity<UserLoginResponseDto> Login(@RequestBody UserLoginRequestDto requestDto) {
        return ResponseEntity.ok(userService.login(requestDto));
    }


}
