package com.quang.LogService.reader;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.io.input.ReversedLinesFileReader;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Component;

import java.io.File;
import java.io.IOException;
import java.io.RandomAccessFile;
import java.util.ArrayList;
import java.util.List;

import static java.nio.charset.StandardCharsets.UTF_8;

@Slf4j
@Component
@RequiredArgsConstructor
public class LogFileReader {

    private final Environment env;

    public List<String> readLogFile(String fileName, int numLines, String searchText) throws Exception {
        try (ReversedLinesFileReader reader = new ReversedLinesFileReader(
                new File(env.getProperty("log-location.default") + fileName), UTF_8)) {
            return readNLines(reader, numLines, searchText);
        } catch (Exception e) {
            log.error("Error processing file: ", e);
            throw e;
        }
    }

    private List<String> readNLines(ReversedLinesFileReader reader, int n, String searchText) throws IOException {
        List<String> lines = new ArrayList<>();
        String line;
        while ((line = reader.readLine()) != null && n > 0) {
            if (searchText == null || line.contains(searchText)) {
                lines.add(line);
                n--;
            }
        }
        return lines;
    }

    public List<String> readLogFile2(String filePath, int n, String searchText) throws Exception {
        String absoluteFilePath = env.getProperty("log-location.default") + filePath;
        File file = new File(absoluteFilePath);
        StringBuilder builder = new StringBuilder();
        String line;
        List<String> ans = new ArrayList<>();
        try (RandomAccessFile randomAccessFile = new RandomAccessFile(absoluteFilePath, "r")) {
            long pos = file.length() - 1;
            randomAccessFile.seek(pos);

            for (long i = pos - 1; i >= 0; i--) {
                randomAccessFile.seek(i);
                char c = (char) randomAccessFile.read();
                if (c == '\n') { // finished reading a line
                    line = builder.reverse().toString();
                    if (searchText == null || line.contains(searchText)) {
                        ans.add(line);
                        n--;
                        if (n == 0) {
                            break;
                        }
                    }
                    builder.setLength(0); // reset and re-use String builder
                } else {
                    builder.append(c);
                }
            }
        } catch (Exception e) {
            log.error("Error processing file: ", e);
            throw e;
        }

        return ans;
    }
}
