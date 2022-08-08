package com.pinkdumbell.cocobob.domain.daily;

import com.pinkdumbell.cocobob.domain.daily.dto.DailyNoteRequestDto;
import com.pinkdumbell.cocobob.domain.daily.dto.DailyNoteResponseDto;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class DailyService {

    public DailyNoteResponseDto recordNote(DailyNoteRequestDto dailyNoteRequestDto,Long petId,String userEmail){

        return new DailyNoteResponseDto(null);
    }

}
