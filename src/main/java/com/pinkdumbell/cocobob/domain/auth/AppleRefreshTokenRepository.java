package com.pinkdumbell.cocobob.domain.auth;

import org.springframework.data.jpa.repository.JpaRepository;

public interface AppleRefreshTokenRepository extends JpaRepository<AppleRefreshToken, Long> {
}
