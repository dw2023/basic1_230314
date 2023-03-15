package com.ll.basic1.boundedContext.member.service;

import com.ll.basic1.base.rsData.RsData;
import com.ll.basic1.boundedContext.member.entity.Member;
import com.ll.basic1.boundedContext.member.repository.MemberRepository;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

// @Component : 아래 클래스의 객체는 Ioc 컨테이너(일명 공용도구상자)에 의해 생사소멸 관리된다.
// @Service : @Component 와 같은 의미, 가독성 때문에 이렇게 표기
@Service
public class MemberService {
    private MemberRepository memberRepository;

    public MemberService() {
        memberRepository = new MemberRepository();
    }

    public RsData tryLogin(String username, String password) {
        Member member = memberRepository.findByUsername(username);

        if (member == null) {
            return RsData.of("F-2", "%s(은)는 존재하지 않는 회원입니다.".formatted(username));
        }

        if (!member.getPassword().equals(password)) {
            return RsData.of("F-1", "비밀번호가 일치하지 않습니다.");
        }

        return RsData.of("S-1", "%s 님 환영합니다.".formatted(username));
    }

    // HomeController가 findByUsername 메서드를 호출했으나
    // Controller는 Repository에게 바로 명령할 수 없고 MemberService에 findByUsername 메서드가 없으므로 생성
    public Member findByUsername(String username) {
        return memberRepository.findByUsername(username);
    }
}
