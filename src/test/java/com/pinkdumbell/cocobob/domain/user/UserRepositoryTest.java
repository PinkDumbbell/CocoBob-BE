package com.pinkdumbell.cocobob.domain.user;

import com.pinkdumbell.cocobob.config.JpaAuditingConfig;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.context.annotation.Import;
import org.assertj.core.api.Assertions;

import java.time.LocalDateTime;

@DataJpaTest
@Import(JpaAuditingConfig.class)
public class UserRepositoryTest {
    @Autowired
    UserRepository userRepository;

    @Test
    @DisplayName("사용자 생성 시 생성시간 등록 여부를 테스트한다.")
    void testAuditing() {
        LocalDateTime now = LocalDateTime.now();
        User user = userRepository.save(User.builder().build());

        Assertions.assertThat(user.getCreatedAt()).isAfter(now);
        Assertions.assertThat(user.getUpdatedAt()).isAfter(now);
    }
}
