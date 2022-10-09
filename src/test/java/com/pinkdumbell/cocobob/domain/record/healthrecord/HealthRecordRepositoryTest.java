package com.pinkdumbell.cocobob.domain.record.healthrecord;

import com.pinkdumbell.cocobob.domain.pet.Pet;
import com.pinkdumbell.cocobob.domain.pet.PetRepository;
import com.pinkdumbell.cocobob.domain.product.ProductSearchQueryDslImpl;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

import static org.assertj.core.api.Assertions.*;

@DataJpaTest
class HealthRecordRepositoryTest {
    @Autowired
    HealthRecordRepository healthRecordRepository;
    @Autowired
    PetRepository petRepository;
    @MockBean
    ProductSearchQueryDslImpl productSearchQueryDsl;
    @PersistenceContext
    EntityManager em;
    Pet pet;
    LocalDate criteria;

    @BeforeEach
    void setup() {
        pet = petRepository.save(Pet.builder()
                .build());
        criteria = LocalDate.parse("2022-08-01");
        for (int i = 0; i < 20; i++) {
            if (i % 2 == 0) {
                healthRecordRepository.save(HealthRecord.builder()
                                .date(criteria.plusDays(i))
                                .bodyWeight((double) (10 + i))
                                .pet(pet)
                        .build());
            }
        }
        em.flush();
        em.clear();
    }

    @Test
    void testFindRecentBodyWeights() {
        List<LocalDate> resultDate = new ArrayList<>();
        List<Double> resultBodyWeight = new ArrayList<>();

        for (int i = 0; i < 10; i++) {
            resultDate.add(criteria.plusDays(i * 2));
            resultBodyWeight.add((double) (10 + i * 2));
        }

        Collections.reverse(resultDate);
        Collections.reverse(resultBodyWeight);

        Page<HealthRecord> recentHealthRecords = healthRecordRepository.findRecentHealthRecords(
                pet.getId(),
                criteria.plusDays(30),
                Pageable.ofSize(10)
        );

        List<HealthRecord> result = recentHealthRecords.getContent();
        assertThat(result.size()).isEqualTo(10);
        assertThat(result.stream().map(HealthRecord::getDate).collect(Collectors.toList())).isEqualTo(resultDate);
        assertThat(result.stream().map(HealthRecord::getBodyWeight).collect(Collectors.toList())).isEqualTo(resultBodyWeight);
    }
}