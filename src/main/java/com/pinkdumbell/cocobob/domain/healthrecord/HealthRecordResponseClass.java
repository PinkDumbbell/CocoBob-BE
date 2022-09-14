package com.pinkdumbell.cocobob.domain.healthrecord;

import com.pinkdumbell.cocobob.common.dto.CommonResponseDto;
import com.pinkdumbell.cocobob.domain.healthrecord.dto.HealthRecordDetailResponseDto;

public class HealthRecordResponseClass {

    public static class HealthRecordDetailResponseClass extends
            CommonResponseDto<HealthRecordDetailResponseDto> {

        public HealthRecordDetailResponseClass(int status, String code, String message, HealthRecordDetailResponseDto data) {
            super(status, code, message, data);
        }
    }
}
