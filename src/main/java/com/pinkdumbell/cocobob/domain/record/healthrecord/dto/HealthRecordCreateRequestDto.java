package com.pinkdumbell.cocobob.domain.record.healthrecord.dto;


import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.web.multipart.MultipartFile;

import javax.validation.constraints.NotNull;
import java.time.LocalDate;
import java.util.List;

@Builder
@Getter
@AllArgsConstructor
public class HealthRecordCreateRequestDto {
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    @NotNull(message = "기록 날짜를 입력해주세요.")
    private LocalDate date;
    private String note;
    private List<MultipartFile> images;
    private List<Long> abnormalIds;
}
