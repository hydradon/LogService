package com.quang.LogService.model;

import lombok.Builder;

import java.util.List;

@Builder
public record BaseLogResponse(long id, List<String> logLines) {}
