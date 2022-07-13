package com.pinkdumbell.cocobob;

import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import org.springframework.core.env.Environment;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Arrays;
import java.util.List;

@RestController
@RequiredArgsConstructor
@ApiOperation(
        value = "Profile API",
        notes = "무중단 배포를 위한 프로파일에 관한 컨트롤러"
)
public class ProfileController {

    private final Environment env;

    @ApiOperation(
            value = "Return Current Profile",
            notes = "현재 사용되는 프로파일을 반환한다."
    )
    @GetMapping("")
    public String getCurrentProfile () {
        List<String> profile = Arrays.asList(env.getActiveProfiles());
        List<String> realProfiles = Arrays.asList("real1", "real2");
        String defaultProfile = profile.isEmpty() ? "default" : profile.get(0);

        return profile.stream()
                .filter(realProfiles::contains)
                .findAny()
                .orElse(defaultProfile);
    }
}
