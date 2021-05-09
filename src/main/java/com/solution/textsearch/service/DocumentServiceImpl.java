package com.solution.textsearch.service;

import com.solution.textsearch.exception.IndexDirectoryReadException;
import com.solution.textsearch.util.LuceneUtil;
import lombok.extern.slf4j.Slf4j;
import org.apache.lucene.analysis.Analyzer;
import org.apache.lucene.store.Directory;
import org.springframework.stereotype.Service;

import java.io.IOException;

/**
 * DocumentService will pass the supplied document to Apache Lucene in order to index
 *
 * @author Krisshna Kumar Mone
 * @see LuceneUtil
 * @see SearchService
 */
@Slf4j
@Service
public class DocumentServiceImpl implements DocumentService {

    private final Analyzer analyzer;

    private final Directory indexDirectory;

    public DocumentServiceImpl(Analyzer analyzer, Directory indexDirectory) {
        this.analyzer = analyzer;
        this.indexDirectory = indexDirectory;
    }

    @Override
    public void parseAndAddToIndex(String file, String fileName) {
        try {
            var luceneDocument = LuceneUtil.prepareLuceneDocument(file, fileName);
            var writer = LuceneUtil.getIndexWriter(indexDirectory, analyzer);
            LuceneUtil.addDocumentToIndex(writer, luceneDocument);
            writer.close();
        } catch (IOException e) {
            log.warn("Exception in reading file. cause : {}", e.getMessage());
            throw new IndexDirectoryReadException(e.getMessage());
        }
    }
}
