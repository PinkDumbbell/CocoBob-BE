package com.pinkdumbell.cocobob.domain.auth;

import com.pinkdumbell.cocobob.domain.user.User;
import com.pinkdumbell.cocobob.domain.user.UserRole;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;


@Getter
@NoArgsConstructor
@Entity
public class Token {

    @Id
//    @JoinColumn(name = "user_id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "refresh_token", length = 500)
    private String value; //Token 값

    @OneToOne
    @MapsId
    @JoinColumn(name = "user_id")
    private User user;

    @Builder
    public Token(Long id, String value, User user) {
        this.id = id;
        this.value = value;
        this.user = user;
    }
}
