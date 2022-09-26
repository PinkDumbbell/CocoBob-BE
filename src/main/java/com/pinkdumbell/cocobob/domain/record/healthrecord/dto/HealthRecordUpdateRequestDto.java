package com.pinkdumbell.cocobob.domain.record.healthrecord.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@Builder
@Getter
@AllArgsConstructor
public class HealthRecordUpdateRequestDto {
    private List<MultipartFile> newImages;
    private String note;
    private Double bodyWeight;
    private List<Long> imageIdsToDelete;
    private List<Long> abnormalIds;
}
