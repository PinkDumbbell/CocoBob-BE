package com.pinkdumbell.cocobob.domain.record;

import lombok.RequiredArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.time.YearMonth;

@RequiredArgsConstructor
@RestController
@RequestMapping("/v1/records")
public class RecordController {

    private final RecordService recordService;

    @GetMapping("/pet/{petId}/ids")
    public ResponseEntity<RecordResponseClass.RecordExistResponseClass> getIdsOfDaysOfMonth(
            @PathVariable Long petId,
            @DateTimeFormat(pattern = "yyyy-MM") @RequestParam("year-month") YearMonth yearMonth
    ) {
        return ResponseEntity.ok(new RecordResponseClass.RecordExistResponseClass(
                HttpStatus.OK.value(),
                "SUCCESS_TO_GET_ID_AND_DATE_OF_MONTH",
                "해당 월의 일별 기록의 아이디와 날짜를 불러왔습니다.",
                recordService.getIdsOfDaysOfMonth(petId, yearMonth)
        ));
    }

    @GetMapping("/pet/{petId}")
    public ResponseEntity<RecordResponseClass.RecordsSimpleResponseClass> getSimpleRecordsOfDay(
            @PathVariable Long petId,
            @DateTimeFormat(pattern = "yyyy-MM-dd") LocalDate date
    ) {
        return ResponseEntity.ok(new RecordResponseClass.RecordsSimpleResponseClass(
                HttpStatus.OK.value(),
                "SUCCESS_TO_GET_SIMPLE_RECORDS_INFO",
                "해당 날짜 기록의 간단한 정보를 불러왔습니다.",
                recordService.getSimpleRecordsOfDay(petId, date)
        ));
    }
}
