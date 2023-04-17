package com.rest.docs.member;

import com.rest.docs.TestSupport;
import org.junit.jupiter.api.Test;
import org.springframework.http.MediaType;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

class MemberApiTest extends TestSupport {

    /**
     * 1. Member 단일 조회 -> 완료
     * 2. Member 생성 -> 완료
     * 3. Member 수정 -> 완료
     * 4. Member 페이징 조회 ->
     */

    @Test
    public void member_page_test() throws Exception {
        mockMvc.perform(
                        get("/api/members")
                                .param("size", "10")
                                .param("page", "0")
                                .contentType(MediaType.APPLICATION_JSON)
                )
                .andExpect(status().isOk());
    }

    @Test
    public void member_get() throws Exception {
        mockMvc.perform(
                        get("/api/members/{id}", 1L)
                                .contentType(MediaType.APPLICATION_JSON)
                )
                .andExpect(status().isOk());

    }

    @Test
    public void member_create() throws Exception {
        mockMvc.perform(
                        post("/api/members")
                                .contentType(MediaType.APPLICATION_JSON)
                                .content(readJson("/json/member-api/member-create.json"))
                )
                .andExpect(status().isOk());
    }

    @Test
    public void member_modify() throws Exception {
        mockMvc.perform(
                        put("/api/members/{id}", 1L)
                                .contentType(MediaType.APPLICATION_JSON)
                                .content(readJson("/json/member-api/member-modify.json"))
                )
                .andExpect(status().isOk());
    }
}