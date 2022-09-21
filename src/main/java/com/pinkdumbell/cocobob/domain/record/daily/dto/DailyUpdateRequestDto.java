package com.pinkdumbell.cocobob.domain.record.daily.dto;

import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Getter;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@Getter
@AllArgsConstructor
public class DailyUpdateRequestDto {
    @ApiModelProperty(notes = "추가된 사진")
    private List<MultipartFile> newImages;
    @ApiModelProperty(notes = "수정할 텍스트 기록")
    private String note;
    @ApiModelProperty(notes = "삭제할 사진의 아이디", example = "1,2,3,4(콤마로 구분하여 공백없이 스트링으로)")
    private List<Long> imageIdsToDelete;
}
