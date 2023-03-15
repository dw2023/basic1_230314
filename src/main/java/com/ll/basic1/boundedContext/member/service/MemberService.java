package com.ll.basic1.boundedContext.member.service;

import com.ll.basic1.base.rsData.RsData;

public class MemberService {
    public RsData tryLogin(String username, String password) {
        // RsData = boolean + 알파
        // 만약 리턴타입을 boolean을 써서 true/false만 나오면 성공실패여부는 알지만 그 이유를 모름
        // 여러가지 성공실패 케이스의 자세한 이유(msg)를 출력하기 위해 RsData 타입 사용

        // 비슷한 3개의 응답을 처리하기 위해 RsData 클래스를 만듦
        if (!password.equals("1234")) {
            return RsData.of("F-1", "비밀번호가 일치하지 않습니다.");
            // RsData.of == new RsData
            // of: 객체생성 안하고 사용하므로 static
        } else if (!username.equals("user1")) { // username이 user1일 때
            return RsData.of("F-2", "%s(은)는 존재하지 않는 회원입니다.".formatted(username));
        }

        return RsData.of("S-1", "%s 님 환영합니다.".formatted(username));
    }
}
