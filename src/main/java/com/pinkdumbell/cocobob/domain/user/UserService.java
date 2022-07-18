package com.pinkdumbell.cocobob.domain.user;

import com.pinkdumbell.cocobob.domain.auth.JwtTokenProvider;
import com.pinkdumbell.cocobob.domain.user.dto.EmailDuplicationCheckResponseDto;
import com.pinkdumbell.cocobob.domain.user.dto.UserCreateRequestDto;
import com.pinkdumbell.cocobob.domain.user.dto.UserCreateResponseDto;
import com.pinkdumbell.cocobob.domain.user.dto.UserLoginRequestDto;
import com.pinkdumbell.cocobob.domain.user.dto.UserLoginResponseDto;
import com.pinkdumbell.cocobob.exception.CustomException;
import com.pinkdumbell.cocobob.exception.ErrorCode;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@RequiredArgsConstructor
@Transactional
@Service
public class UserService {

    private final UserRepository userRepository;
    private final JwtTokenProvider jwtTokenProvider;

    private final BCryptPasswordEncoder bCryptPasswordEncoder;


    public UserCreateResponseDto signup(UserCreateRequestDto requestDto) {
        userRepository.findByEmail(requestDto.getEmail())
            .ifPresent(userWithDuplicatedEmail -> {
                throw new CustomException(ErrorCode.DUPLICATED_EMAIL);
            });

        return new UserCreateResponseDto(
            userRepository.save(User.builder()
                .email(requestDto.getEmail())
                .username(requestDto.getUsername())
                .password(bCryptPasswordEncoder.encode(requestDto.getPassword()))
                .build()));
    }

    public UserLoginResponseDto login(UserLoginRequestDto requestDto) {

        User user = userRepository.findByEmail(requestDto.getEmail())
            .orElseThrow(() -> {
                throw new CustomException(ErrorCode.USER_NOT_FOUND);
            });
        if (!bCryptPasswordEncoder.matches(requestDto.getPassword(), user.getPassword())) {
            throw new CustomException(ErrorCode.INVALID_PASSWORD);
        }
        return new UserLoginResponseDto(user, jwtTokenProvider.createToken(requestDto.getEmail()));
    }

    @Transactional(readOnly = true)
    public EmailDuplicationCheckResponseDto checkEmailDuplicated(String email) {
        userRepository.findByEmail(email)
            .ifPresent(userWithDuplicatedEmail -> {
                throw new CustomException(ErrorCode.DUPLICATED_EMAIL);
            });
        return new EmailDuplicationCheckResponseDto("해당 이메일은 사용할 수 있습니다.");

    }
}
