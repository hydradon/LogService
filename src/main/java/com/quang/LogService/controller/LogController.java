package com.quang.LogService.controller;

import com.quang.LogService.model.BaseLogResponse;
import com.quang.LogService.service.FileReaderService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RestController
@RequiredArgsConstructor
public class LogController {

    private final FileReaderService fileReaderService;

    @GetMapping(
            value = "/logLines",
            produces = MediaType.APPLICATION_JSON_VALUE
    )
    public BaseLogResponse getLogLines(@RequestParam String fileName,
                                       @RequestParam(defaultValue = "5") Integer numLines,
                                       @RequestParam(defaultValue = "") String searchText) {
        return fileReaderService.readLogLines(fileName, numLines, searchText);
    }

    @GetMapping(
            value = "/logLines2",
            produces = MediaType.APPLICATION_JSON_VALUE
    )
    public BaseLogResponse getLogLines2(@RequestParam String fileName,
                                        @RequestParam(defaultValue = "5") Integer numLines,
                                        @RequestParam(defaultValue = "") String searchText) {
        return fileReaderService.readLogLines2(fileName, numLines, searchText);
    }

}
