package com.ll.basic1.boundedContext.article.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import lombok.Builder;
import lombok.Getter;

import java.time.LocalDateTime;

import static jakarta.persistence.GenerationType.IDENTITY;

@Entity
@Getter
@Builder
public class Article {
    @Id // id를 primary key로 설정
    @GeneratedValue(strategy = IDENTITY) // AUTO_INCREMENT
    private long id;
    private LocalDateTime createDate; // 데이터 생성 날짜
    private LocalDateTime modifyDate; // 데이터 수정 날짜
    private String title;
    private String body;
}
