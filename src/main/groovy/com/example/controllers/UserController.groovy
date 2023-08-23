package com.example.controllers

import com.example.domain.dto.UserRegistration
import com.example.domain.models.User
import com.example.services.UserService
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
@RequestMapping('/users')
class UserController {

    private final UserService userService

    @Autowired
    UserController(UserService userService) {
        this.userService = userService
    }

    @Operation(summary = 'Find all users')
    @ApiResponse(
            responseCode = '200',
            description = 'Successfully retrieve page of users',
            content = @Content(
                    mediaType = 'application/json',
                    schema = @Schema(implementation = User)))
    @GetMapping('/all')
    Page<User> getAll(@RequestParam('size') int size, @RequestParam('page') int page) {
        Pageable pageable = Pageable.ofSize(size).withPage(page)
        return userService.findAll(pageable)
    }

    @Operation(summary = 'Full text search by users firstname and lastname')
    @ApiResponse(
            responseCode = '200',
            description = 'Successfully retrieve collection of users',
            content = @Content(
                    mediaType = 'application/json',
                    schema = @Schema(implementation = User)))
    @GetMapping('/search/{q}')
    Iterable<User> search(@PathVariable('q') String query) {
        return userService.search(query)
    }

    @Operation(summary = 'Save new user')
    @ApiResponse(
            responseCode = '201',
            description = 'Successfully save the user',
            content = @Content(
                    mediaType = 'application/json',
                    schema = @Schema(implementation = User)))
    @PostMapping('/save')
    @ResponseStatus(HttpStatus.CREATED)
    User save(@RequestBody UserRegistration user) {
        return userService.save(user)
    }

    @Operation(summary = 'Delete existing user')
    @ApiResponse(
            responseCode = '204',
            description = 'Successfully delete the user')
    @DeleteMapping('/delete/{id}')
    @ResponseStatus(HttpStatus.NO_CONTENT)
    void delete(@PathVariable('id') String id) {
        userService.deleteById(id)
    }

}
