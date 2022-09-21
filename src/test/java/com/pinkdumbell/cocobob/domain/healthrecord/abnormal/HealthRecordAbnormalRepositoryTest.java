package com.pinkdumbell.cocobob.domain.healthrecord.abnormal;

import com.pinkdumbell.cocobob.domain.abnormal.Abnormal;
import com.pinkdumbell.cocobob.domain.abnormal.AbnormalRepository;
import com.pinkdumbell.cocobob.domain.record.healthrecord.HealthRecord;
import com.pinkdumbell.cocobob.domain.record.healthrecord.HealthRecordRepository;
import com.pinkdumbell.cocobob.domain.product.ProductSearchQueryDslImpl;
import com.pinkdumbell.cocobob.domain.record.healthrecord.abnormal.HealthRecordAbnormal;
import com.pinkdumbell.cocobob.domain.record.healthrecord.abnormal.HealthRecordAbnormalId;
import com.pinkdumbell.cocobob.domain.record.healthrecord.abnormal.HealthRecordAbnormalRepository;
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
class HealthRecordAbnormalRepositoryTest {
    @Autowired
    HealthRecordRepository healthRecordRepository;
    @Autowired
    AbnormalRepository abnormalRepository;
    @Autowired
    HealthRecordAbnormalRepository healthRecordAbnormalRepository;
    @PersistenceContext
    EntityManager em;
    @MockBean
    ProductSearchQueryDslImpl productSearchQueryDsl;

    @Test
    void testFindAllAbnormalByHealthRecord() {
        Abnormal abn1 = abnormalRepository.save(Abnormal.builder().name("슬개골 탈구").build());
        Abnormal abn2 = abnormalRepository.save(Abnormal.builder().name("설사").build());
        Abnormal abn3 = abnormalRepository.save(Abnormal.builder().name("눈물").build());

        HealthRecord healthRecord = healthRecordRepository.save(HealthRecord.builder()
                .date(LocalDate.parse("2022-08-01"))
                .note("테스트 입니다.")
                .build());

        healthRecordAbnormalRepository.save(HealthRecordAbnormal.builder()
                        .healthRecordAbnormalId(new HealthRecordAbnormalId(healthRecord.getId(), abn1.getId()))
                        .abnormal(abn1)
                        .healthRecord(healthRecord)
                .build());
        healthRecordAbnormalRepository.save(HealthRecordAbnormal.builder()
                        .healthRecordAbnormalId(new HealthRecordAbnormalId(healthRecord.getId(), abn2.getId()))
                        .abnormal(abn2)
                        .healthRecord(healthRecord)
                .build());
        healthRecordAbnormalRepository.save(HealthRecordAbnormal.builder()
                        .healthRecordAbnormalId(new HealthRecordAbnormalId(healthRecord.getId(), abn3.getId()))
                        .abnormal(abn3)
                        .healthRecord(healthRecord)
                .build());

        em.flush();
        em.clear();

        List<HealthRecordAbnormal> result = healthRecordAbnormalRepository.findAllAbnormalByHealthRecord(healthRecord.getId());
        assertThat(result.stream().map(healthRecordAbnormal -> healthRecordAbnormal.getAbnormal().getName()).collect(Collectors.toList()).toString())
                .contains(abn1.getName(), abn2.getName(), abn3.getName());
    }
}