package com.solution.textsearch.config;

import lombok.extern.slf4j.Slf4j;
import org.apache.lucene.analysis.Analyzer;
import org.apache.lucene.analysis.standard.StandardAnalyzer;
import org.apache.lucene.store.Directory;
import org.apache.lucene.store.FSDirectory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.io.File;
import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;

/**
 * Configuration for apache lucene's index directory and context analyzer config
 *
 * @author Krisshna Kumar Mone
 */
@Configuration
@Slf4j
public class LuceneConfig {

    private static final String LUCENEINDEXPATH="lucene/indexDir/";

    @Bean
    public Analyzer analyzer() {
        return new StandardAnalyzer();
    }

    @Bean
    public Directory directory() throws IOException {

        var path = Paths.get(LUCENEINDEXPATH);
        var file = path.toFile();
        if(!file.exists()) {
            // If the folder does not exist, create
            file.mkdirs();
        }
        return FSDirectory.open(path);
    }
}
