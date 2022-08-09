package com.pinkdumbell.cocobob.domain.daily.dto;

import java.time.LocalDate;
import java.util.List;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.web.multipart.MultipartFile;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class DailyRecordRegisterRequestDto {

    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private LocalDate date;
    private List<MultipartFile> noteImages;
    private String note;
    private Integer feedAmount;
    private Integer walkTotalTime;
    private Float walkDistance;
    private String walkGps;

}
