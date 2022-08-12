package com.pinkdumbell.cocobob.domain.auth.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.List;

@Getter
@AllArgsConstructor
public class ApplePublicKeysResponse {
    private final List<Key> keys;

    @Getter
    @AllArgsConstructor
    public static class Key {
        private final String kty;
        private final String kid;
        private final String use;
        private final String alg;
        private final String n;
        private final String e;
    }
}
