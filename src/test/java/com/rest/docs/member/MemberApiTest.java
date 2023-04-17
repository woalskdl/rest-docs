package com.rest.docs.member;

import com.rest.docs.RestDocsConfiguration;
import io.micrometer.core.instrument.util.IOUtils;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.Import;
import org.springframework.core.io.ResourceLoader;
import org.springframework.http.MediaType;
import org.springframework.restdocs.RestDocumentationContextProvider;
import org.springframework.restdocs.RestDocumentationExtension;
import org.springframework.restdocs.mockmvc.MockMvcRestDocumentation;
import org.springframework.restdocs.mockmvc.RestDocumentationResultHandler;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.result.MockMvcResultHandlers;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import java.io.IOException;
import java.nio.charset.StandardCharsets;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
@ExtendWith(RestDocumentationExtension.class)
@Import(RestDocsConfiguration.class)
class MemberApiTest {

    /**
     * 1. Member 단일 조회 -> 완료
     * 2. Member 생성 -> 완료
     * 3. Member 수정 -> 완료
     * 4. Member 페이징 조회 ->
     */

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    protected RestDocumentationResultHandler restDocs;

    @Autowired
    private ResourceLoader resourceLoader;


    @BeforeEach
    void setUp(
            final WebApplicationContext context,
            final RestDocumentationContextProvider provider
    ) {
        this.mockMvc = MockMvcBuilders.webAppContextSetup(context)
                .apply(MockMvcRestDocumentation.documentationConfiguration(provider))
                .alwaysDo(MockMvcResultHandlers.print())
                .alwaysDo(restDocs)
                .build();
    }

    protected String readJson(final String path) throws IOException {
        return IOUtils.toString(resourceLoader.getResource("classpath:" + path).getInputStream(),
                StandardCharsets.UTF_8);
    }

    @Test
    public void member_page_test() throws Exception {
        mockMvc.perform(
                        get("/api/members")
                                .param("size", "10")
                                .param("page", "0")
                                .contentType(MediaType.APPLICATION_JSON)
                )
                .andDo(print())
                .andExpect(status().isOk());
    }

    @Test
    public void member_get() throws Exception {
        mockMvc.perform(
                        get("/api/members/{id}", 1L)
                                .contentType(MediaType.APPLICATION_JSON)
                )
                .andDo(print())
                .andExpect(status().isOk());

    }

    @Test
    public void member_create() throws Exception {
        mockMvc.perform(
                        post("/api/members")
                                .contentType(MediaType.APPLICATION_JSON)
                                .content("" +
                                        "{\n" +
                                        "  \"email\": \"bbb@bbb.com\",\n" +
                                        "  \"name\": \"bbb\"\n" +
                                        "}"
                                )
                )
                .andDo(print())
                .andExpect(status().isOk());
    }

    @Test
    public void member_modify() throws Exception {
        mockMvc.perform(
                        put("/api/members/{id}", 1L)
                                .contentType(MediaType.APPLICATION_JSON)
                                .content("" +
                                        "{\n" +
                                        "  \"name\": \"new_jam\"\n" +
                                        "}"
                                )
                )
                .andDo(print())
                .andExpect(status().isOk());
    }
}