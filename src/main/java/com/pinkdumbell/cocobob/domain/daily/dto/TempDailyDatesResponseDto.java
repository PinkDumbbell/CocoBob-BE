package com.pinkdumbell.cocobob.domain.daily.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.pinkdumbell.cocobob.domain.daily.Daily;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;

import java.time.LocalDate;
import java.util.List;
import java.util.stream.Collectors;

@Getter
public class TempDailyDatesResponseDto {
    @ApiModelProperty(notes = "해당 월의 데일리 기록이 존재하는 모든 날의 날짜와 아이디"
            , example = "[{id: 1, date: 2020-02-01}, {id: 2, date: 2020-02-03}, ...]")
    List<IdAndDate> idAndDates;

    public TempDailyDatesResponseDto(List<Daily> entities) {
        this.idAndDates = entities.stream().map(IdAndDate::new)
                .collect(Collectors.toList());
    }

    @Getter
    public static class IdAndDate {
        @ApiModelProperty(notes = "데일리 아이디")
        private Long id;
        @ApiModelProperty(notes = "데일리 기록 날짜")
        @JsonFormat(pattern = "yyyy-MM-dd")
        private LocalDate date;

        public IdAndDate(Daily entity) {
            this.id = entity.getId();
            this.date = entity.getDate();
        }
    }
}
