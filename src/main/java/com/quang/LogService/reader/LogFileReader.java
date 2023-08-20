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

/**
 * This class is a Utility class that handles logic to read file and search for matching texts
 * The base directory of the file is retrieved from environment properties "log-location.default"
 */
@Slf4j
@Component
@RequiredArgsConstructor
public class LogFileReader {

    private final Environment env;

    /**
     * This method reads log file using Apache Common IO's ReversedLinesFileReader that reads
     * a file line by line from the end.
     *
     * @param fileName the file name to be read from, the based location is stored in "log-location.default"
     * @param numLines specifies how many lines to we want to retrieve from the end of file.
     *                 if numLines > actual number of log lines in file, we return as many as we can find.
     * @param searchText specifies a text that a log line contains
     * @return a list of matching log lines
     * @throws Exception when there is error processing log file
     */
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

    /**
     * This method reads log file using custom implementation using RandomAccessFile. It seeks to the end of the file
     * and reads backwards from there. If it encounters the end of line, it reverses the current result (since we
     * read backwards), performs text search, and if text is found, it appends to current result.
     *
     * @param fileName the file name to be read from, the based location is stored in "log-location.default"
     * @param numLines specifies how many lines to we want to retrieve from the end of file.
     *                 if numLines > actual number of log lines in file, we return as many as we can find.
     * @param searchText specifies a text that a log line contains
     * @return a list of matching log lines
     * @throws Exception when there is error processing log file
     */
    public List<String> readLogFile2(String fileName, int numLines, String searchText) throws Exception {
        String absoluteFilePath = env.getProperty("log-location.default") + fileName;
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
                        numLines--;
                        if (numLines == 0) {
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
