package com.quang.LogService.controller;


import com.quang.LogService.model.BaseLogResponse;
import com.quang.LogService.reader.LogFileReader;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.Collections;
import java.util.concurrent.atomic.AtomicLong;

@Slf4j
@RestController
@RequiredArgsConstructor
public class LogController {

    private final LogFileReader logFileReader;

    private final AtomicLong counter = new AtomicLong();

    @GetMapping("/log")
    public BaseLogResponse getLog(@RequestParam(value = "name", defaultValue = "World") String name) {
        return BaseLogResponse.builder()
                .id(counter.incrementAndGet())
                .logLines(Collections.singletonList(String.format("Hello, %s!", name)))
                .build();
    }

    @GetMapping("/logLines")
    public BaseLogResponse getLogLines(@RequestParam String fileName,
                                       @RequestParam(defaultValue = "5") Integer numLines) {
        return BaseLogResponse.builder()
                .id(counter.incrementAndGet())
                .logLines(logFileReader.readLogFile(fileName, numLines))
                .build();
    }

}
