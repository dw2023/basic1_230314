package com.ll.basic1.boundedContext.article.repository;

import com.ll.basic1.boundedContext.article.entity.Article;
import org.springframework.data.jpa.repository.JpaRepository;

// ArticleRepository는 JPA가 구현함
// 이 클래스에는 @Repository 어노테이션을 생략해도 된다.
public interface ArticleRepository extends JpaRepository<Article, Long> {
    // ArticleRepository는 Article 테이블을 다룬다
    // Article의 Primary key는 Long타입이다.
}