package com.solution.textsearch.model;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Builder;
import lombok.Getter;

/**
 * Success response model
 */
@Getter
@Builder
public class SearchResults {

    private final String searchResultKey;
    private final ResultPosition position;

    @Getter
    @Builder
    public static class ResultPosition {

        @JsonInclude(JsonInclude.Include.NON_NULL)
        private final Long lineNo;

        @JsonInclude(JsonInclude.Include.NON_NULL)
        private final Long stringPosition;

        @JsonInclude(JsonInclude.Include.NON_NULL)
        private final Long startingCharOffset;

        private final long resultLength;
    }
}
