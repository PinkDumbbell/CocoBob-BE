package com.pinkdumbell.cocobob.domain.record;

import com.pinkdumbell.cocobob.common.dto.CommonResponseDto;
import com.pinkdumbell.cocobob.domain.record.dto.RecordExistResponseDto;
import com.pinkdumbell.cocobob.domain.record.dto.RecordsSimpleResponseDto;

import java.time.LocalDate;
import java.util.Map;

public class RecordResponseClass {

    public static class RecordExistResponseClass
            extends CommonResponseDto<Map<LocalDate, RecordExistResponseDto>> {
        public RecordExistResponseClass(
                int status,
                String code,
                String message,
                Map<LocalDate, RecordExistResponseDto> data
        ) {
            super(status, code, message, data);
        }
    }

    public static class RecordsSimpleResponseClass
            extends CommonResponseDto<RecordsSimpleResponseDto> {
        public RecordsSimpleResponseClass(
                int status,
                String code,
                String message,
                RecordsSimpleResponseDto data
        ) {
            super(status, code, message, data);
        }
    }
}
