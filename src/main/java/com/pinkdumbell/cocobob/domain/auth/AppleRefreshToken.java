package com.pinkdumbell.cocobob.domain.auth;

import com.pinkdumbell.cocobob.domain.user.User;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;

import javax.persistence.*;

@Getter
@NoArgsConstructor
@Builder
@AllArgsConstructor
@Entity
public class AppleRefreshToken {

    @Id
    @JoinColumn(name = "user_id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column
    private String value;

    @OneToOne
    @MapsId
    @JoinColumn(name = "user_id")
    private User user;

    void update(String value) {
        this.value = value;
    }

}
