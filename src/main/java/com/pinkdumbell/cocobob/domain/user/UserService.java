package com.pinkdumbell.cocobob.domain.user;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.pinkdumbell.cocobob.common.dto.EmailSendResultDto;
import com.pinkdumbell.cocobob.domain.auth.*;
import com.pinkdumbell.cocobob.domain.auth.dto.*;
import com.pinkdumbell.cocobob.common.EmailUtil;
import com.pinkdumbell.cocobob.domain.user.dto.EmailDuplicationCheckResponseDto;
import com.pinkdumbell.cocobob.domain.user.dto.LoginUserInfo;
import com.pinkdumbell.cocobob.domain.user.dto.UserCreateRequestDto;
import com.pinkdumbell.cocobob.domain.user.dto.UserCreateResponseDto;
import com.pinkdumbell.cocobob.domain.user.dto.UserEmailRequestDto;
import com.pinkdumbell.cocobob.domain.user.dto.UserGetResponseDto;
import com.pinkdumbell.cocobob.domain.user.dto.UserLoginRequestDto;
import com.pinkdumbell.cocobob.domain.user.dto.UserLoginResponseDto;
import com.pinkdumbell.cocobob.domain.user.dto.UserPasswordRequestDto;
import com.pinkdumbell.cocobob.exception.CustomException;
import com.pinkdumbell.cocobob.exception.ErrorCode;
import java.util.Map;
import lombok.RequiredArgsConstructor;
import org.json.simple.JSONObject;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;

import java.util.Optional;
import java.util.Random;

@RequiredArgsConstructor
@Service
public class UserService {

    private final UserRepository userRepository;
    private final TokenRepository tokenRepository;
    private final JwtTokenProvider jwtTokenProvider;
    private final BCryptPasswordEncoder bCryptPasswordEncoder;
    private final EmailUtil emailUtil;
    private final GoogleOauthInfo googleOauthInfo;
    private final KakaoOauthInfo kakaoOauthInfo;
    private final AppleUtil appleUtil;


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

    @Transactional
    public void withdraw(LoginUserInfo loginUserInfo) {
        User user = findUserByEmail(loginUserInfo.getEmail());
        Optional<AppleRefreshToken> appleRefreshToken = appleUtil.getAppleRefreshToken(user.getId());
        appleRefreshToken.ifPresent(appleUtil::revoke);
    }

    @Transactional(propagation = Propagation.MANDATORY)
    public User findUserByEmail(String email) {
        return userRepository.findByEmail(email)
                .orElseThrow(() -> {
                    throw new CustomException(ErrorCode.USER_NOT_FOUND);
                });
    }

    @Transactional
    public UserLoginResponseDto socialLogin(User user) {

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

        return new UserLoginResponseDto(user, jwtTokenProvider.createToken(user.getEmail()),
            newRefreshToken);
    }

    @Transactional
    public UserLoginResponseDto googleLogin(String code) {

        JSONObject userInfoFromGoogle = getUserInfoFromGoogle(code);

        String username = (String) userInfoFromGoogle.get("name");
        String email = (String) userInfoFromGoogle.get("email");

        Optional<User> foundUser = userRepository.findByEmail(email);

        if (foundUser.isEmpty()) {
            userRepository.save(User.builder()
                .username(username)
                .email(email)
                .accountType(AccountType.GOOGLE)
                .build());
        }

        return socialLogin(findUserByEmail(email));
    }

    private JSONObject getUserInfoFromGoogle(String code) {
        RestTemplate restTemplate = new RestTemplate();

        GoogleOAuthRequest googleOAuthRequest = GoogleOAuthRequest.builder()
            .clientId(googleOauthInfo.getGoogleClientId())
            .clientSecret(googleOauthInfo.getGoogleClientSecret())
            .code(code)
            .redirectUri(googleOauthInfo.getGoogleRedirectUrl())
            .grantType("authorization_code")
            .build();

        ResponseEntity<JSONObject> postResponse = restTemplate.postForEntity(
            googleOauthInfo.getGoogleAuthUrl() + "/token", googleOAuthRequest, JSONObject.class);
        String requestUrl = UriComponentsBuilder.fromHttpUrl(
                googleOauthInfo.getGoogleAuthUrl() + "/tokeninfo")
            .queryParam("id_token", postResponse.getBody().get("id_token")).toUriString();
        return restTemplate.getForObject(requestUrl, JSONObject.class);
    }

    @Transactional
    public UserLoginResponseDto appleLogin(AppleRedirectResponse body) {

        if (body.getUser() != null) {
            try {
                ObjectMapper objectMapper = new ObjectMapper();
                UserInfoFromApple userInfoFromApple = objectMapper.readValue(body.getUser(), UserInfoFromApple.class);
                UserInfoFromApple.Name name = userInfoFromApple.getName();
                Optional<User> foundUser = userRepository.findByEmail(userInfoFromApple.getEmail());
                if (foundUser.isEmpty()) {
                    userRepository.save(
                            User.builder()
                                    .username(name.getMiddleName() == null ?
                                            name.getLastName() + name.getFirstName():
                                            name.getLastName() + name.getMiddleName() + name.getFirstName() )
                                    .email(userInfoFromApple.getEmail())
                                    .accountType(AccountType.APPLE)
                                    .build());
                }
            } catch (JsonProcessingException e) {
                throw new RuntimeException(e);
            }
        }

        DecodedIdTokenAndRefreshTokenDto userInfoAndRefreshToken = appleUtil.getEmailFromIdToken(body.getCode());
        try {
            User user = findUserByEmail(userInfoAndRefreshToken.getDecodedIdToken().get("email").toString());
            appleUtil.saveOrUpdateRefreshToken(user, userInfoAndRefreshToken.getRefreshToken());
            return socialLogin(user);
        } catch (NullPointerException e) {
            throw new RuntimeException("IdToken에서 이메일을 찾을 수 없습니다.");
        }
    }

    @Transactional
    public UserLoginResponseDto kakaoLogin(String code) {

        JSONObject userInfoFromKakao = getUserInfoFromKakao(code);

        JSONObject kakaoAccount = new JSONObject((Map) userInfoFromKakao.get("kakao_account"));
        JSONObject profile = new JSONObject((Map) kakaoAccount.get("profile"));

        String username = profile.get("nickname").toString();
        String email = kakaoAccount.get("email").toString();

        Optional<User> foundUser = userRepository.findByEmail(email);

        if (foundUser.isEmpty()) {
            userRepository.save(User.builder()
                .username(username)
                .email(email)
                .accountType(AccountType.KAKAO)
                .build());
        }

        return socialLogin(findUserByEmail(email));
    }

    private JSONObject getUserInfoFromKakao(String code) {

        RestTemplate restTemplate = new RestTemplate();

        HttpHeaders headers = new HttpHeaders();
        headers.add("Content-type", "application/x-www-form-urlencoded;charset=utf-8");
        MultiValueMap<String, Object> parameters = new LinkedMultiValueMap<String, Object>();
        parameters.set("grant_type", "authorization_code");
        parameters.set("client_id", kakaoOauthInfo.getKakaoClientId());
        parameters.set("redirect_uri", kakaoOauthInfo.getKakaoRedirectUrl());
        parameters.set("code", code);

        HttpEntity<MultiValueMap<String, Object>> kakaoTokenRequest = new HttpEntity<>(parameters,
            headers);

        // kakao accessToken 발급
        ResponseEntity<JSONObject> postResponse = restTemplate.postForEntity(
            kakaoOauthInfo.getKakaoTokenUrl(),
            kakaoTokenRequest,
            JSONObject.class);

        // accessToken을 이용한 사용자 정보 생성
        headers.set("Authorization", "bearer " + postResponse.getBody().get("access_token"));
        HttpEntity<MultiValueMap<String, String>> kakaoProfileRequest =
            new HttpEntity<>(headers);

        return restTemplate.postForObject(kakaoOauthInfo.getKakaoProfileUrl(), kakaoProfileRequest,
            JSONObject.class);
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
            throw new CustomException(ErrorCode.EXPIRED_REFRESH_TOKEN);
        }

        User user = findUserByToken(requestDto.getAccessToken());

        if (!user.getRefreshToken().getValue().equals(rawRefreshToken)) {
            throw new CustomException(ErrorCode.INVALID_ACCESS_AFTER_LOGOUT);
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
    public String sendNewPassword(UserEmailRequestDto userEmailRequestDto) {

        User user = userRepository.findByEmail(userEmailRequestDto.getEmail())
            .orElseThrow(() -> {
                throw new CustomException(ErrorCode.USER_NOT_FOUND);
            });
        String capitalCaseLetters = "ABCDEFGHIJKLMNOPQRSTUVWXYZ";
        String lowerCaseLetters = "abcdefghijklmnopqrstuvwxyz";
        String specialCharacters = "!@#$";
        String numbers = "1234567890";
        String combinedChars = capitalCaseLetters + lowerCaseLetters + specialCharacters + numbers;
        Random random = new Random();
        String newPassword = "";

        for (int i = 0; i < 13; i++) {
            newPassword += combinedChars.charAt(random.nextInt(combinedChars.length()));
        }

        user.updatePassword(bCryptPasswordEncoder.encode(newPassword));

        //비밀번호 전송
        EmailSendResultDto emailSendResultDto = emailUtil.sendEmail(user.getEmail()
            , "[Petalog] 안녕하세요!" + user.getUsername() + "님 새로운 비밀번호 입니다."
            , newPassword);

        if (emailSendResultDto.getStatus() == HttpStatus.NOT_FOUND.value()) {
            throw new CustomException(ErrorCode.USER_NOT_FOUND);
        }

        return newPassword;

    }

    @Transactional
    public void updatePassword(String accessToken, UserPasswordRequestDto userPasswordRequestDto) {
        User user = findUserByToken(accessToken);
        user.updatePassword(bCryptPasswordEncoder.encode(userPasswordRequestDto.getPassword()));
    }

    @Transactional(readOnly = true)
    public UserGetResponseDto getUserInfo(LoginUserInfo loginUserInfo) {
        User user = userRepository.findUserByEmailWithPet(loginUserInfo.getEmail())
            .orElseThrow(() -> new CustomException(ErrorCode.USER_NOT_FOUND));
        return new UserGetResponseDto(user);
    }
}
