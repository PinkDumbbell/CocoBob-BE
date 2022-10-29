package com.pinkdumbell.cocobob.domain.record.healthrecord;

import com.pinkdumbell.cocobob.common.dto.CommonResponseDto;
import com.pinkdumbell.cocobob.domain.record.healthrecord.dto.HealthRecordDetailResponseDto;
import com.pinkdumbell.cocobob.domain.record.healthrecord.dto.RecentWeightsResponseDto;

public class HealthRecordResponseClass {

    public static class HealthRecordDetailResponseClass extends
            CommonResponseDto<HealthRecordDetailResponseDto> {

        public HealthRecordDetailResponseClass(int status, String code, String message, HealthRecordDetailResponseDto data) {
            super(status, code, message, data);
        }
    }

    public static class RecentWeightsResponseClass extends
            CommonResponseDto<RecentWeightsResponseDto> {

        public RecentWeightsResponseClass(int status, String code, String message, RecentWeightsResponseDto data) {
            super(status, code, message, data);
        }
    }
}
