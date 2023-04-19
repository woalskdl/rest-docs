package com.rest.docs;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ArrayNode;
import com.fasterxml.jackson.databind.node.ObjectNode;
import com.rest.docs.member.MemberStatus;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotEmpty;
import java.util.EnumSet;

@RestController
@RequestMapping("/test")
public class RestDocumentApi {

    private final ObjectMapper mapper;

    public RestDocumentApi(ObjectMapper mapper) {
        this.mapper = mapper;
    }

    /**
     * 1. Test Controller (RestDocs 문서용)에 Member Status Enum API 를 생성
     * 2. Test Controller 에 대한 테스트, 코드 작성 > Member Status enum snippet 파일 생성
     * 3. Member Status Enum snippet 기반으로 Document 작성
     * @param dto
     */

    @PostMapping("/sample")
    public void sample(@RequestBody @Valid SampleRequest dto) {

    }

    @GetMapping("/memberStatus")
    public ArrayNode getMemberStatus() {
        final ArrayNode arrayNode = mapper.createArrayNode();
        final EnumSet<MemberStatus> types = EnumSet.allOf(MemberStatus.class);

        for (final MemberStatus type : types) {
            final ObjectNode node = mapper.createObjectNode();
            node.put("MemberStatus", type.name());
            node.put("Description", type.getDescription());
            arrayNode.add(node);
        }

        return arrayNode;
    }

    public static class SampleRequest {
        @NotEmpty
        private String name;
        @Email
        private String email;

        public SampleRequest(String name, String email) {
            this.name = name;
            this.email = email;
        }
    }

}
