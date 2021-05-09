package com.solution.textsearch.web.resource;

import com.solution.textsearch.model.ErrorResponse;
import com.solution.textsearch.model.SearchResults;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@Tag(name = "Number search API", description = "Search numbers from uploaded files or plain text through response body")
@RequestMapping("/documents")
public interface FileResource {

    @Operation(summary = "Number search through file upload", method = "POST", description = "Number search from uploaded file")
    @ApiResponse(responseCode = "200", description = "Successfully extracted numbers",
            content = @Content(schema = @Schema(implementation = SearchResults.class)))
    @ApiResponse(responseCode = "500", description = "All global errors",
            content = @Content(schema = @Schema(implementation = ErrorResponse.class)))
    @ApiResponse(responseCode = "400", description = "Bad request, file uploaded doesn't exist",
            content = @Content(schema = @Schema(implementation = ErrorResponse.class)))
    @ApiResponse(responseCode = "503", description = "This error will be thrown if the application is not able to access lucene Index directories",
            content = @Content(schema = @Schema(implementation = ErrorResponse.class)))
    @PostMapping(value = "/files/search", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    ResponseEntity<List<SearchResults>> searchUsingFileUpload(@RequestParam("file") MultipartFile file);

    @Operation(summary = "Number search through request body", method = "POST", description = "Number search from TEXT/PLAIN, TEXT/HTML, TEXT/XML ")
    @ApiResponse(responseCode = "200", description = "Successfully extracted numbers",
            content = @Content(schema = @Schema(implementation = SearchResults.class)))
    @ApiResponse(responseCode = "500", description = "All global errors",
            content = @Content(schema = @Schema(implementation = ErrorResponse.class)))
    @ApiResponse(responseCode = "400", description = "Bad request, file uploaded doesn't exist",
            content = @Content(schema = @Schema(implementation = ErrorResponse.class)))
    @ApiResponse(responseCode = "503", description = "The application is not able to access lucene Index directories",
            content = @Content(schema = @Schema(implementation = ErrorResponse.class)))
    @PostMapping(value = "/text/search", consumes = {MediaType.TEXT_XML_VALUE, MediaType.TEXT_PLAIN_VALUE, MediaType.TEXT_HTML_VALUE})
    ResponseEntity<List<SearchResults>> searchUsingRequestBody(@RequestBody String file);
}
