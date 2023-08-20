package com.quang.LogService.service;

import com.quang.LogService.model.BaseLogResponse;
import com.quang.LogService.reader.LogFileReader;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.io.FileNotFoundException;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.Mockito.lenient;

@ExtendWith(MockitoExtension.class)
class FileReaderServiceTest {

    @Mock
    private LogFileReader logFileReader;

    @InjectMocks
    FileReaderService fileReaderService;

    private static final String MOCK_FILE_PATH = "mock_path";

    @Test
    public void testReadFile_Ideal() throws Exception {
        lenient().when(logFileReader.readLogFile2(any(), anyInt(), any()))
                .thenReturn(List.of("log line 1", "log line 2", "log_line 3"));

        BaseLogResponse baseLogResponse = fileReaderService.readLogLines2(MOCK_FILE_PATH, 5, "");
        assertEquals(3, baseLogResponse.getLogLines().size());
        assertNull(baseLogResponse.getErrorMessage());
    }

    @Test
    public void testReadFileWithException() throws Exception {
        lenient().when(logFileReader.readLogFile2(any(), anyInt(), any()))
                .thenThrow(new FileNotFoundException("File not found"));

        BaseLogResponse baseLogResponse = fileReaderService.readLogLines2(MOCK_FILE_PATH, 5, "");
        assertEquals("File not found", baseLogResponse.getErrorMessage());
        assertNull(baseLogResponse.getLogLines());
    }
}