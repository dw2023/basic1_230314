package com.ll.basic1.boundedContext.member.repository;

import com.ll.basic1.boundedContext.member.entity.Member;

import java.util.ArrayList;
import java.util.List;

public class MemberRepository {
    private List<Member> members;

    public MemberRepository() {
        members = new ArrayList<>();

        members.add(new Member("user1", "1234"));
        members.add(new Member("abc", "12345"));
        members.add(new Member("test", "12346"));
        members.add(new Member("love", "12347"));
        members.add(new Member("like", "12348"));
        members.add(new Member("giving", "12349"));
        members.add(new Member("thanks", "123410"));
        members.add(new Member("hello", "123411"));
        members.add(new Member("good", "123412"));
        members.add(new Member("peace", "123413"));
    }

    public Member findByUsername(String username) {
        return members // 전체 members에서 매개변수username과 같은 username을 가진 member 필터링
                .stream()
                .filter(m -> m.getUsername().equals(username))
                .findFirst()
                .orElse(null);
    }
}
