package com.pinkdumbell.cocobob.domain.auth;

import com.pinkdumbell.cocobob.domain.auth.dto.AppleRedirectResponse;
import lombok.*;

@Getter
@AllArgsConstructor
@Builder
public class UserInfoFromApple {
    private Name name;
    private String email;

    @Getter
    @AllArgsConstructor
    @Builder
    public static class Name {
        private String firstName;
        private String middleName;
        private String lastName;
    }
}
