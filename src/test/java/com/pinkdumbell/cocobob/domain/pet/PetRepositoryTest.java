package com.pinkdumbell.cocobob.domain.pet;

import com.pinkdumbell.cocobob.config.MailConfig;
import com.pinkdumbell.cocobob.domain.pet.breed.Breed;
import com.pinkdumbell.cocobob.domain.pet.breed.BreedRepository;
import com.pinkdumbell.cocobob.domain.product.ProductSearchQueryDslImpl;
import com.pinkdumbell.cocobob.domain.user.User;
import com.pinkdumbell.cocobob.domain.user.UserRepository;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
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

    User user;
    Breed breed;
    Pet pet;
    @BeforeEach
    void setup() {
        user = userRepository.save(
                User.builder()
                        .email("test@test.com")
                        .build());
        breed = breedRepository.save(
                Breed.builder()
                        .name("진돗개")
                        .build());
        pet = petRepository.save(
                Pet.builder()
                        .name("코코")
                        .breed(breed)
                        .user(user)
                        .build());
    }

    @Test
    @DisplayName("반려동물 아이디와 사용자 이메일을 통해 반려동물 상세정보를 조회할 수 있다.")
    void testFindByIdAndUserEmail() {
        em.flush();
        em.clear();

        Pet result = petRepository.findByIdAndUserEmail(
                pet.getId(), user.getEmail()).get();
        Assertions.assertThat(result.getUser().getEmail()).isEqualTo("test@test.com");
        Assertions.assertThat(result.getBreed().getName()).isEqualTo("진돗개");
    }

    @Test
    @DisplayName("반려동물 삭제 테스트")
    void testDeletePet() {
        petRepository.delete(pet);

        em.flush();
        em.clear();

        Assertions.assertThat(petRepository.findById(pet.getId()).isEmpty()).isEqualTo(true);
    }

    @Test
    @DisplayName("반려동물을 삭제한 후 사용자를 조회할 때 반려동물이 조회되지 않는 것을 테스트한다.")
    void testPetNotToBeRetrieved() {
        petRepository.delete(pet);

        em.flush();
        em.clear();

        Assertions.assertThat(
                userRepository.findUserByEmailWithPet(user.getEmail())
                        .get().getPets().size()
        ).isEqualTo(0);
    }
}
