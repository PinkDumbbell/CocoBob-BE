package com.pinkdumbell.cocobob.domain.walk.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.web.multipart.MultipartFile;

import java.time.LocalDate;
import java.time.LocalTime;

@Builder
@Getter
@AllArgsConstructor
public class WalkCreateRequestDto {
    @DateTimeFormat(pattern = "HH:mm:ss")
    private LocalTime startedAt;
    @DateTimeFormat(pattern = "HH:mm:ss")
    private LocalTime finishedAt;
    private MultipartFile photo;
    private Double distance;
    private Integer totalTime;
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private LocalDate date;
    private Double spentKcal;
}
