package com.pinkdumbell.cocobob;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class TestController {
    @Value("${server.port}")
    private String portNum;

    @GetMapping("")
    public String test() {
        return "portNum is" + portNum;
    }
}
