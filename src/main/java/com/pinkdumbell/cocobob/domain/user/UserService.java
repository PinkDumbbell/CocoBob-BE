package com.pinkdumbell.cocobob.domain.user;

import com.pinkdumbell.cocobob.common.dto.EmailSendResultDto;
import com.pinkdumbell.cocobob.domain.auth.JwtTokenProvider;
import com.pinkdumbell.cocobob.domain.auth.Token;
import com.pinkdumbell.cocobob.domain.auth.TokenRepository;
import com.pinkdumbell.cocobob.domain.auth.dto.TokenRequestDto;
import com.pinkdumbell.cocobob.domain.auth.dto.TokenResponseDto;
import com.pinkdumbell.cocobob.common.EmailUtil;
import com.pinkdumbell.cocobob.domain.user.dto.EmailDuplicationCheckResponseDto;
import com.pinkdumbell.cocobob.domain.user.dto.UserCreateRequestDto;
import com.pinkdumbell.cocobob.domain.user.dto.UserCreateResponseDto;
import com.pinkdumbell.cocobob.domain.user.dto.UserEmailRequestDto;
import com.pinkdumbell.cocobob.domain.user.dto.UserLoginRequestDto;
import com.pinkdumbell.cocobob.domain.user.dto.UserLoginResponseDto;
import com.pinkdumbell.cocobob.domain.user.dto.UserPasswordRequestDto;
import com.pinkdumbell.cocobob.exception.CustomException;
import com.pinkdumbell.cocobob.exception.ErrorCode;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Random;

@RequiredArgsConstructor
@Service
public class UserService {

    private final UserRepository userRepository;

    private final TokenRepository tokenRepository;

    private final JwtTokenProvider jwtTokenProvider;

    private final BCryptPasswordEncoder bCryptPasswordEncoder;

    private final EmailUtil emailUtil;


    @Transactional
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

    @Transactional
    public UserLoginResponseDto login(UserLoginRequestDto requestDto) {

        User user = userRepository.findByEmail(requestDto.getEmail())
            .orElseThrow(() -> {
                throw new CustomException(ErrorCode.USER_NOT_FOUND);
            });

        if (!bCryptPasswordEncoder.matches(requestDto.getPassword(), user.getPassword())) {
            throw new CustomException(ErrorCode.INVALID_PASSWORD);
        }

        String newRefreshToken = jwtTokenProvider.createRefreshToken();

        if (user.getRefreshToken() == null) {
            Token newUserToken = tokenRepository.save(Token.builder()
                .value(newRefreshToken)
                .user(user).build());
            user.updateRefreshToken(newUserToken);
        } else {
            Token userRefreshToken = user.getRefreshToken();
            userRefreshToken.updateRefreshTokenValue(newRefreshToken);
            user.updateRefreshToken(userRefreshToken);
        }

        return new UserLoginResponseDto(user, jwtTokenProvider.createToken(requestDto.getEmail()),
            newRefreshToken);
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
    public TokenResponseDto reissue(TokenRequestDto requestDto) throws CustomException {
        String TOKEN_PREFIX = "Bearer ";
        String rawRefreshToken = requestDto.getRefreshToken().replace(TOKEN_PREFIX, "");

        try {
            jwtTokenProvider.validateTokenExpiration(rawRefreshToken);
        } catch (Exception e) {
            throw new CustomException(ErrorCode.INVALID_REFRESH_TOKEN);
        }

        User user = findUserByToken(requestDto.getAccessToken());

        if (!user.getRefreshToken().getValue().equals(rawRefreshToken)) {
            throw new CustomException(ErrorCode.INVALID_REFRESH_TOKEN);
        }

        String accessToken = jwtTokenProvider.createToken(user.getEmail());
        String refreshToken = jwtTokenProvider.createRefreshToken();

        Token newUserToken = tokenRepository.findById(user.getId())
            .orElseThrow(() -> {
                throw new CustomException(ErrorCode.INVALID_REFRESH_TOKEN);
            });

        newUserToken.updateRefreshTokenValue(refreshToken);

        user.updateRefreshToken(newUserToken);
        return new TokenResponseDto(accessToken, refreshToken);
    }

    @Transactional
    public User findUserByToken(String accessToken) {
        String TOKEN_PREFIX = "Bearer ";
        String rawAccessToken = accessToken.replace(TOKEN_PREFIX, "");
        Authentication auth = jwtTokenProvider.getAuthentication(rawAccessToken);
        UserDetails userDetails = (UserDetails) auth.getPrincipal();
        String email = userDetails.getUsername(); // email을 스프링 시큐리티 username으로 사용

        return userRepository.findByEmail(email).orElseThrow(() -> {
            throw new CustomException(ErrorCode.USER_NOT_FOUND);
        });
    }

    @Transactional
    public void logout(String rawAccessToken) {
        String TOKEN_PREFIX = "Bearer ";
        String accessToken = rawAccessToken.replace(TOKEN_PREFIX, "");
        Authentication auth = jwtTokenProvider.getAuthentication(accessToken);
        UserDetails userDetails = (UserDetails) auth.getPrincipal();
        String email = userDetails.getUsername(); // email을 스프링 시큐리티 username으로 사용

        User findUser = userRepository.findByEmail(email).orElseThrow(() -> {
            throw new CustomException(ErrorCode.INVALID_LOGOUT_REQUEST);
        });

        try {
            tokenRepository.delete(findUser.getRefreshToken());
        } catch (Exception e) {
            throw new CustomException(ErrorCode.INVALID_LOGOUT_REQUEST);
        }
        findUser.updateRefreshToken(null);
    }

    @Transactional
    public String sendNewPassword(UserEmailRequestDto userEmailRequestDto){

        User user = userRepository.findByEmail(userEmailRequestDto.getEmail())
                .orElseThrow(()->{
                    throw new CustomException(ErrorCode.USER_NOT_FOUND);
                });
        String capitalCaseLetters = "ABCDEFGHIJKLMNOPQRSTUVWXYZ";
        String lowerCaseLetters = "abcdefghijklmnopqrstuvwxyz";
        String specialCharacters = "!@#$";
        String numbers = "1234567890";
        String combinedChars = capitalCaseLetters + lowerCaseLetters + specialCharacters + numbers;
        Random random = new Random();
        String newPassword = "";


        for(int i = 0; i< 13 ; i++) {
            newPassword += combinedChars.charAt(random.nextInt(combinedChars.length()));
        }

        user.updatePassword(bCryptPasswordEncoder.encode(newPassword));

        //비밀번호 전송
        EmailSendResultDto emailSendResultDto = emailUtil.sendEmail(user.getEmail()
            , "[Petalog] 안녕하세요!" + user.getUsername() + "님 새로운 비밀번호 입니다."
            , newPassword);

        if(emailSendResultDto.getStatus() == HttpStatus.NOT_FOUND.value()){
            throw new CustomException(ErrorCode.USER_NOT_FOUND);
        }

        return newPassword;
        
    }
    @Transactional
    public void updatePassword(String accessToken, UserPasswordRequestDto userPasswordRequestDto) {
        User user = findUserByToken(accessToken);
        user.updatePassword(bCryptPasswordEncoder.encode(userPasswordRequestDto.getPassword()));
    }
}
