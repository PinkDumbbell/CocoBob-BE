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
import com.pinkdumbell.cocobob.exception.ErrorResponse;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
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
import org.springframework.web.bind.annotation.PutMapping;
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

    @ApiOperation(value = "create daily record", notes = "데일리 기록 생성")
    @ApiResponses(value = {
        @ApiResponse(code = 200, message = "", response = DailyRecordRegisterResponseClass.class),
        @ApiResponse(code = 400, message = "", response = ErrorResponse.class),
        @ApiResponse(code = 500, message = "INTERNAL SERVER ERROR", response = ErrorResponse.class)
    })
    @PostMapping("/pets/{petId}")
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

    @ApiOperation(value = "get all daily record", notes = "데일리 기록 조회")
    @ApiResponses(value = {
        @ApiResponse(code = 200, message = "{\n"
            + "  \"status\": 200,\n"
            + "  \"code\": \"SUCCESS GET DAILY\",\n"
            + "  \"message\": \"데일리 기록을 불러오는데 성공하였습니다.\",\n"
            + "  \"data\": [\n"
            + "    {\n"
            + "      \"id\": 46,\n"
            + "      \"date\": \"2022-05-01\",\n"
            + "      \"feedAmount\": null,\n"
            + "      \"walkTotalTime\": null,\n"
            + "      \"walkDistance\": null,\n"
            + "      \"walkGps\": null,\n"
            + "      \"note\": \"25631512fhj\",\n"
            + "      \"images\": [\n"
            + "        {\n"
            + "          \"imagePath\": \"https://cocobob-storage.s3.ap-northeast-2.amazonaws.com/daily/6_2022-05-01_0\",\n"
            + "          \"dailyImageId\": 20\n"
            + "        }\n"
            + "      ]\n"
            + "    }\n"
            + "  ]\n"
            + "}"),
        @ApiResponse(code = 200, message = "", response = RecordDailyNoteGetResponseClass.class),
        @ApiResponse(code = 400, message = "", response = ErrorResponse.class),
        @ApiResponse(code = 500, message = "INTERNAL SERVER ERROR", response = ErrorResponse.class)
    })
    @ApiImplicitParams({
        @ApiImplicitParam(name = "startDate", dataType = "LocalDate", paramType = "query",
            value = "2022-01-01"),
        @ApiImplicitParam(name = "lastDate", dataType = "LocalDate", paramType = "query",
            value = "2022-12-31"),
    })
    @GetMapping("/pets/{petId}")
    public ResponseEntity<RecordDailyNoteGetResponseClass> getAllDailyRecord(
        @PathVariable("petId") Long petId,
        @Valid DailyRecordGetRequestDto dailyRecordGetRequestDto) {

        return ResponseEntity.ok(
            new RecordDailyNoteGetResponseClass(HttpStatus.OK.value(),
                "SUCCESS GET DAILY",
                "데일리 기록을 불러오는데 성공하였습니다.",
                dailyService.getDaily(petId, dailyRecordGetRequestDto)));
    }

    @ApiOperation(value = "get all simple daily record", notes = "데일리 기록 단순 날짜 조회")
    @ApiResponses(value = {
        @ApiResponse(code = 200, message = "{\n"
            + "  \"status\": 200,\n"
            + "  \"code\": \"SUCCESS GET SIMPLE DAILY\",\n"
            + "  \"message\": \"데일리 기록을 불러오는데 성공하였습니다.\",\n"
            + "  \"data\": [\n"
            + "    {\n"
            + "      \"date\": \"2022-08-08\",\n"
            + "      \"dailyId\": 58\n"
            + "    },\n"
            + "    {\n"
            + "      \"date\": \"2022-08-08\",\n"
            + "      \"dailyId\": 55\n"
            + "    }\n"
            + "  ]\n"
            + "}"),
        @ApiResponse(code = 200, message = "", response = SimpleDailyResponseClass.class),
        @ApiResponse(code = 400, message = "", response = ErrorResponse.class),
        @ApiResponse(code = 500, message = "INTERNAL SERVER ERROR", response = ErrorResponse.class)
    })
    @ApiImplicitParams({
        @ApiImplicitParam(name = "yearMonth", dataType = "LocalDate", paramType = "query",
            value = "2022-08"),
    })
    @GetMapping("/pets/simple/{petId}")
    public ResponseEntity<SimpleDailyResponseClass> getSimpleDaily(
        @PathVariable("petId") Long petId, @Valid DailySimpleRequestDto dailySimpleRequestDto) {

        return ResponseEntity.ok(
            new SimpleDailyResponseClass(HttpStatus.OK.value(),
                "SUCCESS GET SIMPLE DAILY",
                "데일리 기록을 불러오는데 성공하였습니다.",
                dailyService.getSimpleDaily(petId, dailySimpleRequestDto.getYearMonth())));
    }

    @ApiOperation(value = "update daily record", notes = "데일리 기록 수정")
    @ApiResponses(value = {
        @ApiResponse(code = 200, message = "", response = DailyRecordUpdateResponseClass.class),
        @ApiResponse(code = 400, message = "", response = ErrorResponse.class),
        @ApiResponse(code = 500, message = "INTERNAL SERVER ERROR", response = ErrorResponse.class)
    })
    @PatchMapping("/{dailyId}")
    public ResponseEntity<DailyRecordUpdateResponseClass> updateDailyRecord(
        @PathVariable("dailyId") Long dailyId, @ModelAttribute
    DailyRecordUpdateRequestDto dailyRecordUpdateRequestDto) {

        return ResponseEntity.ok(
            new DailyRecordUpdateResponseClass(HttpStatus.OK.value(),
                "SUCCESS UPDATE DAILY",
                "데일리 기록을 변경하는데 성공하였습니다.",
                dailyService.updateDailyRecord(dailyId, dailyRecordUpdateRequestDto)));
    }

    @ApiOperation(value = "delete daily record", notes = "데일리 기록 삭제")
    @ApiResponses(value = {
        @ApiResponse(code = 200, message = "", response = CommonResponseDto.class),
        @ApiResponse(code = 400, message = "", response = ErrorResponse.class),
        @ApiResponse(code = 500, message = "INTERNAL SERVER ERROR", response = ErrorResponse.class)
    })
    @DeleteMapping("/{dailyId}")
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

    @ApiOperation(value = "view detail daily record", notes = "데일리 상세 기록 조회")
    @ApiResponses(value = {
        @ApiResponse(code = 200, message = "", response = DailyRecordDetailResponseClass.class),
        @ApiResponse(code = 400, message = "", response = ErrorResponse.class),
        @ApiResponse(code = 500, message = "INTERNAL SERVER ERROR", response = ErrorResponse.class)
    })
    @GetMapping("/{dailyId}")
    public ResponseEntity<DailyRecordDetailResponseClass> getDailyDetailRecord(
        @PathVariable("dailyId") Long dailyId) {

        return ResponseEntity.ok(new DailyRecordDetailResponseClass(
            HttpStatus.OK.value(),
            "SUCCESS GET DAILY",
            "데일리 기록을 불러오는데 성공하였습니다.",
            dailyService.getDailyDetailRecord(dailyId)));
    }

    @ApiOperation(value = "delete daily image", notes = "데일리 이미지 삭제")
    @ApiResponses(value = {
        @ApiResponse(code = 200, message = "", response = CommonResponseDto.class),
        @ApiResponse(code = 400, message = "", response = ErrorResponse.class),
        @ApiResponse(code = 500, message = "INTERNAL SERVER ERROR", response = ErrorResponse.class)
    })
    @DeleteMapping("/{dailyId}/images/{dailyimageId}")
    public ResponseEntity<CommonResponseDto> deleteDailyImage(
        @PathVariable("dailyId") Long dailyId, @PathVariable("dailyimageId") Long dailyImageId) {

        dailyService.deleteDailyImage(dailyId, dailyImageId);

        return ResponseEntity.ok(CommonResponseDto.builder().
            status(HttpStatus.OK.value())
            .code("SUCCESS DELETE DAILY IMAGE")
            .message("데일리 이미지를 삭제하는데 성공하였습니다.")
            .data(null)
            .build());
    }


}
