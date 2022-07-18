package com.pinkdumbell.cocobob.domain.auth;

import com.pinkdumbell.cocobob.domain.user.User;
import lombok.Getter;

import javax.persistence.*;


@Getter
@Entity
public class Token {

    @Id
    @JoinColumn(name = "user_id")
    private Long id;

    @Column(name = "refresh_token", length = 500)
    private String refreshToken;

    @OneToOne
    @MapsId
    @JoinColumn(name = "user_id")
    private User user;
}
