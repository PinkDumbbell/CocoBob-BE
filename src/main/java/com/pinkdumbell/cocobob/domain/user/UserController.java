package com.pinkdumbell.cocobob.domain.user;

import com.pinkdumbell.cocobob.domain.auth.dto.TokenRequestDto;
import com.pinkdumbell.cocobob.common.dto.CommonResponseDto;
import com.pinkdumbell.cocobob.domain.auth.dto.TokenResponseDto;
import com.pinkdumbell.cocobob.domain.user.dto.*;

import com.pinkdumbell.cocobob.exception.CustomException;
import io.jsonwebtoken.JwtException;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;

import lombok.RequiredArgsConstructor;


import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@ApiOperation("User API")
@RequestMapping("/v1/users")
@RequiredArgsConstructor
@RestController
public class UserController {

    private final UserService userService;

    private class SignUpResponseClass extends CommonResponseDto<UserCreateResponseDto> {

        public SignUpResponseClass(int status, String code, String message,
            UserCreateResponseDto data) {
            super(status, code, message, data);
        }
    }

    private class CheckEmailResponseClass extends
        CommonResponseDto<EmailDuplicationCheckResponseDto> {

        public CheckEmailResponseClass(int status, String code, String message,
            EmailDuplicationCheckResponseDto data) {
            super(status, code, message, data);
        }
    }

    private class LoginResponseClass extends CommonResponseDto<UserLoginResponseDto> {

        public LoginResponseClass(int status, String code, String message,
            UserLoginResponseDto data) {
            super(status, code, message, data);
        }
    }

    private class ReissueResponseClass extends CommonResponseDto<TokenResponseDto> {

        public ReissueResponseClass(int status, String code, String message,
            TokenResponseDto data) {
            super(status, code, message, data);
        }
    }


    @ApiOperation(value = "signup", notes = "서비스 자체 회원가입")
    @ApiResponses(value = {
        @ApiResponse(code = 200, message = "", response = SignUpResponseClass.class),
        @ApiResponse(code = 500, message = "INTERNAL SERVER ERROR")
    })
    @PostMapping("/new")
    public ResponseEntity<SignUpResponseClass> signup(
        @RequestBody @Valid UserCreateRequestDto requestDto) {

        return ResponseEntity.ok(new SignUpResponseClass(HttpStatus.OK.value(),
            "SUCCESS SIGNUP",
            "회원가입 정상처리",
            userService.signup(requestDto)));
    }


    @ApiOperation(value = "check email duplicated", notes = "이메일 중복 확인")
    @ApiResponses(value = {
        @ApiResponse(code = 200, message = "해당 이메일은 사용할 수 있습니다.", response = CheckEmailResponseClass.class),
        @ApiResponse(code = 409, message = "해당 이메일을 가진 사용자가 존재합니다")
    })
    @GetMapping("/email")
    public ResponseEntity<CheckEmailResponseClass> checkEmailDuplicated(
        @RequestParam("email") String email) {

        return ResponseEntity.ok(new CheckEmailResponseClass(HttpStatus.OK.value(),
            "SUCCESS CHECK EMAIL",
            "중복 이메일 확인",
            userService.checkEmailDuplicated(email)));
    }


    @ApiOperation(value = "Login", notes = "서비스 자체 로그인")
    @ApiResponses(value = {
        @ApiResponse(code = 200, message = "", response = LoginResponseClass.class),
        @ApiResponse(code = 404, message = "USER_NOT_FOUND"),
        @ApiResponse(code = 403, message = "INVALID_PASSWORD")
    })
    @PostMapping("")
    public ResponseEntity<LoginResponseClass> login(@RequestBody UserLoginRequestDto requestDto) {

        return ResponseEntity.ok(new LoginResponseClass(HttpStatus.OK.value(),
            "SUCCESS LOGIN",
            "로그인 정상처리",
            userService.login(requestDto)));
    }

    @ApiOperation(value = "Reissue", notes = "refresh Token을 통한 Token 재발행")
    @ApiResponses(value = {
        @ApiResponse(code = 200, message = "", response = ReissueResponseClass.class),
        @ApiResponse(code = 401, message = "UNAUTHORIZED"),
    })
    @GetMapping("/token")
    public ResponseEntity<ReissueResponseClass> reissue(
        @RequestHeader("authorization") String accessToken,
        @RequestHeader("refresh-token") String refreshToken) {
        TokenRequestDto tokenRequestDto = TokenRequestDto.builder()
            .accessToken(accessToken)
            .refreshToken(refreshToken)
            .build();

        try {

            return ResponseEntity.ok(new ReissueResponseClass(HttpStatus.ACCEPTED.value(),
                "SUCCESS REISSUE",
                "토큰이 재발행 정상처리",
                userService.reissue(tokenRequestDto)));
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
    public ResponseEntity<CommonResponseDto> logout(
        @RequestHeader("Authorization") String accessToken) {

        try {
            userService.logout(accessToken);
        } catch (CustomException e) {
            throw e;
        }

        return ResponseEntity.ok(CommonResponseDto.builder()
            .status(HttpStatus.OK.value()).
            code("SUCCESS LOGOUT").
            message("로그아웃 처리완료 되었습니다.").
            data(null).
            build());
    }

    @ApiOperation(value = "SendNewPassword", notes = "비밀번호 분실시 새로운 비밀번호를 발급")
    @ApiResponses(value = {
        @ApiResponse(code = 200, message = "", response = CommonResponseDto.class),
        @ApiResponse(code = 404, message = "USER NOT FOUND"),
    })
    @PostMapping("/password")
    public ResponseEntity<CommonResponseDto> sendNewPassword(
        @RequestBody UserPasswordRequestDto userPasswordRequestDto) {

        try {
            userService.sendNewPassword(userPasswordRequestDto);
        } catch (CustomException e) {
            throw e;
        }

        return ResponseEntity.ok(CommonResponseDto.builder()
            .status(HttpStatus.OK.value()).
            code("Send Email Success").
            message("새로운 비밀번호를 전송하였습니다.").
            data(null).
            build());
    }

    @ApiOperation(value = "UpdatePassword", notes = "로그인 후 현재 비밀번호를 새로운 비밀번호로 변경")
    @ApiResponses(value = {
        @ApiResponse(code = 200, message = "", response = CommonResponseDto.class),
        @ApiResponse(code = 404, message = "USER NOT FOUND"),
    })
    @PutMapping("/password")
    public ResponseEntity<CommonResponseDto> updatePassword(
        @RequestHeader("Authorization") String accessToken,
        @RequestBody UserPasswordRequestDto userPasswordRequestDto) {
        try {
            userService.updatePassword(accessToken, userPasswordRequestDto);
        } catch (CustomException e) {
            throw e;
        }

        return ResponseEntity.ok(CommonResponseDto.builder()
            .status(HttpStatus.OK.value()).
            code("Update Password Success").
            message("비밀번호가 성공적으로 변경되었습니다.").
            data(null).
            build());

    }

}
