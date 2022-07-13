package com.pinkdumbell.cocobob.domain.auth;

import com.pinkdumbell.cocobob.domain.user.User;
import lombok.Getter;

import javax.persistence.*;


@Getter
@Entity
public class Token {
    @Id
    @OneToOne
    @JoinColumn(name = "user_id")
    private User user;
    @Column(name = "refresh_token")
    private String refreshToken;
}
