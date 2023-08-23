package com.example.controllers

import com.example.domain.models.User
import com.example.services.UserService
import com.example.util.FakeDataGenerator
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest
import org.springframework.boot.test.context.TestConfiguration
import org.springframework.context.annotation.Bean
import org.springframework.data.domain.Page
import org.springframework.data.domain.PageImpl
import org.springframework.data.domain.Pageable
import org.springframework.test.web.servlet.MockMvc
import spock.lang.Specification
import spock.mock.DetachedMockFactory

import java.util.stream.Stream

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status

@WebMvcTest(controllers = [UserController])
class UserControllerSpec extends Specification {

    @Autowired
    private UserService userService

    @Autowired
    private MockMvc mockMvc

    private static Page usersPage

    def setup() {
        def users = Stream.of(1..30).map { FakeDataGenerator.getUser(User.Role.AUTHOR) }.toList()
        usersPage = new PageImpl(users)
    }

    def 'UserController::getAll should return status OK'() {
        setup: 'create pageable object with a given size and a given page number'
        int size = 30
        int page = 1
        def pageable = Pageable.ofSize(size).withPage(page)

        when: 'return this.usersPage from mock userService when UserService::findAll is invoked'
        userService.findAll(pageable) >> usersPage

        then: 'check that status of response is OK, and page parameters is in the created json'
        mockMvc.perform(get("/users/all")
                .param('size', "$size")
                .param('page', "$page"))
                .andExpect(status().isOk())
    }

    @TestConfiguration
    static class UserControllerMockConfiguration {

        def detachedMockFactory = new DetachedMockFactory()

        @Bean
        UserService userService() {
            return detachedMockFactory.Stub(UserService)
        }

    }

}
