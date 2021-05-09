package com.solution.textsearch.web.resource;

import com.solution.textsearch.exception.FileParseException;
import com.solution.textsearch.model.SearchResults;
import com.solution.textsearch.service.DocumentService;
import com.solution.textsearch.service.SearchService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

/**
 * Controller implementations to provide search based on file upload and text based searches
 * Look for supported media types in the interface declaration
 *
 * @see FileResource
 * @see SearchResults
 * @author Krisshna Kumar Mone
 */
@Slf4j
@RestController
public class FileResourceImpl implements FileResource{

    private static final String FILE_NAME = "DEFAULT";

    private final DocumentService documentService;

    private final SearchService searchService;

    public FileResourceImpl(DocumentService documentService, SearchService searchService) {
        this.documentService = documentService;
        this.searchService = searchService;
    }

    /**
     * Method to enable file upload based number extraction
     * @param file Multipart file uploaded as form data
     * @return SearchResults with numbers present in the file indexed using Lucene.
     */
    public ResponseEntity<List<SearchResults>> searchUsingFileUpload(@RequestParam("file") MultipartFile file){
        try {
            var content = new String(file.getInputStream().readAllBytes());
            documentService.parseAndAddToIndex(content, file.getOriginalFilename());
        } catch (IOException e) {
            log.warn("IO error in parsing file: {}", e.getMessage());
            throw new FileParseException(e.getMessage());
        }

        return new ResponseEntity<>(searchService.searchForContent(), HttpStatus.OK);
    }

    /**
     * Method to enable text based search.
     * @param content String to support plain text, html and xml searches
     * @return SearchResults with numbers present in the file indexed using Lucene.
     */
    public ResponseEntity<List<SearchResults>> searchUsingRequestBody(@RequestBody String content){
        documentService.parseAndAddToIndex(content, FILE_NAME);
        return new ResponseEntity<>(searchService.searchForContent(), HttpStatus.OK);
    }
}
