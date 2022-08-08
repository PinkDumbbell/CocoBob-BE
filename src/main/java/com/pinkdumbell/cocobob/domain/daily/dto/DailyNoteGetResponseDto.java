package com.pinkdumbell.cocobob.domain.daily.dto;

import com.pinkdumbell.cocobob.domain.daily.Daily;
import com.pinkdumbell.cocobob.domain.daily.image.DailyImage;
import java.time.LocalDate;
import java.util.List;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class DailyNoteGetResponseDto {

    private Long id;

    private LocalDate date;

    private Integer feedAmount;

    private Integer walkTotalTime;

    private Float walkDistance;

    private String walkGps;

    private String note;

    private Long petId;

    private List<String> images;

    public DailyNoteGetResponseDto(Daily daily,List<DailyImage> dailyImages){

    }


}
