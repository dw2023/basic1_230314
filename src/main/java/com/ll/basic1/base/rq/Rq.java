package com.ll.basic1.base.rq;

import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.AllArgsConstructor;

import java.util.Arrays;

@AllArgsConstructor
public class Rq {
    private final HttpServletRequest req;
    private final HttpServletResponse resp;

    // 삭제가 성공했는지 t/f로 리턴
    public boolean removeCookie(String name) {
        if (req.getCookies() != null) {
            Arrays.stream(req.getCookies())
                    .filter(cookie -> cookie.getName().equals(name))
                    .forEach(cookie -> {
                        cookie.setMaxAge(0);
                        resp.addCookie(cookie);
                    });

            return Arrays.stream(req.getCookies())
                    .filter(cookie -> cookie.getName().equals(name)) // name인 쿠키가 있으면 위 if문에서 삭제됐을 것이기 때문에
                    .count() > 0; // name인 쿠키가 0개 이상 있으면 삭제된 것 -> true 리턴
        }

        return false;
    }

    public String getCookie(String name, String defaultValue) {
        if ( req.getCookies() == null ) return defaultValue;

        return Arrays.stream(req.getCookies())
                .filter(cookie -> cookie.getName().equals(name)) // 전체 쿠키에서 name인 쿠키 찾기
                .map(Cookie::getValue)
                .findFirst()
                .orElse(defaultValue);
    }

    // 쿠키를 갖고 와서 정수화
    public long getCookieAsLong(String name, long defaultValue) {
        String value = getCookie(name, null); // 이름이 name인 쿠키의 값 가져옴

        if ( value == null ) {
            return defaultValue;
        }

        try {
            return Long.parseLong(value); // ctrl 누르고 parseLong 클릭하면 발생할 예외 알 수 있음
        }
        catch ( NumberFormatException e ) {
            return defaultValue;
        }
    }

    public void setCookie(String name, long value) {
        setCookie(name, value + "");
    }

    // 같은 클래스에 같은 이름의 메서드 (오버로딩)
    public void setCookie(String name, String value) {
        resp.addCookie(new Cookie(name, value));
    }
}
