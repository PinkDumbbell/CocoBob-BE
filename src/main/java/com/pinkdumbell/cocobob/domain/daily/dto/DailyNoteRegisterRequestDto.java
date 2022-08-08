package com.pinkdumbell.cocobob.domain.daily.dto;

import java.time.LocalDate;
import java.util.List;
import javax.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.web.multipart.MultipartFile;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class DailyNoteRegisterRequestDto {

    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private LocalDate date;
    private List<MultipartFile> noteImages;
    @NotBlank(message = "필수 입력 항목(note)가 없습니다.")
    private String note;
}
