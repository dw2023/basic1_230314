package com.ll.basic1.boundedContext.member.service;

import com.ll.basic1.base.rsData.RsData;
import com.ll.basic1.boundedContext.member.entity.Member;
import com.ll.basic1.boundedContext.member.repository.MemberRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

// @Component : 아래 클래스의 객체는 Ioc 컨테이너(일명 공용도구상자)에 의해 생사소멸 관리된다.
// @Service : @Component 와 같은 의미, 가독성 때문에 이렇게 표기
@Service
@AllArgsConstructor // 생성자 자동생성
public class MemberService {
    private final MemberRepository memberRepository;

    /* @Repository 때문에 아래 생성자로 직접 객체생성 없이 만들어질 수 있으나, 그마저도 @AllArgsConstructor로 생략됨
    public MemberService(MemberRepository memberRepository) {
        this.memberRepository = MemberRepository;
    }
     */

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
