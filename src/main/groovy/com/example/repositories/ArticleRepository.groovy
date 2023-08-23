package com.example.repositories

import com.example.domain.models.Article
import com.redis.om.spring.repository.RedisDocumentRepository
import org.springframework.data.domain.Page
import org.springframework.data.domain.Pageable

interface ArticleRepository extends RedisDocumentRepository<Article, String> {

    Page<Article> findByAuthor_Id(String id, Pageable pageable);

    Iterable<Article> search(String text);

}
