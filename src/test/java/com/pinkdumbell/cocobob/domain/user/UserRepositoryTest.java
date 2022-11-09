package com.pinkdumbell.cocobob.domain.user;

import com.pinkdumbell.cocobob.config.JpaAuditingConfig;
import com.pinkdumbell.cocobob.domain.pet.Pet;
import com.pinkdumbell.cocobob.domain.pet.PetRepository;
import com.pinkdumbell.cocobob.domain.pet.breed.Breed;
import com.pinkdumbell.cocobob.domain.pet.breed.BreedRepository;
import com.pinkdumbell.cocobob.domain.pet.image.PetImage;
import com.pinkdumbell.cocobob.domain.pet.image.PetImageRepository;
import com.pinkdumbell.cocobob.domain.product.ProductSearchQueryDslImpl;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Import;
import org.assertj.core.api.Assertions;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@DataJpaTest
@Import(JpaAuditingConfig.class)
public class UserRepositoryTest {

    @Autowired
    UserRepository userRepository;
    @Autowired
    PetRepository petRepository;
    @Autowired
    PetImageRepository petImageRepository;
    @Autowired
    BreedRepository breedRepository;
    @PersistenceContext
    EntityManager em;
    @MockBean
    ProductSearchQueryDslImpl productSearchQueryDsl;

    @Test
    @DisplayName("사용자 생성 시 생성시간 등록 여부를 테스트한다.")
    void testAuditing() {
        LocalDateTime now = LocalDateTime.now();
        now = now.minusNanos(1L); // 1나노세컨드만 뒤로
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

    @Test
    @DisplayName("사용자 이메일을 통해 등록한 반려동물을 불러오는 메서드 테스트")
    void testFindAllByUserIdWithBreed() {
        String email = "test@test.com";
        User user = userRepository.save(User.builder().email(email).build());
        Breed breed = breedRepository.save(Breed.builder()
            .name("진돗개")
            .build());
        petRepository.save(Pet.builder()
            .user(user)
            .breed(breed)
            .name("코코")
            .build());
        petRepository.save(Pet.builder()
            .user(user)
            .breed(breed)
            .name("키키")
            .build());

        em.flush();
        em.clear();

        Optional<User> result = userRepository.findUserByEmailWithPetDetail(user.getEmail());
        List<Pet> pets = result.get().getPets();
        Assertions.assertThat(pets.size()).isEqualTo(2);
        Assertions.assertThat(pets.stream().map(Pet::getName).collect(Collectors.toList()))
            .isEqualTo(Arrays.asList("코코", "키키"));
        Assertions.assertThat(
                pets.stream().map(Pet::getBreed).map(Breed::getName).collect(Collectors.toList()))
            .isEqualTo(Arrays.asList("진돗개", "진돗개"));
    }
}
