package com.pinkdumbell.cocobob.domain.walk;

import com.pinkdumbell.cocobob.common.dto.CommonResponseDto;
import com.pinkdumbell.cocobob.domain.walk.dto.WalkDetailResponseDto;

public class WalkResponseClass {

    public static class WalkDetailResponseClass extends
            CommonResponseDto<WalkDetailResponseDto>  {
        public WalkDetailResponseClass(int status, String code, String message, WalkDetailResponseDto data) {
            super(status, code, message, data);
        }
    }
}
