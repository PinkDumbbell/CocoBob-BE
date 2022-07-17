package com.pinkdumbell.cocobob.domain.user;

import com.pinkdumbell.cocobob.domain.user.dto.UserCreateRequestDto;
import com.pinkdumbell.cocobob.domain.user.dto.UserCreateResponseDto;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@RequiredArgsConstructor
@Service
public class UserService {
    private final UserRepository userRepository;

    public UserCreateResponseDto signup(UserCreateRequestDto requestDto) {
        return new UserCreateResponseDto(userRepository.save(userRepository.findByEmail(requestDto.getEmail())
                .orElseThrow(() -> new RuntimeException("해당 이메일을 가진 사용자가 존재합니다."))));
    }
}
