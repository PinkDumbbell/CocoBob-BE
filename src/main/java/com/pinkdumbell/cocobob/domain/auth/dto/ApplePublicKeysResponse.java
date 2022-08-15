package com.pinkdumbell.cocobob.domain.auth.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.List;

@Getter
@AllArgsConstructor
@NoArgsConstructor
public class ApplePublicKeysResponse {
    private List<Key> keys;

    @Getter
    @AllArgsConstructor
    @NoArgsConstructor
    public static class Key {
        private String kty;
        private String kid;
        private String use;
        private String alg;
        private String n;
        private String e;
    }
}
