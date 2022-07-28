package com.pinkdumbell.cocobob.domain.user;

import com.pinkdumbell.cocobob.domain.auth.dto.TokenRequestDto;
import com.pinkdumbell.cocobob.domain.auth.dto.TokenResponseDto;
import com.pinkdumbell.cocobob.domain.common.dto.ResponseDto;
import com.pinkdumbell.cocobob.domain.user.dto.*;
import com.pinkdumbell.cocobob.exception.CustomException;
import io.jsonwebtoken.JwtException;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

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

    @ApiOperation(value = "Login", notes = "서비스 자체 로그인")
    @ApiResponses(value = {
        @ApiResponse(code = 200, message = "", response = UserLoginResponseDto.class),
        @ApiResponse(code = 404, message = "USER_NOT_FOUND"),
        @ApiResponse(code = 403, message = "INVALID_PASSWORD")
    })
    @PostMapping("")
    public ResponseEntity<UserLoginResponseDto> login(@RequestBody UserLoginRequestDto requestDto) {
        return ResponseEntity.ok(userService.login(requestDto));
    }

    @ApiOperation(value = "Reissue", notes = "refresh Token을 통한 Token 재발행")
    @ApiResponses(value = {
        @ApiResponse(code = 200, message = "", response = TokenResponseDto.class),
        @ApiResponse(code = 401, message = "UNAUTHORIZED"),
    })
    @GetMapping("/token")
    public ResponseEntity<TokenResponseDto> reissue(
        @RequestHeader("accessToken") String accessToken,
        @RequestHeader("refreshToken") String refreshToken) {
        TokenRequestDto tokenRequestDto = TokenRequestDto.builder()
            .accessToken(accessToken)
            .refreshToken(refreshToken)
            .build();

        try {
            return ResponseEntity.ok(userService.reissue(tokenRequestDto));
        } catch (CustomException | JwtException e) {
            throw e;
        }
    }

    @ApiOperation(value = "Logout", notes = "로그아웃")
    @ApiResponses(value = {
        @ApiResponse(code = 200, message = "로그아웃 처리완료 되었습니다.", response = ResponseDto.class),
        @ApiResponse(code = 401, message = "UNAUTHORIZED"),
    })
    @DeleteMapping("")
    public ResponseEntity<ResponseDto> logout(@RequestHeader("Authorization") String accessToken) {

        try {
            userService.logout(accessToken);
        } catch (CustomException e) {
            throw e;
        }
       

        return ResponseEntity.ok(ResponseDto.builder().status(200).code("Logout Success").message("로그아웃 처리완료 되었습니다.").build());
    }

    @ApiOperation(value = "SendNewPassword", notes = "비밀번호 분실시 새로운 비밀번호를 발급 받습니다.")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "", response = ResponseDto.class),
            @ApiResponse(code = 404, message = "USER NOT FOUND"),
    })
    @PostMapping("/password")
    public ResponseEntity<ResponseDto> sendNewPassword(@RequestBody UserPasswordRequestDto userPasswordRequestDto){
        System.out.println(userPasswordRequestDto.getEmail());
        try {
            userService.sendNewPassword(userPasswordRequestDto);
        } catch (CustomException e)
        {
            throw e;
        }

        return ResponseEntity.ok(ResponseDto.builder().status(200).code("Find Password Success").message("사용자 이메일로 새로운 password를 보냈습니다.").build());
    }

    @PutMapping("/password")
    public ResponseEntity<ResponseDto> updatePassword(@RequestHeader("Authorization") String accessToken,@RequestBody UserPasswordRequestDto userPasswordRequestDto){
        try {
            userService.UpdatePassword(accessToken,userPasswordRequestDto);
        } catch (CustomException e)
        {
            throw e;
        }

        return ResponseEntity.ok(ResponseDto.builder().status(200).code("Change Password Success").message("비밀번호를 성공적으로 변경하였습니다.").build());
    }

}
