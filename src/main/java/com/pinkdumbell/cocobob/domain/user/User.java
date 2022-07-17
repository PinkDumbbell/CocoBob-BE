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
@Entity
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "user_id")
    private Long id;

    private String username;

    private String email;

    private String password;

    @OneToOne(mappedBy = "user", fetch = FetchType.LAZY)
    private Token token;

    @Builder
    public User(@NotBlank String username,
                @NotBlank String email,
                @NotBlank String password) {
        this.username = username;
        this.email = email;
        this.password = password;
    }
}
