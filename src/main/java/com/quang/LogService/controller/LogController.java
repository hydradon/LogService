package com.quang.LogService.controller;


import com.quang.LogService.model.BaseLogResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.concurrent.atomic.AtomicLong;

@Slf4j
@RestController
public class LogController {

    private final AtomicLong counter = new AtomicLong();

    @GetMapping("/log")
    public BaseLogResponse getLog(@RequestParam(value = "name", defaultValue = "World") String name) {
        return BaseLogResponse.builder()
                .id(counter.incrementAndGet())
                .content(String.format("Hello, %s!", name))
                .build();
    }

}
