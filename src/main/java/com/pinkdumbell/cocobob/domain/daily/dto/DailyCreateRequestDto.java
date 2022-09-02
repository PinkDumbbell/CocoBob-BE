package com.pinkdumbell.cocobob.domain.daily.dto;


import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.web.multipart.MultipartFile;

import javax.validation.constraints.NotBlank;
import java.time.LocalDate;
import java.util.List;

@Builder
@Getter
@AllArgsConstructor
public class DailyCreateRequestDto {
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    @NotBlank
    private LocalDate date;
    private String note;
    private List<MultipartFile> images;
}
