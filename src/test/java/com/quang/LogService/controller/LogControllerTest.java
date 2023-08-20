package com.quang.LogService.controller;

import com.quang.LogService.model.BaseLogResponse;
import com.quang.LogService.service.FileReaderService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class LogControllerTest {

    @Mock
    private FileReaderService fileReaderService;

    @InjectMocks
    LogController logController;

    @BeforeEach
    public void setUp() {
        lenient().doReturn(BaseLogResponse.builder().build())
                .when(fileReaderService).readLogLines(any(), anyInt(), any());
        lenient().doReturn(BaseLogResponse.builder().build())
                .when(fileReaderService).readLogLines2(any(), anyInt(), any());
    }

    @Test
    void getLogLines() {
        logController.getLogLines("MOCK_FILE", 5, "");
        verify(fileReaderService, times(1))
                .readLogLines("MOCK_FILE", 5, "");
    }

    @Test
    void getLogLines2() {
        logController.getLogLines2("MOCK_FILE", 5, "");
        verify(fileReaderService, times(1))
                .readLogLines2("MOCK_FILE", 5, "");
    }
}