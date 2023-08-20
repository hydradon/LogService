package com.quang.LogService.service;

import com.quang.LogService.model.BaseLogResponse;
import com.quang.LogService.reader.LogFileReader;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.sql.Timestamp;

/**
 * This is the service layer of the microservice. It invokes the utility class to read the log file.
 */
@Slf4j
@Component
@RequiredArgsConstructor
public class FileReaderService {

    private final LogFileReader logFileReader;

    /**
     * Invokes the utility class with the approach that uses the Apache Common IO ReversedLinesFileReader.
     */
    public BaseLogResponse readLogLines(String fileName, int numLines, String searchText) {

        try {
            return BaseLogResponse.builder()
                    .asOf(new Timestamp(System.currentTimeMillis()))
                    .searchText(searchText)
                    .logLines(logFileReader.readLogFile(fileName, numLines, searchText))
                    .build();

        } catch (Exception e) {
            log.warn("Error reading log lines: " + e.getMessage());
            return BaseLogResponse.builder()
                    .asOf(new Timestamp(System.currentTimeMillis()))
                    .searchText(searchText)
                    .errorMessage(e.getMessage())
                    .build();
        }
    }

    /**
     * Invokes the utility class with the approach that uses custom implementation.
     */
    public BaseLogResponse readLogLines2(String fileName, int numLines, String searchText) {

        try {
            return BaseLogResponse.builder()
                    .asOf(new Timestamp(System.currentTimeMillis()))
                    .searchText(searchText)
                    .logLines(logFileReader.readLogFile2(fileName, numLines, searchText))
                    .build();

        } catch (Exception e) {
            log.warn("Error reading log lines: " + e.getMessage());
            return BaseLogResponse.builder()
                    .asOf(new Timestamp(System.currentTimeMillis()))
                    .searchText(searchText)
                    .errorMessage(e.getMessage())
                    .build();
        }
    }

}
