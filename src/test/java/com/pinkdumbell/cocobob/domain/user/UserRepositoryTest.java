package com.pinkdumbell.cocobob.domain.user;

import com.pinkdumbell.cocobob.config.JpaAuditingConfig;
import com.pinkdumbell.cocobob.domain.pet.Pet;
import com.pinkdumbell.cocobob.domain.pet.PetRepository;
import com.pinkdumbell.cocobob.domain.pet.image.PetImage;
import com.pinkdumbell.cocobob.domain.pet.image.PetImageRepository;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.context.annotation.Import;
import org.assertj.core.api.Assertions;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.time.LocalDateTime;
import java.util.Optional;

@DataJpaTest
@Import(JpaAuditingConfig.class)
public class UserRepositoryTest {
    @Autowired
    UserRepository userRepository;
    @Autowired
    PetRepository petRepository;
    @Autowired
    PetImageRepository petImageRepository;
    @PersistenceContext
    EntityManager em;

    @Test
    @DisplayName("사용자 생성 시 생성시간 등록 여부를 테스트한다.")
    void testAuditing() {
        LocalDateTime now = LocalDateTime.now();
        User user = userRepository.save(User.builder().build());

        Assertions.assertThat(user.getCreatedAt()).isAfter(now);
        Assertions.assertThat(user.getUpdatedAt()).isAfter(now);
    }

    @Test
    @DisplayName("사용자 정보를 가져올 때 반려동물 정보를 한 번의 쿼리로 가져오는지 테스트한다.")
    void testFindUserByEmailWithSimplePetInfo() {
        String email = "test@test.com";
        String thumbnailPath = "thumbnailPath";
        String name = "코코";

        User user = userRepository.save(User.builder()
                .email(email)
                .build());

        Pet pet1 = Pet.builder()
                .user(user)
                .name(name)
                .build();
        pet1.setThumbnailPath(thumbnailPath);

        Pet pet2 = Pet.builder()
                .user(user)
                .name("키키")
                .build();
        pet2.setThumbnailPath(thumbnailPath);

        PetImage petImage = PetImage.builder()
                .pet(pet1)
                .path("imagePath")
                .build();

        petRepository.save(pet1);
        petRepository.save(pet2);
        petImageRepository.save(petImage);

        em.flush();
        em.clear();

        Optional<User> result = userRepository.findUserByEmailWithPet(email);
        Assertions.assertThat(result.get().getEmail()).isEqualTo(email);
        Assertions.assertThat(result.get().getPets().size()).isEqualTo(2);
        Assertions.assertThat(result.get().getPets().get(0).getName()).isEqualTo(name);
    }
}
