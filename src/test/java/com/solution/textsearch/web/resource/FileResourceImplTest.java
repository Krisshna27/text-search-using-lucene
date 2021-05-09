package com.solution.textsearch.web.resource;

import com.solution.textsearch.exception.FileParseException;
import com.solution.textsearch.service.DocumentServiceImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;
import org.springframework.web.multipart.MultipartFile;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.multipart;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
class FileResourceImplTest {


    MockMvc mockMvc;

    @MockBean
    DocumentServiceImpl documentService;

    @Autowired
    private WebApplicationContext context;

    @BeforeEach
    void before(){
        mockMvc= MockMvcBuilders.webAppContextSetup(context).build();
    }

    @Test
    void testWithFile() throws Exception {
        MockMultipartFile file = new MockMultipartFile("file", "test.txt",
                MediaType.MULTIPART_FORM_DATA_VALUE, this.getClass().getResourceAsStream("test.txt"));
        mockMvc.perform(multipart("/documents/files/search").file(file)).andExpect(status().isOk());
    }
}
