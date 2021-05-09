package com.solution.textsearch.exception;

/**
 * This exception will be thrown if access to Lucene's index folder has failed
 *
 * @author Krisshna Kumar Mone
 */
public class IndexDirectoryReadException extends RuntimeException{

    public IndexDirectoryReadException(String message) {
        super(message);
    }
}
