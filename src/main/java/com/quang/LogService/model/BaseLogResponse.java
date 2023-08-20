package com.quang.LogService.model;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Builder;
import lombok.Data;

import java.io.Serializable;
import java.sql.Timestamp;
import java.util.List;

/**
 * The Java pojo model to store the final response
 */
@Builder
@Data
@JsonInclude(JsonInclude.Include.NON_NULL)
public class BaseLogResponse implements Serializable {

    // Generated timestamp at the point of creation of this model
    Timestamp asOf;

    // Storing the text to search in log line
    String searchText;

    // List of matching log lines, null if error/exception is raised
    List<String> logLines;

    // Error message when exception raised during log file reading
    String errorMessage;

}
