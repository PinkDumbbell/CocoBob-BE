package com.pinkdumbell.cocobob.domain.record.daily;

import com.pinkdumbell.cocobob.common.dto.CommonResponseDto;
import com.pinkdumbell.cocobob.domain.record.daily.dto.DailyCreateResponseDto;
import com.pinkdumbell.cocobob.domain.record.daily.dto.DailyDetailResponseDto;

public class DailyResponseClass {

    public static class DailyDetailResponseClass extends
            CommonResponseDto<DailyDetailResponseDto> {

        public DailyDetailResponseClass(int status, String code, String message, DailyDetailResponseDto data) {
            super(status, code, message, data);
        }
    }

    public static class DailyCreateResponseClass extends
            CommonResponseDto<DailyCreateResponseDto> {
        public DailyCreateResponseClass(int status, String code, String message, DailyCreateResponseDto data) {
            super(status, code, message, data);
        }
    }
}
