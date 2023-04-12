package com.rest.docs;

import com.rest.docs.member.Member;
import com.rest.docs.member.MemberRepository;
import lombok.AllArgsConstructor;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Component
@AllArgsConstructor
public class DataSetup implements ApplicationRunner {

    private final MemberRepository memberRepository;

    @Override
    public void run(ApplicationArguments args) throws Exception {

        final List<Member> members = new ArrayList<>();

        members.add(new Member("jay1@gmail.com", "jay1"));
        members.add(new Member("jay2@gmail.com", "jay2"));
        members.add(new Member("jay3@gmail.com", "jay3"));
        members.add(new Member("jay4@gmail.com", "jay4"));

        memberRepository.saveAll(members);
    }
}
