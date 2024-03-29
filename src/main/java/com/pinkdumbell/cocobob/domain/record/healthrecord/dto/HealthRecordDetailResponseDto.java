package com.pinkdumbell.cocobob.domain.record.healthrecord.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.pinkdumbell.cocobob.domain.abnormal.Abnormal;
import com.pinkdumbell.cocobob.domain.record.healthrecord.HealthRecord;
import com.pinkdumbell.cocobob.domain.record.healthrecord.image.HealthRecordImage;
import com.pinkdumbell.cocobob.domain.record.healthrecord.meal.Meal;
import com.pinkdumbell.cocobob.domain.product.Product;
import lombok.Getter;

import java.time.LocalDate;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

@Getter
public class HealthRecordDetailResponseDto {
    private final Long healthRecordId;
    @JsonFormat(pattern = "yyyy-MM-dd")
    private final LocalDate date;
    private final String note;
    private final Double bodyWeight;
    private final List<RecentBodyWeightResponseDto> bodyWeights;
    private final List<HealthRecordImageResponseDto> images;
    private final List<AbnormalResponseDto> abnormals;
    private final List<MealResponseDto> meals;

    @Getter
    public static class RecentBodyWeightResponseDto {
        @JsonFormat(pattern = "yyyy-MM-dd")
        private final LocalDate date;
        private final Double bodyWeight;

        public RecentBodyWeightResponseDto(HealthRecord entity) {
            this.date = entity.getDate();
            this.bodyWeight = entity.getBodyWeight();
        }
    }
    @Getter
    public static class HealthRecordImageResponseDto {
        private final Long imageId;
        private final String path;

        public HealthRecordImageResponseDto(HealthRecordImage entity) {
            this.imageId = entity.getId();
            this.path = entity.getPath();
        }
    }

    @Getter
    public static class AbnormalResponseDto {
        private final Long abnormalId;
        private final String name;

        public AbnormalResponseDto(Abnormal entity) {
            this.abnormalId = entity.getId();
            this.name = entity.getName();
        }
    }

    @Getter
    public static class MealResponseDto {
        private final Long mealId;
        private final ProductSimpleInfoDto productInfo;
        private final Double kcal;
        private final Integer amount;

        @Getter
        public static class ProductSimpleInfoDto {
            private final Long productId;
            private final String productName;
            private final String brand;
            private final String thumbnail;

            public ProductSimpleInfoDto(Long productId, String productName, String brand, String thumbnail) {
                this.productId = productId;
                this.productName = productName;
                this.brand = brand;
                this.thumbnail = thumbnail;
            }
        }

        public MealResponseDto(Meal entity) {
            this.mealId = entity.getId();
            this.kcal = entity.getKcal();
            this.amount = entity.getAmount();
            Product product = entity.getProduct();
            if (product != null) {
                this.productInfo = new ProductSimpleInfoDto(product.getId(), product.getName(), product.getBrand(), product.getThumbnail());
            } else {
                this.productInfo = new ProductSimpleInfoDto(null, entity.getProductName(), null, null);
            }
        }
    }

    public HealthRecordDetailResponseDto(
            HealthRecord healthRecord,
            List<HealthRecord> recentHealthRecords,
            List<HealthRecordImage> images,
            List<Abnormal> abnormals,
            List<Meal> meals
    ) {
        this.healthRecordId = healthRecord.getId();
        this.date = healthRecord.getDate();
        this.note = healthRecord.getNote();
        this.bodyWeight = healthRecord.getBodyWeight();
        List<RecentBodyWeightResponseDto> temp = recentHealthRecords.stream().map(RecentBodyWeightResponseDto::new)
                .collect(Collectors.toList());
        Collections.reverse(temp);
        this.bodyWeights = temp;
        this.images = images.stream().map(HealthRecordImageResponseDto::new)
                .collect(Collectors.toList());
        this.abnormals = abnormals.stream().map(AbnormalResponseDto::new)
                .collect(Collectors.toList());
        this.meals = meals.stream().map(MealResponseDto::new)
                .collect(Collectors.toList());
    }
}
