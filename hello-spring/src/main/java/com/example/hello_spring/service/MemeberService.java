package com.example.hello_spring.service;


import com.example.hello_spring.domain.Member;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class MemeberService {

    /**
     * 회원 가입
     */
    public long join(Member member) { // 회원 가입 메서드
        long start = System.currentTimeMillis();

        try {
            validateDublicateMember(member);
            memberRepository.save(member);

            return member.getId();
        } finally {
            long end = System.currentTimeMillis();
            long timeMs = end - start;

            System.out.println("join = " + timeMs + " ms");
        }
    }

    /**
     * 전체 회원 조회
     */
    public List<Member> findMembers() {
        long start = System.currentTimeMillis();

        try {
            return memberRepository.findAll();
        } finally {
            long end = System.currentTimeMillis();
            long timeMs = end - start;

            System.out.println("findMembers = " + timeMs + " ms");
        }
    }
}
