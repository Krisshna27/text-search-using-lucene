package com.solution.textsearch;

import com.solution.textsearch.web.resource.FileResource;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

@SpringBootTest
class TextSearchApplicationTests {

    @Autowired
    FileResource fileResource;

    @Test
    void contextLoads() {
        assertThat(fileResource).isNotNull();
    }

}
