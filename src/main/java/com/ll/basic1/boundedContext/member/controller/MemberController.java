package com.ll.basic1.boundedContext.member.controller;

import com.ll.basic1.base.rsData.RsData;
import com.ll.basic1.boundedContext.member.entity.Member;
import com.ll.basic1.boundedContext.member.service.MemberService;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.Arrays;

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
    public RsData login(String username, String password, HttpServletResponse resp) {

        // Controller는 문지기 역할, 복잡한 일은 Service에게 토스
        if ( username == null || username.trim().length() == 0 ) {
            return RsData.of("F-3", "username(을)를 입력해주세요.");
        }

        if ( password == null || password.trim().length() == 0 ) {
            return RsData.of("F-4", "password(을)를 입력해주세요.");
        }

        RsData rsData = memberService.tryLogin(username, password);

        if (rsData.isSuccess()) {
            Member member = (Member) rsData.getData();
            resp.addCookie(new Cookie("loginedMemberId", member.getId() + ""));
        } // 로그인 성공여부 판단 메서드 isSuccess()가 true 이면 쿠키 만듦

        return rsData;
    }

    @GetMapping("/member/logout") // logout 시 쿠키 지우기 using ChatGPT
    @ResponseBody
    public RsData logout(HttpServletRequest req, HttpServletResponse resp) {
        if (req.getCookies() != null) { // 쿠키 있는지 체크
            Arrays.stream(req.getCookies())
                    .filter(cookie -> cookie.getName().equals("loginedMemberId"))
                    .forEach(cookie -> {
                        cookie.setMaxAge(0);
                        resp.addCookie(cookie);
                    });
        }

        return RsData.of("S-1", "로그아웃 되었습니다.");
    }

    //
    @GetMapping("/member/me")
    @ResponseBody
    public RsData showMe(HttpServletRequest req) {
        long loginedMemberId = 0; // 현재 로그인한 member의 id

        // 스프링부트는 사용자들을 구별못하므로 특정 사용자의 로그인 여부를 알기 위해서는 쿠키를 이용해야 한다
        if (req.getCookies() != null) {
            loginedMemberId = Arrays.stream(req.getCookies())
                    .filter(cookie -> cookie.getName().equals("loginedMemberId")) // 이름이 loginedMemberId인 쿠키 찾기
                    .map(Cookie::getValue)
                    .mapToInt(Integer::parseInt)
                    .findFirst()
                    .orElse(0);
        }

        boolean isLogined = loginedMemberId > 0; // 0보다 크면 로그인된 것임

        if (isLogined == false) // 로그인 상태가 아닐 때(쿠키가 없으면 logout 상태임)
            return RsData.of("F-1", "로그인 후 이용해주세요.");

        Member member = memberService.findById(loginedMemberId); // Service를 거쳐 Repository에게 요청됨

        return RsData.of("S-1", "당신의 username(은)는 %s 입니다.".formatted(member.getUsername()));
    }
}
