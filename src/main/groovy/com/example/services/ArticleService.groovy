package com.example.services

import com.example.domain.dto.ArticleRequest
import com.example.domain.models.Article
import com.example.domain.models.User
import com.example.repositories.ArticleRepository
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.data.domain.Page
import org.springframework.data.domain.Pageable
import org.springframework.stereotype.Service

@Service
class ArticleService {

    private final ArticleRepository articleRepository
    private final UserService userService

    @Autowired
    ArticleService(ArticleRepository articleRepository,
                   UserService userService) {
        this.articleRepository = articleRepository
        this.userService = userService
    }

    Page<Article> findAll(Pageable pageable) {
        return articleRepository.findAll(pageable)
    }

    Article findById(String id) {
        return articleRepository.findById(id).orElseThrow()
    }

    Page<Article> findByAuthorId(String id, Pageable pageable) {
        return articleRepository.findByAuthor_Id(id, pageable)
    }

    Iterable<Article> search(String query) {
        return articleRepository.search(query)
    }

    Article save(ArticleRequest request) {
        def author = getArticleAuthor(request)
        def article = request.toArticle(author)
        return articleRepository.save(article)
    }

    void deleteById(String id) {
        articleRepository.delete(findById(id))
    }

    private Closure<User> getArticleAuthor = { ArticleRequest request -> userService.findById(request.authorId()) }
}
