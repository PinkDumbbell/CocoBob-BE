package com.pinkdumbell.cocobob.domain.user;

import com.pinkdumbell.cocobob.domain.auth.Token;
import com.pinkdumbell.cocobob.domain.user.dto.UserCreateRequestDto;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;

@Getter
@NoArgsConstructor
@Table(name = "\"user\"")
@Entity
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "user_id")
    private Long id;

    private String username;

    private String email;

    private String password;
    @Enumerated(value = EnumType.STRING)
    private UserRole role;

    @OneToOne(mappedBy = "user", fetch = FetchType.LAZY)
    private Token refreshToken;

    private String token;

    @Builder
    public User(@NotBlank String username,
        @NotBlank String email,
        @NotBlank String password) {
        this.username = username;
        this.email = email;
        this.password = password;
        this.role = UserRole.USER; // 일반 회원 기본 권한 USER
    }

    public void updateRefreshToken(/*Token refreshToken*/ String refreshToken) {
//        this.refreshToken = refreshToken;
        this.token = refreshToken;
    }
}
