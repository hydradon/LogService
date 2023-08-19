package com.quang.LogService.reader;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;


import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;

@Slf4j
@Component
@RequiredArgsConstructor
public class LogFileReader {
    private static int BUFFER_SIZE = 16384; // 16 KB
    private static String DEFAULT_LOCATION = "src/main/resources/logfiles/";

    public List<String> readLogFile(String fileName, int numLines) {
//        try (BufferedReader br = new BufferedReader(new FileReader(fileName), BUFFER_SIZE)) {
        try (BufferedReader br = Files.newBufferedReader(Paths.get(DEFAULT_LOCATION + fileName))) {
            return readNLines(br, numLines);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    private List<String> readNLines(BufferedReader bf, int n) throws IOException {
        List<String> lines = new ArrayList<>();
        String line;
        while ((line = bf.readLine()) != null && n > 0) {
            lines.add(line);
            n--;
        }

        return lines;
    }
}
