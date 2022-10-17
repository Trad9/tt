package com.yolo.testtask.rest.exception;

import lombok.Builder;
import lombok.Value;

import java.util.List;
import java.util.Map;

@Builder
@Value
public class ErrorResource {

    String type;
    Map<String, List<String>> messages;
}
