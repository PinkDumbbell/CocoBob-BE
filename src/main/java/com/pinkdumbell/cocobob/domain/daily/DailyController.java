package com.pinkdumbell.cocobob.domain.daily;

import com.pinkdumbell.cocobob.common.dto.CommonResponseDto;
import com.pinkdumbell.cocobob.domain.daily.dto.DailyNoteGetResponseDto;
import com.pinkdumbell.cocobob.domain.daily.dto.DailyNoteRegisterRequestDto;
import com.pinkdumbell.cocobob.domain.daily.dto.DailyNoteRegisterResponseDto;
import io.swagger.annotations.ApiOperation;
import java.util.List;
import javax.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
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

    private static class RecordDailyNoteRegisterResponseClass extends
        CommonResponseDto<DailyNoteRegisterResponseDto> {

        public RecordDailyNoteRegisterResponseClass(int status, String code, String message,
            DailyNoteRegisterResponseDto data) {
            super(status, code, message, data);
        }
    }

    private static class RecordDailyNoteGetResponseClass extends
        CommonResponseDto<List<DailyNoteGetResponseDto>> {


        public RecordDailyNoteGetResponseClass(int status, String code, String message,
            List<DailyNoteGetResponseDto> data) {
            super(status, code, message, data);
        }
    }

    @PostMapping("/{petId}")
    public ResponseEntity<RecordDailyNoteRegisterResponseClass> recordDailyNote(@ModelAttribute
    @Valid DailyNoteRegisterRequestDto dailyNoteRegisterRequestDto,
        @PathVariable("petId") Long petId) {

        return ResponseEntity.ok(
            new RecordDailyNoteRegisterResponseClass(HttpStatus.OK.value(),
                "SUCCESS RECORD DAILY",
                "데일리 기록을 저장하는데 성공하였습니다.",
                dailyService.recordNote(dailyNoteRegisterRequestDto, petId)));
    }

    @GetMapping("/{petId}")
    public ResponseEntity<RecordDailyNoteGetResponseClass> getDailyNote(
        @PathVariable("petId") Long petId) {

        return ResponseEntity.ok(
            new RecordDailyNoteGetResponseClass(HttpStatus.OK.value(),
                "SUCCESS GET DAILY",
                "데일리 기록을 불러오는데 성공하였습니다.",
                dailyService.getNotes(petId)));
    }

}
