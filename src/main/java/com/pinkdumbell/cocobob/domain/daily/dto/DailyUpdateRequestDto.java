package com.pinkdumbell.cocobob.domain.daily.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@Getter
@AllArgsConstructor
public class DailyUpdateRequestDto {
    private List<MultipartFile> newImages;
    private String note;
    private List<Long> imageIdsToDelete;
}
