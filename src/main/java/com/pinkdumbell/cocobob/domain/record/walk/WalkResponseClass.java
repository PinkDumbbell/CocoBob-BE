package com.pinkdumbell.cocobob.domain.record.walk;

import com.pinkdumbell.cocobob.common.dto.CommonResponseDto;
import com.pinkdumbell.cocobob.domain.record.walk.dto.WalkDetailResponseDto;
import com.pinkdumbell.cocobob.domain.record.walk.dto.WalksBriefInfoDto;

public class WalkResponseClass {

    public static class WalkDetailResponseClass extends
            CommonResponseDto<WalkDetailResponseDto>  {
        public WalkDetailResponseClass(int status, String code, String message, WalkDetailResponseDto data) {
            super(status, code, message, data);
        }
    }

    public static class WalksBriefInfoClass extends
            CommonResponseDto<WalksBriefInfoDto> {

        public WalksBriefInfoClass(int status, String code, String message, WalksBriefInfoDto data) {
            super(status, code, message, data);
        }
    }
}
