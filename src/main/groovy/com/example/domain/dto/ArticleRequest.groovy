package com.example.domain.dto

import com.example.domain.models.Article
import com.example.domain.models.User
import jakarta.validation.constraints.NotBlank
import jakarta.validation.constraints.NotNull
import jakarta.validation.constraints.Size

import static com.example.constraints.SharedConstraints.*

record ArticleRequest(
        @NotNull @NotBlank @Size(min = MIN_TITLE_LENGTH, max = MAX_TITLE_LENGTH) String title,
        @NotNull @NotBlank @Size(min = MIN_CONTENT_LENGTH, max = MAX_CONTENT_LENGTH) String content,
        Set<String> tags,
        @NotNull String authorId
) {
    def toArticle(User author) { Article.of(this.title(), this.content(), author) }
}
