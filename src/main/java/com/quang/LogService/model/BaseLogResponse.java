package com.quang.LogService.model;

import lombok.Builder;

@Builder
public record  BaseLogResponse(long id, String content) {}
