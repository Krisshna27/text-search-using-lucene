package com.solution.textsearch.exception;

import com.solution.textsearch.model.ErrorResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpEntity;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import java.time.LocalDateTime;

import static org.springframework.http.HttpStatus.*;

/**
 * Exception handler to intercept all the application exceptions
 *
 * @author Krisshna Kumar Mone
 */
@Slf4j
@ControllerAdvice
public class TextSearchExceptionHandler {

    @ExceptionHandler(FileParseException.class)
    public ResponseEntity<ErrorResponse> handleFileParseException(FileParseException exception){
        log.error("IO error in parsing file: {}", exception.getMessage());
        var errorResponse = buildErrorResponse(exception.getMessage(), BAD_REQUEST.value());
        return new ResponseEntity<>(errorResponse, BAD_REQUEST);
    }

    @ExceptionHandler(IndexDirectoryReadException.class)
    public ResponseEntity<ErrorResponse> handleIndexDirectoryReadException(IndexDirectoryReadException exception){
        log.error("Exception in reading file. cause : {}", exception.getMessage());
        var errorResponse = buildErrorResponse(exception.getMessage(), INTERNAL_SERVER_ERROR.value());
        return new ResponseEntity<>(errorResponse, INTERNAL_SERVER_ERROR);
    }

    @ExceptionHandler(Throwable.class)
    public HttpEntity<ErrorResponse> handleGlobalException(Throwable exception) {
        log.error("Handler rest of error cases. Exception : {}", exception.getMessage());
        var errorResponse = buildErrorResponse(exception.getMessage(), SERVICE_UNAVAILABLE.value());
        return new ResponseEntity<>(errorResponse, SERVICE_UNAVAILABLE);
    }

    private ErrorResponse buildErrorResponse(String message, int errorCode) {
        return ErrorResponse.builder()
                .errorCode(errorCode)
                .errorMessage(message)
                .timeStamp(LocalDateTime.now().toString())
                .build();
    }
}
