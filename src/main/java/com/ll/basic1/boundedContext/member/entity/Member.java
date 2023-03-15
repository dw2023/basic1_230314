package com.ll.basic1.boundedContext.member.entity;

import lombok.AllArgsConstructor;
import lombok.Data;

@AllArgsConstructor
@Data
public class Member {
    private static long lastId;
    private final long id;
    private final String username;
    private final String password;
    // 별다른 일 없으면 항상 final을 붙인다

    // static 필드는 static 생성자를 통해서 초기화
    static {
        lastId = 0;
    }

    public Member(String username, String password) {
        this(++lastId, username, password);
    }
}
