package com.ll.basic1.boundedContext.member.controller;

import com.ll.basic1.base.rq.Rq;
import com.ll.basic1.base.rsData.RsData;
import com.ll.basic1.boundedContext.member.entity.Member;
import com.ll.basic1.boundedContext.member.service.MemberService;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
@AllArgsConstructor
public class MemberController {
    // @Autowired
    // private MemberService memberService;
    // 아래 코드 대신 이것도 가능 (필드주입)
    private final MemberService memberService;
    private final Rq rq;

    /* @AllArgsConstructor로 생략됨
    @Autowired
    public MemberController(MemberService memberService, Rq rq) {
        this.memberService = memberService;
        this.rq = rq;
    }
    */

    // 로그인 성공한 회원의 id를 쿠키 값으로 만듦
    @GetMapping("/member/login")
    public String showLogin() {
        return "usr/member/login";
    }

    @PostMapping("/member/login") // Post로 method를 바꿨기 때문에 위에 것과 url이 같아도 됨
    @ResponseBody
    public RsData login(String username, String password) {
        if ( username == null || username.trim().length() == 0 ) {
            return RsData.of("F-3", "username(을)를 입력해주세요.");
        }

        if ( password == null || password.trim().length() == 0 ) {
            return RsData.of("F-4", "password(을)를 입력해주세요.");
        }

        RsData rsData = memberService.tryLogin(username, password);

        if (rsData.isSuccess()) {
            Member member = (Member) rsData.getData();
            rq.setSession("loginedMemberId", member.getId()); // member.getId()가 숫자 or 문자열 다 들어가도 됨
        } // 로그인 성공여부 판단 메서드 isSuccess()가 true 이면 쿠키 만듦

        return rsData;
    }

    @GetMapping("/member/logout") // logout 시 쿠키 지우기 using ChatGPT
    @ResponseBody
    public RsData logout() {
        boolean cookieRemoved = rq.removeSession("loginedMemberId");

        if (cookieRemoved == false) { // 쿠키 삭제 안됨 == name인 쿠키 없음
            return RsData.of("S-2", "이미 로그아웃 상태입니다.");
        }

        return RsData.of("S-1", "로그아웃 되었습니다.");
    }

    //
    @GetMapping("/member/me")
    @ResponseBody
    public RsData showMe() {
        long loginedMemberId = rq.getSessionAsLong("loginedMemberId", 0); // rq에게 쿠키 값 요청

        boolean isLogined = loginedMemberId > 0; // 0보다 크면 로그인된 것임

        if (isLogined == false) // 로그인 상태가 아닐 때(쿠키가 없으면 logout 상태임)
            return RsData.of("F-1", "로그인 후 이용해주세요.");

        Member member = memberService.findById(loginedMemberId); // Service를 거쳐 Repository에게 요청됨

        return RsData.of("S-1", "당신의 username(은)는 %s 입니다.".formatted(member.getUsername()));
    }

    // 디버깅용 함수
    @GetMapping("/member/session")
    @ResponseBody
    public String showSession() {
        return rq.getSessionDebugContents().replaceAll("\n", "<br>");
    }
}
