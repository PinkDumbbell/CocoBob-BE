package com.pinkdumbell.cocobob.domain.record.healthrecord;

import com.pinkdumbell.cocobob.common.ImageService;
import com.pinkdumbell.cocobob.domain.abnormal.Abnormal;
import com.pinkdumbell.cocobob.domain.abnormal.AbnormalRepository;
import com.pinkdumbell.cocobob.domain.record.healthrecord.abnormal.HealthRecordAbnormal;
import com.pinkdumbell.cocobob.domain.record.healthrecord.abnormal.HealthRecordAbnormalId;
import com.pinkdumbell.cocobob.domain.record.healthrecord.abnormal.HealthRecordAbnormalRepository;
import com.pinkdumbell.cocobob.domain.record.healthrecord.dto.HealthRecordCreateRequestDto;
import com.pinkdumbell.cocobob.domain.record.healthrecord.dto.HealthRecordDetailResponseDto;
import com.pinkdumbell.cocobob.domain.record.healthrecord.dto.HealthRecordUpdateRequestDto;
import com.pinkdumbell.cocobob.domain.record.healthrecord.dto.RecentWeightsResponseDto;
import com.pinkdumbell.cocobob.domain.record.healthrecord.image.HealthRecordImage;
import com.pinkdumbell.cocobob.domain.record.healthrecord.image.HealthRecordImageRepository;
import com.pinkdumbell.cocobob.domain.record.healthrecord.meal.Meal;
import com.pinkdumbell.cocobob.domain.record.healthrecord.meal.MealRepository;
import com.pinkdumbell.cocobob.domain.record.healthrecord.meal.dto.MealCreateRequestDto;
import com.pinkdumbell.cocobob.domain.record.healthrecord.meal.dto.MealUpdateRequestDto;
import com.pinkdumbell.cocobob.domain.pet.Pet;
import com.pinkdumbell.cocobob.domain.pet.PetRepository;
import com.pinkdumbell.cocobob.domain.product.Product;
import com.pinkdumbell.cocobob.domain.product.ProductRepository;
import com.pinkdumbell.cocobob.exception.CustomException;
import com.pinkdumbell.cocobob.exception.ErrorCode;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.util.ArrayList;
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
                .bodyWeight(requestDto.getBodyWeight())
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
                healthRecordRepository.findRecentHealthRecords(
                        healthRecord.getPet().getId(),
                        healthRecord.getDate(),
                        Pageable.ofSize(10)
                ).getContent(),
                healthRecordImageRepository.findAllByHealthRecord(healthRecord),
                healthRecordAbnormalRepository.findAllAbnormalByHealthRecord(healthRecordId)
                        .stream().map(HealthRecordAbnormal::getAbnormal).collect(Collectors.toList()),
                mealRepository.findAllByHealthRecordId(healthRecord.getId())
        );
    }

    @Transactional
    public Product findProductById(Long productId) {

        return productRepository.findById(productId)
                .orElseThrow(() -> new CustomException(ErrorCode.PRODUCT_NOT_FOUND));

    }

    @Transactional
    public Meal findMealById(Long mealId) {

        return mealRepository.findById(mealId)
                .orElseThrow(() -> new CustomException(ErrorCode.MEAL_RECORD_NOT_FOUND));
    }

    @Transactional
    public void createMeal(Long healthRecordId, MealCreateRequestDto requestDto) {
        HealthRecord healthRecord = healthRecordRepository.findById(healthRecordId)
                .orElseThrow(() -> new CustomException(ErrorCode.HEALTH_RECORD_NOT_FOUND));
        Product product = null;
        if (requestDto.getProductId() != null) {
            product = findProductById(requestDto.getProductId());
        }
        mealRepository.save(Meal.builder()
                        .kcal(requestDto.getKcal())
                        .amount(requestDto.getAmount())
                        .product(product)
                        .productName(requestDto.getProductName())
                        .healthRecord(healthRecord)
                .build());
    }

    @Transactional
    public void updateHealthRecord(Long healthRecordId, HealthRecordUpdateRequestDto requestDto) {
        HealthRecord healthRecord = healthRecordRepository.findById(healthRecordId)
                .orElseThrow(() -> new CustomException(ErrorCode.HEALTH_RECORD_NOT_FOUND));
        healthRecord.update(requestDto);
        saveImages(healthRecord, requestDto.getNewImages());
        deleteImages(requestDto.getImageIdsToDelete());
        updateHealthRecordAbnormals(healthRecord, requestDto.getAbnormalIds());
    }

    @Transactional
    public void deleteImages(List<Long> imageIdsToDelete) {
        if (imageIdsToDelete == null) {
            return;
        }
        List<HealthRecordImage> images = healthRecordImageRepository.findAllById(imageIdsToDelete);
        healthRecordImageRepository.deleteAll(images);
        getImageNames(images).forEach(imageService::deleteImage);
    }

    private List<String> getImageNames(List<HealthRecordImage> images) {
        final String amazonPattern = "amazonaws.com/";
        return images.stream().map(healthRecordImage ->
                healthRecordImage.getPath().split(amazonPattern)[1])
                .collect(Collectors.toList());
    }

    @Transactional
    public void updateHealthRecordAbnormals(HealthRecord healthRecord, List<Long> abnormalIds) {
        List<Long> abnormalIdsToDelete = healthRecordAbnormalRepository.findAllAbnormalByHealthRecord(healthRecord.getId())
                .stream().map(healthRecordAbnormal -> healthRecordAbnormal.getAbnormal().getId()).collect(Collectors.toList());
        List<Long> abnormalIdsToAdd = new ArrayList<>(abnormalIds);
        // 추가될 것
        abnormalIdsToAdd.removeAll(abnormalIdsToDelete);
        // 삭제될 것
        abnormalIdsToDelete.removeAll(abnormalIds);
        healthRecordAbnormalRepository.deleteAllByHealthRecordIdAndAbnormalId(healthRecord.getId(), abnormalIdsToDelete);
        List<Abnormal> abnormalsToAdd = abnormalRepository.findAllById(abnormalIdsToAdd);
        healthRecordAbnormalRepository.saveAll(abnormalsToAdd.stream().map(abnormal -> HealthRecordAbnormal.builder()
                .healthRecordAbnormalId(new HealthRecordAbnormalId(healthRecord.getId(), abnormal.getId()))
                .healthRecord(healthRecord)
                .abnormal(abnormal)
                .build()
        ).collect(Collectors.toList()));
    }

    @Transactional
    public void deleteHealthRecord(Long healthRecordId) {
        HealthRecord healthRecord = healthRecordRepository.findById(healthRecordId)
                .orElseThrow(() -> new CustomException(ErrorCode.HEALTH_RECORD_NOT_FOUND));
        healthRecordAbnormalRepository.deleteAllByHealthRecordId(healthRecordId);
        mealRepository.deleteAllByHealthRecordId(healthRecordId);
        healthRecordImageRepository.deleteAllByHealthRecordId(healthRecordId);
        healthRecordRepository.delete(healthRecord);
    }

    @Transactional
    public void updateMeal(Long mealId, MealUpdateRequestDto requestDto) {
        Meal meal = findMealById(mealId);
        Product product = null;
        if (requestDto.getProductId() != null) {
            product = findProductById(requestDto.getProductId());
        }
        meal.updateMeal(
                requestDto.getProductName(),
                requestDto.getKcal(),
                requestDto.getAmount(),
                product);
    }

    @Transactional
    public void deleteMeal(Long mealId) {
        mealRepository.delete(findMealById(mealId));
    }

    @Transactional(readOnly = true)
    public RecentWeightsResponseDto getRecentWeights(Long petId) {

        return new RecentWeightsResponseDto(healthRecordRepository.findRecentWeightsWithDatesByPetId(
                petId,
                Pageable.ofSize(7)
        ).getContent());
    }
}
