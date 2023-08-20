package com.quang.LogService.model;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Builder;
import lombok.Data;

import java.io.Serializable;
import java.sql.Timestamp;
import java.util.List;

@Builder
@Data
@JsonInclude(JsonInclude.Include.NON_NULL)
public class BaseLogResponse implements Serializable {

    Timestamp asOf;
    String searchText;
    List<String> logLines;
    String errorMessage;

}
