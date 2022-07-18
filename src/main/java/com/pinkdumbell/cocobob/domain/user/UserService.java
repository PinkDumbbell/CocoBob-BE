package com.pinkdumbell.cocobob.domain.user;

import com.pinkdumbell.cocobob.domain.user.dto.UserCreateRequestDto;
import com.pinkdumbell.cocobob.domain.user.dto.UserCreateResponseDto;
import com.pinkdumbell.cocobob.domain.user.dto.UserLoginRequestDto;
import com.pinkdumbell.cocobob.domain.user.dto.UserLoginResponseDto;
import com.pinkdumbell.cocobob.exception.CustomException;
import com.pinkdumbell.cocobob.exception.ErrorCode;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.Optional;

@RequiredArgsConstructor
@Service
public class UserService {
    private final UserRepository userRepository;

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
                                .password(requestDto.getPassword())
                                .build()));
    }

    public UserLoginResponseDto login(UserLoginRequestDto requestDto){

        User user = userRepository.findByEmail(requestDto.getEmail())
            .orElseThrow(() -> {
                throw new CustomException(ErrorCode.USER_NOT_FOUND);
            });
        return new UserLoginResponseDto(user);


    }
}
