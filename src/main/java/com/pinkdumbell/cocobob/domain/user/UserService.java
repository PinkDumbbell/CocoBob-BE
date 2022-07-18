package com.pinkdumbell.cocobob.domain.user;

import com.pinkdumbell.cocobob.domain.auth.JwtTokenProvider;
import com.pinkdumbell.cocobob.domain.auth.Token;
import com.pinkdumbell.cocobob.domain.auth.dto.TokenRequestDto;
import com.pinkdumbell.cocobob.domain.auth.dto.TokenResponseDto;
import com.pinkdumbell.cocobob.domain.user.dto.EmailDuplicationCheckResponseDto;
import com.pinkdumbell.cocobob.domain.user.dto.UserCreateRequestDto;
import com.pinkdumbell.cocobob.domain.user.dto.UserCreateResponseDto;
import com.pinkdumbell.cocobob.domain.user.dto.UserLoginRequestDto;
import com.pinkdumbell.cocobob.domain.user.dto.UserLoginResponseDto;
import com.pinkdumbell.cocobob.exception.CustomException;
import com.pinkdumbell.cocobob.exception.ErrorCode;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@RequiredArgsConstructor
@Transactional(readOnly = true)
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

        String refreshToken = jwtTokenProvider.createRefreshToken();

        Token newUserToken = Token.builder()
                .id(user.getId())
                .refreshToken(refreshToken)
                .user(user)
                .build();

        user.updateRefreshToken(newUserToken);
        System.out.println("login user.getRefreshToken "+user.getRefreshToken());
        return new UserLoginResponseDto(user, jwtTokenProvider.createToken(requestDto.getEmail()),refreshToken);
    }

    @Transactional(readOnly = true)
    public EmailDuplicationCheckResponseDto checkEmailDuplicated(String email) {
        userRepository.findByEmail(email)
            .ifPresent(userWithDuplicatedEmail -> {
                throw new CustomException(ErrorCode.DUPLICATED_EMAIL);
            });
        return new EmailDuplicationCheckResponseDto("해당 이메일은 사용할 수 있습니다.");

    }

    @Transactional
    public TokenResponseDto reIssue(TokenRequestDto requestDto) {
        if (!jwtTokenProvider.validateTokenExpiration(requestDto.getRefreshToken()))
            throw new CustomException(ErrorCode.INVALID_REFRESH_TOKEN);

        User user = findMemberByToken(requestDto);

        if (!user.getRefreshToken().getValue().equals(requestDto.getRefreshToken()))
            throw new CustomException(ErrorCode.INVALID_REFRESH_TOKEN);

        String accessToken = jwtTokenProvider.createToken(user.getEmail());
        String refreshToken = jwtTokenProvider.createRefreshToken();

        Token newUserToken = Token.builder()
                .id(user.getId())
                .refreshToken(refreshToken)
                .user(user)
                .build();


        user.updateRefreshToken(newUserToken);

        System.out.println("reIssue user.getRefreshToken "+user.getRefreshToken());
        return new TokenResponseDto(accessToken, refreshToken);
    }

    public User findMemberByToken(TokenRequestDto requestDto) {
        Authentication auth = jwtTokenProvider.getAuthentication(requestDto.getAccessToken());
        UserDetails userDetails = (UserDetails) auth.getPrincipal();
        String username = userDetails.getUsername();

        return userRepository.findByEmail(username).orElseThrow(() -> {
            throw new CustomException(ErrorCode.USER_NOT_FOUND);
        });
    }
}
