package com.pinkdumbell.cocobob;

import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
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
    public String getCurrentProfile() {
        List<String> profile = Arrays.asList(env.getActiveProfiles());
        List<String> realProfiles = Arrays.asList("real1", "real2");
        String defaultProfile = profile.isEmpty() ? "default" : profile.get(0);

        return profile.stream()
            .filter(realProfiles::contains)
            .findAny()
            .orElse(defaultProfile);
    }

    @ApiOperation(
        value = "check access token",
        notes = "사용중인 access token이 유효한지 테스트하는 페이지"
    )
    @ApiImplicitParams({@ApiImplicitParam(name = "Authorization", value = "access Token", required = true, dataType = "string", paramType = "header")})
    @GetMapping("/hello")
    public String hello() {
        return "Hello";
    }

    @ApiOperation(
        value = "check access authority",
        notes = "접근하는 사람의 권한이 ADMIN인지 테스트하는 페이지"
    )
    @ApiImplicitParams({@ApiImplicitParam(name = "Authorization", value = "access Token", required = true, dataType = "string", paramType = "header")})
    @GetMapping("/admin")
    public String admin() {
        return "ADMIN";
    }

    @ApiOperation(
        value = "check access authority",
        notes = "접근하는 사람의 권한이 USER인지 테스트하는 페이지"
    )
    @ApiImplicitParams({@ApiImplicitParam(name = "Authorization", value = "access Token", required = true, dataType = "string", paramType = "header")})
    @GetMapping("/manager")
    public String usertest() {
        return "USERTEST";
    }
}
