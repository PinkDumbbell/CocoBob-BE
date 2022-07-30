package com.pinkdumbell.cocobob.domain.user;

import com.pinkdumbell.cocobob.domain.auth.Token;
import com.pinkdumbell.cocobob.domain.pet.Pet;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import java.util.ArrayList;
import java.util.List;

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
    private AccountType accountType;

    @OneToOne
    @JoinColumn(name = "representative_pet_id")
    private Pet representativePet;
    @Enumerated(value = EnumType.STRING)
    private UserRole role;

    @OneToOne(mappedBy = "user", fetch = FetchType.LAZY)
    private Token refreshToken;

    @OneToMany(mappedBy = "user", fetch = FetchType.LAZY)
    private List<Pet> pets = new ArrayList<>();

    @Builder
    public User(@NotBlank String username,
        @NotBlank String email,
        @NotBlank String password) {
        this.username = username;
        this.email = email;
        this.password = password;
        this.role = UserRole.USER; // 일반 회원 기본 권한 USER
    }

    public void updateRefreshToken(Token refreshToken) {
        this.refreshToken = refreshToken;
    }

    public void updatePassword(String newPassword){
        this.password = newPassword;
    }
}
