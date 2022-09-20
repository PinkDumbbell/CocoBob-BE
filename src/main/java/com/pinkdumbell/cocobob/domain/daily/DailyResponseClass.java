package com.pinkdumbell.cocobob.domain.daily;

import com.pinkdumbell.cocobob.common.dto.CommonResponseDto;
import com.pinkdumbell.cocobob.domain.daily.dto.DailyDetailResponseDto;

public class DailyResponseClass {

    public static class DailyDetailResponseClass extends
            CommonResponseDto<DailyDetailResponseDto> {

        public DailyDetailResponseClass(int status, String code, String message, DailyDetailResponseDto data) {
            super(status, code, message, data);
        }
    }
}