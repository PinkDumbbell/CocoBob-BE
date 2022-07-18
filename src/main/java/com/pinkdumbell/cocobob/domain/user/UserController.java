package com.pinkdumbell.cocobob.domain.user;

import com.pinkdumbell.cocobob.domain.user.dto.UserCreateRequestDto;
import com.pinkdumbell.cocobob.domain.user.dto.UserCreateResponseDto;
import com.pinkdumbell.cocobob.domain.user.dto.UserLoginRequestDto;
import com.pinkdumbell.cocobob.domain.user.dto.UserLoginResponseDto;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;

@RequestMapping("/v1/users")
@RequiredArgsConstructor
@RestController
public class UserController {
    private final UserService userService;

    @PostMapping("/new")
    public ResponseEntity<UserCreateResponseDto> signup(@RequestBody @Valid UserCreateRequestDto requestDto) {
        return ResponseEntity.ok(userService.signup(requestDto));
    }

    @PostMapping("")
    public ResponseEntity<UserLoginResponseDto> Login(@RequestBody UserLoginRequestDto requestDto){
        UserLoginResponseDto responseDto = userService.login(requestDto);
        return ResponseEntity.ok(responseDto);
    }
}
