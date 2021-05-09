package com.solution.textsearch.util;

import com.solution.textsearch.exception.IndexDirectoryReadException;
import com.solution.textsearch.model.SearchResults;
import lombok.extern.slf4j.Slf4j;
import org.apache.lucene.analysis.Analyzer;
import org.apache.lucene.document.Document;
import org.apache.lucene.document.Field;
import org.apache.lucene.document.FieldType;
import org.apache.lucene.document.StringField;
import org.apache.lucene.index.*;
import org.apache.lucene.search.*;
import org.apache.lucene.store.Directory;
import org.apache.lucene.util.BytesRef;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import static com.solution.textsearch.common.Constants.CONTENTS;

/**
 * Utility class to work with Lucene library methods.
 *
 * This class is implemented based on facade design pattern
 *
 * @author Krisshna Kumar Mone
 */
@Slf4j
public class LuceneUtil {

    private LuceneUtil() {
    }

    public static IndexWriter getIndexWriter(Directory indexDirectory, Analyzer analyzer) throws IOException {
        var indexWriterConfig = new IndexWriterConfig(analyzer);
        return new IndexWriter(indexDirectory, indexWriterConfig);
    }

    public static void addDocumentToIndex(IndexWriter writer, Document document) throws IOException {
        writer.deleteAll();
        writer.addDocument(document);
        writer.commit();
        writer.close();
    }

    public static Document prepareLuceneDocument(String content, String fileName) {
        var type = new FieldType();
        type.setStoreTermVectors(true);
        type.setStoreTermVectorPositions(true);
        type.setStoreTermVectorOffsets(true);
        type.setIndexOptions(IndexOptions.DOCS);

        var luceneDocument = new Document();
        luceneDocument.add(new Field(CONTENTS, content, type));
        luceneDocument.add(new StringField("fileName", fileName, Field.Store.YES));

        return luceneDocument;
    }

    public static List<SearchResults> executeSearch(Directory indexDirectory) {
        try(IndexReader reader = DirectoryReader.open(indexDirectory)) {
            var searcher = new IndexSearcher(reader);
            var query = new RegexpQuery(new Term(CONTENTS, ".*\\d.*"));
            var collector = TopScoreDocCollector.create(10000, 0);
            searcher.search(query, collector);
            var hits = collector.topDocs().scoreDocs;
            return prepareSearchResponse(reader, hits);
        } catch (IOException e) {
            log.warn("Error in parsing the query. Cause : {}", e.getMessage());
            throw new IndexDirectoryReadException(e.getMessage());
        }
    }

    private static List<SearchResults> prepareSearchResponse(IndexReader reader, ScoreDoc[] hits) throws IOException {
        List<SearchResults> results = new ArrayList<>();
        for (ScoreDoc scoreDoc : hits) {
            var fields = reader.getTermVectors(scoreDoc.doc);
            reader.close();
            var terms = fields.terms(CONTENTS);
            TermsEnum te = terms.iterator();
            PostingsEnum docsAndPosEnum = null;
            BytesRef bytesRef;
            while ((bytesRef = te.next()) != null) {
                docsAndPosEnum = te.postings(docsAndPosEnum, PostingsEnum.ALL);

                int nextDoc = docsAndPosEnum.nextDoc();
                assert nextDoc != DocIdSetIterator.NO_MORE_DOCS;
                final long freq = docsAndPosEnum.freq();
                final long position = docsAndPosEnum.nextPosition();
                final long charOffset = docsAndPosEnum.startOffset();
                log.debug("position={}, charOffset={}, l={}, freq={}, searchResult={}", position, charOffset, bytesRef.length,
                        freq, bytesRef.utf8ToString());
                results.add(buildSearchResult(bytesRef.utf8ToString(), position, charOffset));
            }
        }
        return results;
    }

    private static SearchResults buildSearchResult(String searchResultKey, long position, long offset) {
        return SearchResults.builder().searchResultKey(searchResultKey)
                .position(buildResultPosition(position, offset, searchResultKey.length())).build();
    }

    private static SearchResults.ResultPosition buildResultPosition(long position, long offset, long length) {
        return SearchResults.ResultPosition.builder()
                .stringPosition(position)
                .startingCharOffset(offset)
                .resultLength(length).build();
    }
}
