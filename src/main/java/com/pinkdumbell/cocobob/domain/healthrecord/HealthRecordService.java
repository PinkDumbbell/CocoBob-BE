package com.pinkdumbell.cocobob.domain.healthrecord;

import com.pinkdumbell.cocobob.common.ImageService;
import com.pinkdumbell.cocobob.domain.abnormal.AbnormalRepository;
import com.pinkdumbell.cocobob.domain.healthrecord.abnormal.HealthRecordAbnormal;
import com.pinkdumbell.cocobob.domain.healthrecord.abnormal.HealthRecordAbnormalId;
import com.pinkdumbell.cocobob.domain.healthrecord.abnormal.HealthRecordAbnormalRepository;
import com.pinkdumbell.cocobob.domain.healthrecord.dto.HealthRecordCreateRequestDto;
import com.pinkdumbell.cocobob.domain.healthrecord.dto.HealthRecordDetailResponseDto;
import com.pinkdumbell.cocobob.domain.healthrecord.image.HealthRecordImage;
import com.pinkdumbell.cocobob.domain.healthrecord.image.HealthRecordImageRepository;
import com.pinkdumbell.cocobob.domain.healthrecord.meal.Meal;
import com.pinkdumbell.cocobob.domain.healthrecord.meal.MealRepository;
import com.pinkdumbell.cocobob.domain.healthrecord.meal.dto.MealCreateRequestDto;
import com.pinkdumbell.cocobob.domain.pet.Pet;
import com.pinkdumbell.cocobob.domain.pet.PetRepository;
import com.pinkdumbell.cocobob.domain.product.Product;
import com.pinkdumbell.cocobob.domain.product.ProductRepository;
import com.pinkdumbell.cocobob.exception.CustomException;
import com.pinkdumbell.cocobob.exception.ErrorCode;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;


@RequiredArgsConstructor
@Service
public class HealthRecordService {

    private final HealthRecordRepository healthRecordRepository;
    private final AbnormalRepository abnormalRepository;
    private final HealthRecordAbnormalRepository healthRecordAbnormalRepository;
    private final HealthRecordImageRepository healthRecordImageRepository;
    private final PetRepository petRepository;
    private final MealRepository mealRepository;
    private final ProductRepository productRepository;
    private final ImageService imageService;
    private static final String HEALTH_RECORD_IMAGE_PREFIX = "health-record/";

    @Transactional
    public void createHealthRecord(Long petId, HealthRecordCreateRequestDto requestDto) {
        Pet pet = petRepository.findById(petId)
                .orElseThrow(() -> new CustomException(ErrorCode.PET_NOT_FOUND));
        HealthRecord healthRecord = healthRecordRepository.save(HealthRecord.builder()
                .date(requestDto.getDate())
                .note(requestDto.getNote())
                .pet(pet)
                .build());
        saveImages(healthRecord, requestDto.getImages());
        registerAbnormal(healthRecord, requestDto.getAbnormalIds());
    }

    @Transactional
    public void saveImages(HealthRecord healthRecord, List<MultipartFile> images) {
        if (images != null) {
            images.forEach(image -> healthRecordImageRepository.save(
                    HealthRecordImage.builder()
                            .healthRecord(healthRecord)
                            .path(imageService.saveImage(
                                    image,
                                    HEALTH_RECORD_IMAGE_PREFIX + healthRecord.getId() + "_" + UUID.randomUUID()
                            ))
                            .build()
            ));
        }
    }

    @Transactional
    public void registerAbnormal(HealthRecord healthRecord, List<Long> abnormalIds) {
        if (abnormalIds != null) {
            abnormalRepository.findAllById(abnormalIds)
                    .forEach(abnormal -> healthRecordAbnormalRepository.save(
                            HealthRecordAbnormal.builder()
                                    .healthRecordAbnormalId(new HealthRecordAbnormalId(
                                            healthRecord.getId(),
                                            abnormal.getId()
                                    ))
                                    .healthRecord(healthRecord)
                                    .abnormal(abnormal)
                                    .build()
                    ));
        }
    }

    @Transactional(readOnly = true)
    public HealthRecordDetailResponseDto getHealthRecord(Long healthRecordId) {
        HealthRecord healthRecord = healthRecordRepository.findById(healthRecordId)
                .orElseThrow(() -> new CustomException(ErrorCode.HEALTH_RECORD_NOT_FOUND));

        return new HealthRecordDetailResponseDto(
                healthRecord,
                healthRecordImageRepository.findAllByHealthRecord(healthRecord),
                healthRecordAbnormalRepository.findAllAbnormalByHealthRecord(healthRecordId)
                        .stream().map(HealthRecordAbnormal::getAbnormal).collect(Collectors.toList()),
                mealRepository.findAllByHealthRecordId(healthRecord.getId())
        );
    }

    @Transactional
    public void createMeal(Long healthRecordId, MealCreateRequestDto requestDto) {
        HealthRecord healthRecord = healthRecordRepository.findById(healthRecordId)
                .orElseThrow(() -> new CustomException(ErrorCode.HEALTH_RECORD_NOT_FOUND));
        Product product = null;
        if (requestDto.getProductId() != null) {
            product = productRepository.findById(requestDto.getProductId())
                    .orElseThrow(() -> new CustomException(ErrorCode.PRODUCT_NOT_FOUND));
        }
        mealRepository.save(Meal.builder()
                        .kcal(requestDto.getKcal())
                        .amount(requestDto.getAmount())
                        .product(product)
                        .productName(requestDto.getProductName())
                        .healthRecord(healthRecord)
                .build());
    }
}
