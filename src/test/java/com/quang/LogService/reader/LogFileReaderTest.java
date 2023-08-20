package com.quang.LogService.reader;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.core.env.Environment;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class LogFileReaderTest {

    @Mock
    Environment environment;

    @InjectMocks
    LogFileReader logFileReader;

    private static final String FILE_NAME = "HDFS_2k_test.log";

    @BeforeEach
    public void setup() {
        when(environment.getProperty("log-location.default")).thenReturn("D:/Projects/LogService/logfiles/");
    }

    @Test
    void test_readLogFile_withNullFilter() throws Exception {
        List<String> results = logFileReader.readLogFile(FILE_NAME, 3, null);

        List<String> expectedResults = List.of(
                "081111 102017 26347 INFO dfs.DataNode$DataXceiver: Receiving block blk_4343207286455274569 src: /10.250.9.207:59759 dest: /10.250.9.207:50010",
                "081111 101954 26414 INFO dfs.DataNode$PacketResponder: PacketResponder 0 for block blk_5225719677049010638 terminating",
                "081111 101804 26494 INFO dfs.DataNode$DataXceiver: Receiving block blk_-295306975763175640 src: /10.250.9.207:53270 dest: /10.250.9.207:50010"
        );

        assertEquals(3, results.size());
        assertEquals(expectedResults, results);
    }

    @Test
    void test_readLogFile_withEmptyStringFilter() throws Exception {
        List<String> results = logFileReader.readLogFile(FILE_NAME, 3, "");

        List<String> expectedResults = List.of(
                "081111 102017 26347 INFO dfs.DataNode$DataXceiver: Receiving block blk_4343207286455274569 src: /10.250.9.207:59759 dest: /10.250.9.207:50010",
                "081111 101954 26414 INFO dfs.DataNode$PacketResponder: PacketResponder 0 for block blk_5225719677049010638 terminating",
                "081111 101804 26494 INFO dfs.DataNode$DataXceiver: Receiving block blk_-295306975763175640 src: /10.250.9.207:53270 dest: /10.250.9.207:50010"
        );

        assertEquals(3, results.size());
        assertEquals(expectedResults, results);
    }

    @Test
    void test_readLogFile_withFilter() throws Exception {
        List<String> results = logFileReader.readLogFile(FILE_NAME, 3, "PacketResponder");

        List<String> expectedResults = List.of(
                "081111 101954 26414 INFO dfs.DataNode$PacketResponder: PacketResponder 0 for block blk_5225719677049010638 terminating",
                "081111 101735 26595 INFO dfs.DataNode$PacketResponder: Received block blk_-5815145248455404269 of size 67108864 from /10.251.121.224",
                "081109 204106 329 INFO dfs.DataNode$PacketResponder: PacketResponder 2 for block blk_-6670958622368987959 terminating"
        );

        assertEquals(3, results.size());
        assertEquals(expectedResults, results);
    }

    @Test
    void test_readLogFile2_withNullFilter() throws Exception {
        List<String> results = logFileReader.readLogFile2(FILE_NAME, 3, null);

        List<String> expectedResults = List.of(
                "081111 102017 26347 INFO dfs.DataNode$DataXceiver: Receiving block blk_4343207286455274569 src: /10.250.9.207:59759 dest: /10.250.9.207:50010",
                "081111 101954 26414 INFO dfs.DataNode$PacketResponder: PacketResponder 0 for block blk_5225719677049010638 terminating",
                "081111 101804 26494 INFO dfs.DataNode$DataXceiver: Receiving block blk_-295306975763175640 src: /10.250.9.207:53270 dest: /10.250.9.207:50010"
        );

        assertEquals(3, results.size());
        assertEquals(expectedResults, results);
    }

    @Test
    void test_readLogFile2_withEmptyStringFilter() throws Exception {
        List<String> results = logFileReader.readLogFile2(FILE_NAME, 3, "");

        List<String> expectedResults = List.of(
                "081111 102017 26347 INFO dfs.DataNode$DataXceiver: Receiving block blk_4343207286455274569 src: /10.250.9.207:59759 dest: /10.250.9.207:50010",
                "081111 101954 26414 INFO dfs.DataNode$PacketResponder: PacketResponder 0 for block blk_5225719677049010638 terminating",
                "081111 101804 26494 INFO dfs.DataNode$DataXceiver: Receiving block blk_-295306975763175640 src: /10.250.9.207:53270 dest: /10.250.9.207:50010"
        );

        assertEquals(3, results.size());
        assertEquals(expectedResults, results);
    }

    @Test
    void test_readLogFile2_withFilter() throws Exception {
        List<String> results = logFileReader.readLogFile2(FILE_NAME, 3, "PacketResponder");

        List<String> expectedResults = List.of(
                "081111 101954 26414 INFO dfs.DataNode$PacketResponder: PacketResponder 0 for block blk_5225719677049010638 terminating",
                "081111 101735 26595 INFO dfs.DataNode$PacketResponder: Received block blk_-5815145248455404269 of size 67108864 from /10.251.121.224",
                "081109 204106 329 INFO dfs.DataNode$PacketResponder: PacketResponder 2 for block blk_-6670958622368987959 terminating"
        );

        assertEquals(3, results.size());
        assertEquals(expectedResults, results);
    }
}