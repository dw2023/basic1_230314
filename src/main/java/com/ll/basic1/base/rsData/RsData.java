package com.ll.basic1.base.rsData;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public class RsData {
    private final String resultCode;
    private final String msg;
    private final Object data;
    // data 변수를 선언하여, MemberService의 tryLogin 메서드가 추가적인 데이터를 리턴하게 함
    // 무엇이든 담기게 하려고 Object

    public static RsData of(String resultCode, String msg) {
        return of(resultCode, msg, null);
    }

    public static RsData of(String resultCode, String msg, Object data) {
        return new RsData(resultCode, msg, data);
    }

    public boolean isSuccess() {
        return resultCode.startsWith("S-");
    }
}