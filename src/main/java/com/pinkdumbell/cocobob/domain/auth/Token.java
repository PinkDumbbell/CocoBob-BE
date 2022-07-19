package com.pinkdumbell.cocobob.domain.auth;

import com.pinkdumbell.cocobob.domain.user.User;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import lombok.Setter;


@Getter
@Setter
@NoArgsConstructor
@Entity
public class Token {

    @Id
    @JoinColumn(name = "user_id")
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
        this.value = value;
        this.user = user;
    }
}
