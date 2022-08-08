package com.pinkdumbell.cocobob.domain.pet;

import com.pinkdumbell.cocobob.config.MailConfig;
import com.pinkdumbell.cocobob.domain.pet.breed.Breed;
import com.pinkdumbell.cocobob.domain.pet.breed.BreedRepository;
import com.pinkdumbell.cocobob.domain.product.ProductSearchQueryDslImpl;
import com.pinkdumbell.cocobob.domain.user.User;
import com.pinkdumbell.cocobob.domain.user.UserRepository;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.util.Optional;
import org.springframework.boot.test.mock.mockito.MockBean;

@DataJpaTest
public class PetRepositoryTest {
    @Autowired
    PetRepository petRepository;
    @Autowired
    UserRepository userRepository;
    @Autowired
    BreedRepository breedRepository;
    @PersistenceContext
    EntityManager em;
    @MockBean
    ProductSearchQueryDslImpl productSearchQueryDsl;

    @Test
    @DisplayName("반려동물 아이디와 사용자 이메일을 통해 반려동물 상세정보를 조회할 수 있다.")
    void testFindByIdAndUserEmail() {
        User user = userRepository.save(
                User.builder()
                        .email("test@test.com")
                        .build());
        Breed breed = breedRepository.save(
                Breed.builder()
                        .name("진돗개")
                        .build());
        Pet pet = petRepository.save(
                Pet.builder()
                        .name("코코")
                        .breed(breed)
                        .user(user)
                        .build());

        em.flush();
        em.clear();

        Pet result = petRepository.findByIdAndUserEmail(
                pet.getId(), user.getEmail()).get();
        Assertions.assertThat(result.getUser().getEmail()).isEqualTo("test@test.com");
        Assertions.assertThat(result.getBreed().getName()).isEqualTo("진돗개");
    }
}
