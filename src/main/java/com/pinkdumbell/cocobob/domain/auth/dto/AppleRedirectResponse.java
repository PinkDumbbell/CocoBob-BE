package com.pinkdumbell.cocobob.domain.auth.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Setter
@Getter
@NoArgsConstructor
public class AppleRedirectResponse {
    private String code;
    private UserInfoFromApple user;

    @Getter
    @Setter
    @NoArgsConstructor
    public static class UserInfoFromApple {

        private Name name;
        private String email;

        @Getter
        @Setter
        @NoArgsConstructor
        public static class Name {
            private String firstName;
            private String middleName;
            private String lastName;
        }
    }
}
