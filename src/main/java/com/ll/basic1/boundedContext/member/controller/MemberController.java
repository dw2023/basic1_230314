package com.ll.basic1.boundedContext.member.controller;

import com.ll.basic1.base.rq.Rq;
import com.ll.basic1.base.rsData.RsData;
import com.ll.basic1.boundedContext.member.entity.Member;
import com.ll.basic1.boundedContext.member.service.MemberService;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
public class MemberController {
    // @Autowired
    // private MemberService memberService;
    // 아래 코드 대신 이것도 가능 (필드주입)
    private final MemberService memberService;

        // 생성자 주입
        @Autowired
        public MemberController(MemberService memberService) {
            this.memberService = memberService;
        }


    // 로그인 성공한 회원의 id를 쿠키 값으로 만듦
    @GetMapping("/member/login")
    @ResponseBody
    public RsData login(String username, String password, HttpServletRequest req, HttpServletResponse resp) {
        Rq rq = new Rq(req, resp);

        if ( username == null || username.trim().length() == 0 ) {
            return RsData.of("F-3", "username(을)를 입력해주세요.");
        }

        if ( password == null || password.trim().length() == 0 ) {
            return RsData.of("F-4", "password(을)를 입력해주세요.");
        }

        RsData rsData = memberService.tryLogin(username, password);

        if (rsData.isSuccess()) {
            Member member = (Member) rsData.getData();
            rq.setCookie("loginedMemberId", member.getId()); // member.getId()가 숫자 or 문자열 다 들어가도 됨
        } // 로그인 성공여부 판단 메서드 isSuccess()가 true 이면 쿠키 만듦

        return rsData;
    }

    @GetMapping("/member/logout") // logout 시 쿠키 지우기 using ChatGPT
    @ResponseBody
    public RsData logout(HttpServletRequest req, HttpServletResponse resp) {
        Rq rq = new Rq(req, resp); // rq에게 맡겨버리기
        boolean cookieRemoved = rq.removeCookie("loginedMemberId");

        if (cookieRemoved == false) { // 쿠키 삭제 안됨 == name인 쿠키 없음
            return RsData.of("S-2", "이미 로그아웃 상태입니다.");
        }

        return RsData.of("S-1", "로그아웃 되었습니다.");
    }

    //
    @GetMapping("/member/me")
    @ResponseBody
    public RsData showMe(HttpServletRequest req, HttpServletResponse resp) {
        Rq rq = new Rq(req, resp);

        long loginedMemberId = rq.getCookieAsLong("loginedMemberId", 0); // rq에게 쿠키 값 요청

        boolean isLogined = loginedMemberId > 0; // 0보다 크면 로그인된 것임

        if (isLogined == false) // 로그인 상태가 아닐 때(쿠키가 없으면 logout 상태임)
            return RsData.of("F-1", "로그인 후 이용해주세요.");

        Member member = memberService.findById(loginedMemberId); // Service를 거쳐 Repository에게 요청됨

        return RsData.of("S-1", "당신의 username(은)는 %s 입니다.".formatted(member.getUsername()));
    }
}
