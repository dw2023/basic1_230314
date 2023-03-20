package com.ll.basic1.boundedContext.article.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.EntityListeners;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import java.time.LocalDateTime;

import static jakarta.persistence.GenerationType.IDENTITY;

@Entity
@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor
@EntityListeners(AuditingEntityListener.class) // @CreatedDate, @LastModifiedDate 를 사용하려면 써야 함
public class Article {
    @Id // id를 primary key로 설정
    @GeneratedValue(strategy = IDENTITY) // AUTO_INCREMENT
    private long id;
    @CreatedDate // 데이터 생성될 때 자동으로 날짜 넣어줌
    private LocalDateTime createDate; // 데이터 생성 날짜
    @LastModifiedDate // 데이터 수정될 때 자동으로 날짜 넣어줌
    private LocalDateTime modifyDate; // 데이터 수정 날짜
    private String title;
    private String body;
}
