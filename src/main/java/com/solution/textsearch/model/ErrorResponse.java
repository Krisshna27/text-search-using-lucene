package com.solution.textsearch.model;

import lombok.Builder;
import lombok.Getter;

/**
 * Application level error response model
 *
 * @author Krisshna Kumar Mone
 */
@Getter
@Builder
public class ErrorResponse {
    private final int errorCode;

    private final String timeStamp;

    private final String errorMessage;
}
