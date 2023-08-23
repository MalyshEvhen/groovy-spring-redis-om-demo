package com.example.services

import com.example.domain.dto.UserRegistration
import com.example.services.config.AbstractRedisIntegrationSpec
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.data.domain.Pageable

import static com.example.domain.models.User.Role.AUTHOR
import static com.example.util.FakeDataGenerator.getUser

class UserServiceSpec extends AbstractRedisIntegrationSpec {

    @Autowired
    private UserService userService

    def 'UserService::save should save valid user and return user with ID'() {
        setup: 'prepare test user and convert to registration form'
        def user = getUser(AUTHOR)
        def registrationForm
                = new UserRegistration(
                user.getFirstName(),
                user.getLastName(),
                user.getEmail(),
                user.getBio())

        when: 'invoke UserService::save method with valid form'
        def savedUser = userService.save(registrationForm)

        then: 'check if retrieved user has ID '
        savedUser.getId() != null

        cleanup: 'remove new user'
        userService.deleteById(savedUser.id)
    }

    def 'UserService::getAll should retrieve page of users with given parameters'() {
        setup: 'create pageable object with a given size and a given page number'
        int size = 30
        int pageNumber = 0
        def pageable
                = Pageable
                .ofSize(size)
                .withPage(pageNumber)

        when: 'invoke UserService::getAll with prepared pageable'
        def page = userService.findAll(pageable)

        then: 'check if retrieved page has size: #size and all users are valid and have IDs'
        page.size == 30
        page.map { it.id }.filter { it == null }.collect().isEmpty()
        page.totalElements == 200
    }

}
