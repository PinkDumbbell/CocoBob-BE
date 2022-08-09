package com.pinkdumbell.cocobob.domain.daily;

import com.pinkdumbell.cocobob.common.dto.CommonResponseDto;
import com.pinkdumbell.cocobob.domain.daily.dto.DailyRecordDetailResponseDto;
import com.pinkdumbell.cocobob.domain.daily.dto.DailyRecordGetRequestDto;
import com.pinkdumbell.cocobob.domain.daily.dto.DailyRecordGetResponseDto;
import com.pinkdumbell.cocobob.domain.daily.dto.DailyRecordRegisterRequestDto;
import com.pinkdumbell.cocobob.domain.daily.dto.DailyRecordRegisterResponseDto;
import com.pinkdumbell.cocobob.domain.daily.dto.DailyRecordUpdateRequestDto;
import com.pinkdumbell.cocobob.domain.daily.dto.DailyRecordUpdateResponseDto;
import com.pinkdumbell.cocobob.domain.daily.dto.DailySimpleRequestDto;
import com.pinkdumbell.cocobob.domain.daily.dto.DailySimpleResponseDto;
import io.swagger.annotations.ApiOperation;
import java.util.List;
import javax.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@ApiOperation("Daily API")
@RequestMapping("/v1/dailys")
@RequiredArgsConstructor
@RestController
public class DailyController {

    private final DailyService dailyService;

    private static class DailyRecordRegisterResponseClass extends
        CommonResponseDto<DailyRecordRegisterResponseDto> {

        public DailyRecordRegisterResponseClass(int status, String code, String message,
            DailyRecordRegisterResponseDto data) {
            super(status, code, message, data);
        }
    }

    private static class RecordDailyNoteGetResponseClass extends
        CommonResponseDto<List<DailyRecordGetResponseDto>> {

        public RecordDailyNoteGetResponseClass(int status, String code, String message,
            List<DailyRecordGetResponseDto> data) {
            super(status, code, message, data);
        }
    }

    private static class SimpleDailyResponseClass extends
        CommonResponseDto<List<DailySimpleResponseDto>> {

        public SimpleDailyResponseClass(int status, String code, String message,
            List<DailySimpleResponseDto> data) {
            super(status, code, message, data);
        }
    }

    private static class DailyRecordUpdateResponseClass extends
        CommonResponseDto<DailyRecordUpdateResponseDto> {

        public DailyRecordUpdateResponseClass(int status, String code, String message,
            DailyRecordUpdateResponseDto data) {
            super(status, code, message, data);
        }
    }

    private static class DailyRecordDetailResponseClass extends
        CommonResponseDto<DailyRecordDetailResponseDto> {

        public DailyRecordDetailResponseClass(int status, String code, String message,
            DailyRecordDetailResponseDto data) {
            super(status, code, message, data);
        }
    }

    @PostMapping("/{petId}")
    public ResponseEntity<DailyRecordRegisterResponseClass> createDailyRecord(
        @Valid @ModelAttribute
        DailyRecordRegisterRequestDto dailyRecordRegisterRequestDto,
        @PathVariable("petId") Long petId) {

        return ResponseEntity.ok(
            new DailyRecordRegisterResponseClass(HttpStatus.OK.value(),
                "SUCCESS CREATE DAILY",
                "데일리 기록을 생성 하는데 성공하였습니다.",
                dailyService.createDailyRecord(dailyRecordRegisterRequestDto, petId)));
    }

    @GetMapping("/{petId}")
    public ResponseEntity<RecordDailyNoteGetResponseClass> getDailyNote(
        @PathVariable("petId") Long petId,
        @Valid DailyRecordGetRequestDto dailyRecordGetRequestDto) {

        return ResponseEntity.ok(
            new RecordDailyNoteGetResponseClass(HttpStatus.OK.value(),
                "SUCCESS GET DAILY",
                "데일리 기록을 불러오는데 성공하였습니다.",
                dailyService.getDaily(petId, dailyRecordGetRequestDto)));
    }

    @GetMapping("/simple/{petId}")
    public ResponseEntity<SimpleDailyResponseClass> getSimpleDaily(
        @PathVariable("petId") Long petId, @Valid DailySimpleRequestDto dailySimpleRequestDto) {

        return ResponseEntity.ok(
            new SimpleDailyResponseClass(HttpStatus.OK.value(),
                "SUCCESS GET SIMPLE DAILY",
                "데일리 기록을 불러오는데 성공하였습니다.",
                dailyService.getSimpleDaily(petId, dailySimpleRequestDto.getYearMonth())));
    }

    @PatchMapping("/edit/{dailyId}")
    public ResponseEntity<DailyRecordUpdateResponseClass> updateDailyRecord(
        @PathVariable("dailyId") Long dailyId, @ModelAttribute
    DailyRecordUpdateRequestDto dailyRecordUpdateRequestDto) {

        return ResponseEntity.ok(
            new DailyRecordUpdateResponseClass(HttpStatus.OK.value(),
                "SUCCESS UPDATE DAILY",
                "데일리 기록을 변경하는데 성공하였습니다.",
                dailyService.updateDailyRecord(dailyId, dailyRecordUpdateRequestDto)));
    }

    @DeleteMapping("/edit/{dailyId}")
    public ResponseEntity<CommonResponseDto> deleteDailyRecord(
        @PathVariable("dailyId") Long dailyId) {

        dailyService.deleteDaily(dailyId);

        return ResponseEntity.ok(CommonResponseDto.builder()
            .status(HttpStatus.OK.value())
            .code("SUCCESS DELETE DAILY")
            .message("데일리 기록을 정상적으로 삭제하였습니다.")
            .data(null)
            .build());
    }

    @GetMapping("/detail/{dailyId}")
    public ResponseEntity<DailyRecordDetailResponseClass> getDailyDetailRecord(
        @PathVariable("dailyId") Long dailyId) {

        return ResponseEntity.ok(new DailyRecordDetailResponseClass(
            HttpStatus.OK.value(),
            "SUCCESS GET DAILY",
            "데일리 기록을 불러오는데 성공하였습니다.",
            dailyService.getDailyDetailRecord(dailyId)));
    }

    @DeleteMapping("/{dailyId}/images/{dailyimageId}")
    public ResponseEntity<CommonResponseDto> deleteDailyImage(
        @PathVariable("dailyId") Long dailyId, @PathVariable("dailyimageId") Long dailyImageId) {

        dailyService.deleteDailyImage(dailyId,dailyImageId);

        return ResponseEntity.ok(CommonResponseDto.builder().
            status(HttpStatus.OK.value())
            .code("SUCCESS DELETE DAILY IMAGE")
            .message("데일리 이미지를 삭제하는데 성공하였습니다.")
            .data(null)
            .build());
    }


}
