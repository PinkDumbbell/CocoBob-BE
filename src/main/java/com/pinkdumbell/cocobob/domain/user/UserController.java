package com.pinkdumbell.cocobob.domain.user;

import com.pinkdumbell.cocobob.domain.auth.dto.TokenRequestDto;
import com.pinkdumbell.cocobob.domain.auth.dto.TokenResponseDto;
import com.pinkdumbell.cocobob.common.dto.CommonResponseDto;
import com.pinkdumbell.cocobob.domain.user.dto.EmailDuplicationCheckResponseDto;
import com.pinkdumbell.cocobob.domain.user.dto.UserCreateRequestDto;
import com.pinkdumbell.cocobob.domain.user.dto.UserCreateResponseDto;
import com.pinkdumbell.cocobob.exception.CustomException;
import io.jsonwebtoken.JwtException;
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
        @ApiResponse(code = 200, message = "", response = CommonResponseDto.class),
        @ApiResponse(code = 500, message = "INTERNAL SERVER ERROR")
    })
    @PostMapping("/new")
    public ResponseEntity<CommonResponseDto> signup(
        @RequestBody @Valid UserCreateRequestDto requestDto) {
        return ResponseEntity.ok(CommonResponseDto.builder().
            status(200).
            code("SUCCESS SIGNUP").
            message("회원가입 정상처리").
            data(userService.signup(requestDto)).
            build());
    }


    @ApiOperation(value = "check email duplicated", notes = "이메일 중복 확인")
    @ApiResponses(value = {
        @ApiResponse(code = 200, message = "해당 이메일은 사용할 수 있습니다."),
        @ApiResponse(code = 409, message = "해당 이메일을 가진 사용자가 존재합니다")
    })
    @GetMapping("/email")
    public ResponseEntity<CommonResponseDto> checkEmailDuplicated(
        @RequestParam("email") String email) {
        return ResponseEntity.ok(CommonResponseDto.builder().
            status(200).
            code("SUCCESS CHECK EMAIL").
            message("중복 이메일 확인").
            data(userService.checkEmailDuplicated(email)).
            build());
    }

    @ApiOperation(value = "Login", notes = "서비스 자체 로그인")
    @ApiResponses(value = {
        @ApiResponse(code = 200, message = "", response = CommonResponseDto.class),
        @ApiResponse(code = 404, message = "USER_NOT_FOUND"),
        @ApiResponse(code = 403, message = "INVALID_PASSWORD")
    })
    @PostMapping("")
    public ResponseEntity<CommonResponseDto> login(@RequestBody UserLoginRequestDto requestDto) {
        return ResponseEntity.ok(CommonResponseDto.builder().
            status(200).
            code("SUCCESS LOGIN").
            message("로그인 정상처리").
            data(userService.login(requestDto)).
            build());
    }

    @ApiOperation(value = "Reissue", notes = "refresh Token을 통한 Token 재발행")
    @ApiResponses(value = {
        @ApiResponse(code = 200, message = "", response = CommonResponseDto.class),
        @ApiResponse(code = 401, message = "UNAUTHORIZED"),
    })
    @GetMapping("/token")
    public ResponseEntity<CommonResponseDto> reissue(
        @RequestHeader("authorization") String accessToken,
        @RequestHeader("refresh-token") String refreshToken) {
        TokenRequestDto tokenRequestDto = TokenRequestDto.builder()
            .accessToken(accessToken)
            .refreshToken(refreshToken)
            .build();

        try {
            return ResponseEntity.ok(CommonResponseDto.builder()
                .status(201).code("SUCCESS REISSUE")
                .message("토큰이 재발행 정상처리")
                .data(userService.reissue(tokenRequestDto))
                .build());
        } catch (CustomException | JwtException e) {
            throw e;
        }
    }

    @ApiOperation(value = "Logout", notes = "로그아웃")
    @ApiResponses(value = {
        @ApiResponse(code = 200, message = "로그아웃 처리완료 되었습니다.", response = CommonResponseDto.class),
        @ApiResponse(code = 401, message = "UNAUTHORIZED"),
    })
    @DeleteMapping("")
    public ResponseEntity<CommonResponseDto> logout(@RequestHeader("Authorization") String accessToken) {

        try {
            userService.logout(accessToken);
        } catch (CustomException e) {
            throw e;
        }
       

        return ResponseEntity.ok(CommonResponseDto.builder()
            .status(200).
            code("Logout Success").
            message("로그아웃 처리완료 되었습니다.").
            data(null).
            build());
    }

}
