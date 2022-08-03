package com.pinkdumbell.cocobob.domain.pet;

import com.pinkdumbell.cocobob.config.JpaAuditingConfig;
import com.pinkdumbell.cocobob.domain.pet.breed.Breed;
import com.pinkdumbell.cocobob.domain.pet.breed.BreedRepository;
import com.pinkdumbell.cocobob.domain.user.User;
import com.pinkdumbell.cocobob.domain.user.UserRepository;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.context.annotation.Import;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

@DataJpaTest
@Import(JpaAuditingConfig.class)
public class PetRepositoryTest {
    @Autowired
    PetRepository petRepository;
    @Autowired
    UserRepository userRepository;
    @Autowired
    BreedRepository breedRepository;

    @Test
    @DisplayName("사용자 아이디를 통해 등록한 반려동물을 불러오는 메서드 테스트")
    void testFindAllByUserIdWithBreed() {
        User user = userRepository.save(User.builder().build());
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

        List<Pet> pets = petRepository.findAllByUserIdWithBreed(user.getId());
        Assertions.assertThat(pets.size()).isEqualTo(2);
        Assertions.assertThat(pets.stream().map(Pet::getName).collect(Collectors.toList()))
                .isEqualTo(Arrays.asList("코코", "키키"));
        Assertions.assertThat(pets.stream().map(Pet::getBreed).map(Breed::getName).collect(Collectors.toList()))
                .isEqualTo(Arrays.asList("진돗개", "진돗개"));
    }
}
