package com.pinkdumbell.cocobob.domain.daily;

import com.pinkdumbell.cocobob.common.dto.CommonResponseDto;
import com.pinkdumbell.cocobob.config.annotation.loginuser.LoginUser;
import com.pinkdumbell.cocobob.domain.daily.dto.DailyNoteRequestDto;
import com.pinkdumbell.cocobob.domain.daily.dto.DailyNoteResponseDto;
import com.pinkdumbell.cocobob.domain.user.dto.LoginUserInfo;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
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

    private static class RecordDailyNoteResponseClass extends
        CommonResponseDto<DailyNoteResponseDto> {

        public RecordDailyNoteResponseClass(int status, String code, String message,
            DailyNoteResponseDto data) {
            super(status, code, message, data);
        }
    }

    @PostMapping("/{petId}/note")
    public ResponseEntity<RecordDailyNoteResponseClass> recordDailyNote(@ModelAttribute
    DailyNoteRequestDto dailyNoteRequestDto, @PathVariable("petId") Long petId) {

        return ResponseEntity.ok(
            new RecordDailyNoteResponseClass(HttpStatus.OK.value(),
                "SUCCESS RECORD DAILY NOT",
                "데일리 기록을 저장하는데 성공하였습니다.",
                dailyService.recordNote(dailyNoteRequestDto, petId)));
    }

}
