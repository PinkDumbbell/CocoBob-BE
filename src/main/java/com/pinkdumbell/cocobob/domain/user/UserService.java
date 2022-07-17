package com.pinkdumbell.cocobob.domain.user;

import com.pinkdumbell.cocobob.domain.user.dto.UserCreateRequestDto;
import com.pinkdumbell.cocobob.domain.user.dto.UserCreateResponseDto;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Optional;

@RequiredArgsConstructor
@Service
public class UserService {
    private final UserRepository userRepository;

    public UserCreateResponseDto signup(UserCreateRequestDto requestDto) {
        Optional<User> user = userRepository.findByEmail(requestDto.getEmail());

        if (user.isPresent()) {
            throw new RuntimeException("해당 이메일을 가진 사용자가 이미 존재합니다.");
        }

        return new UserCreateResponseDto(
                userRepository.save(User.builder()
                                .email(requestDto.getEmail())
                                .username(requestDto.getUsername())
                                .password(requestDto.getPassword())
                                .build()));
    }
}
