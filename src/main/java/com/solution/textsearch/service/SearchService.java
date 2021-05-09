package com.solution.textsearch.service;

import com.solution.textsearch.model.SearchResults;

import java.util.List;

public interface SearchService {

    List<SearchResults> searchForContent();
}
