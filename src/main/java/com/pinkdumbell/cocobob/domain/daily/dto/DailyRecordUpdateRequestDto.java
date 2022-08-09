package com.pinkdumbell.cocobob.domain.daily.dto;

import java.util.List;
import lombok.AllArgsConstructor;

import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.web.multipart.MultipartFile;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class DailyRecordUpdateRequestDto {
    private List<MultipartFile> images;
    private String note;
    private Integer feedAmount;
    private Integer walkTotalTime;
    private Float walkDistance;
    private String walkGps;
}
