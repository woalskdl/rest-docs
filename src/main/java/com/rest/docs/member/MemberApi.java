package com.rest.docs.member;

import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RestController
@RequestMapping("/api/members")
@RequiredArgsConstructor
public class MemberApi {

    private final MemberRepository memberRepository;

    /**
     * 1. Member 단일 조회
     * 2. Member 생성
     * 3. Member 수정
     * 4. Member 페이징 조회
     */

    @GetMapping("/{id}")
    public MemberResponse getMember(@PathVariable Long id) {
        return new MemberResponse(memberRepository.findById(id).orElseThrow(() -> new IllegalArgumentException("Not found")));
    }

    @PostMapping
    public void createMember(@RequestBody @Valid MemberSignUpRequest dto) {
        memberRepository.save(dto.toEntity());
    }

    @PutMapping("/{id}")
    public void modify(@PathVariable Long id,
                       @RequestBody @Valid MemberModificationRequest dto) {
        final Member member = memberRepository.findById(id).get();
        member.modify(dto.getName());
        memberRepository.save(member);
    }

    @GetMapping
    public Page<MemberResponse> getMemebers (
            @PageableDefault(sort = "id", direction = Sort.Direction.DESC) Pageable pageable
    ) {
        return memberRepository.findAll(pageable).map(MemberResponse::new);
    }

}
