package com.example.controllers

import com.example.domain.dto.ArticleRequest
import com.example.domain.models.Article
import com.example.services.ArticleService
import io.swagger.v3.oas.annotations.Operation
import io.swagger.v3.oas.annotations.media.Content
import io.swagger.v3.oas.annotations.media.Schema
import io.swagger.v3.oas.annotations.responses.ApiResponse
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.data.domain.Page
import org.springframework.data.domain.Pageable
import org.springframework.http.HttpStatus
import org.springframework.web.bind.annotation.*

@RestController
@RequestMapping('/api/articles')
class ArticleController {

    private final ArticleService articleService

    @Autowired
    ArticleController(ArticleService articleService) {
        this.articleService = articleService
    }

    @Operation(summary = 'Get all articles')
    @ApiResponse(
            responseCode = '200',
            description = 'Successfully retrieved the list of articles',
            content = @Content(
                    mediaType = 'application/json',
                    schema = @Schema(implementation = Article)))
    @GetMapping('/all')
    Page<Article> all(@RequestParam('size') int size, @RequestParam('page') int page) {
        Pageable pageable = Pageable.ofSize(size).withPage(page)
        return articleService.findAll(pageable)
    }

    @Operation(summary = 'Get a list of all articles by author ID')
    @ApiResponse(
            responseCode = '200',
            description = 'Successfully retrieved the list of articles',
            content = @Content(
                    mediaType = 'application/json',
                    schema = @Schema(implementation = Article)))
    @GetMapping('/by-author-id')
    Page<Article> byAuthor(@RequestParam('authorId') String id,
                           @RequestParam('size') int size,
                           @RequestParam('page') int page) {
        Pageable pageable = Pageable.ofSize(size).withPage(page)
        return articleService.findByAuthorId(id, pageable)
    }

    @Operation(summary = 'Get a list of all articles by part of article title')
    @ApiResponse(
            responseCode = '200',
            description = 'Successfully retrieved the collection of articles',
            content = @Content(
                    mediaType = 'application/json',
                    schema = @Schema(implementation = Article)))
    @GetMapping('/search/{q}')
    Iterable<Article> search(@PathVariable('q') String query) {
        return articleService.search(query)
    }

    @Operation(summary = 'Create a new article')
    @ApiResponse(
            responseCode = '201',
            description = 'Article created successfully',
            content = @Content(
                    mediaType = 'application/json',
                    schema = @Schema(implementation = Article)))
    @PostMapping('/save')
    @ResponseStatus(HttpStatus.CREATED)
    Article save(@RequestBody ArticleRequest article) {
        return articleService.save(article)
    }

    @Operation(summary = 'Delete an article by ID')
    @ApiResponse(
            responseCode = '204',
            description = 'Article deleted successfully')
    @ApiResponse(
            responseCode = '400',
            description = 'Id is not valid')
    @ApiResponse(
            responseCode = '404',
            description = 'Article not found')
    @DeleteMapping('/delete/{id}')
    @ResponseStatus(HttpStatus.NO_CONTENT)
    void delete(@PathVariable('id') String id) {
        articleService.deleteById(id)
    }

}
