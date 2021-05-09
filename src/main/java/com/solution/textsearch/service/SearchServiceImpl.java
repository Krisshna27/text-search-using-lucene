package com.solution.textsearch.service;

import com.solution.textsearch.model.SearchResults;
import com.solution.textsearch.util.LuceneUtil;
import lombok.extern.slf4j.Slf4j;
import org.apache.lucene.store.Directory;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * SearchService will execute search query to do number based search using Apache Lucene
 *
 * @see DocumentService
 * @see LuceneUtil
 * @author Krisshna Kumar Mone
 */
@Service
@Slf4j
public class SearchServiceImpl implements SearchService {

    private final Directory directory;
    public SearchServiceImpl( Directory directory) {
        this.directory = directory;
    }

    @Override
    public List<SearchResults> searchForContent() {
     return LuceneUtil.executeSearch(directory);
    }


}
