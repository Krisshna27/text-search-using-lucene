package com.solution.textsearch.exception;

/**
 * This exception will be thrown if the supplied file is null
 * or file didn't exist from the upload location
 *
 * @author Krisshna Kumar Mone
 */
public class FileParseException extends RuntimeException{

    public FileParseException(String message) {
        super(message);
    }
}
