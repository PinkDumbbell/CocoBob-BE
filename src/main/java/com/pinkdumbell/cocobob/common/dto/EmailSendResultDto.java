package com.pinkdumbell.cocobob.common.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

@Getter
@AllArgsConstructor
@Builder
public class EmailSendResultDto {

    private int status;
}
