package com.pinkdumbell.cocobob.domain.record.walk;

import com.pinkdumbell.cocobob.domain.pet.Pet;
import com.pinkdumbell.cocobob.domain.pet.PetRepository;
import com.pinkdumbell.cocobob.domain.product.ProductSearchQueryDslImpl;
import com.pinkdumbell.cocobob.domain.record.walk.dto.WalkBriefInfoDto;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.mock.mockito.MockBean;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import java.time.LocalDate;
import java.util.List;
import java.util.stream.Collectors;

import static org.assertj.core.api.Assertions.*;

@DataJpaTest
class WalkRepositoryTest {
    @Autowired
    WalkRepository walkRepository;
    @Autowired
    PetRepository petRepository;
    @PersistenceContext
    EntityManager em;
    @MockBean
    ProductSearchQueryDslImpl productSearchQueryDsl;
    Pet pet;
    List<Walk> walks;
    LocalDate date = LocalDate.parse("2022-08-01");
    @BeforeEach
    void setup() {
        pet = petRepository.save(Pet.builder()
                .build());
        List<Long> numbers = List.of(1L, 2L, 3L);
        walks = numbers.stream().map(number -> walkRepository.save(Walk.builder()
                        .date(date)
                        .pet(pet)
                        .totalTime(Math.toIntExact(number))
                        .distance(number + 0.1)
                .build())).collect(Collectors.toList());
        em.flush();
        em.clear();
    }

    @Test
    void testGetTotalTimeAndTotalDistance() {
        WalkBriefInfoDto result = walkRepository.getTotalTimeAndTotalDistance(pet.getId(), date);

        assertThat(result.getTotalTime()).isEqualTo(6);
        assertThat(result.getTotalDistance()).isEqualTo(6.3);
    }
}