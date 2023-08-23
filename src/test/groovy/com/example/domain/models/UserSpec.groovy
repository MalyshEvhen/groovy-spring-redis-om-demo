package com.example.domain.models

import com.example.util.FakeDataGenerator
import jakarta.validation.Validation
import jakarta.validation.Validator
import spock.lang.Specification

import static com.example.domain.models.User.Role.AUTHOR

class UserSpec extends Specification {

    Validator validator

    void setup() {
        def factory = Validation.buildDefaultValidatorFactory()
        validator = factory.getValidator()

    }

    def 'Valid method should not have validation constraints'() {
        given: 'create valid user'
        def user = FakeDataGenerator.getUser(AUTHOR)

        when: 'validate prepared user'
        def violations = validator.validate(user)

        then: 'check if set of validation constraints is empty'
        violations.isEmpty()
    }

    def 'Violations should be created if email is invalid'(String email) {
        given: 'create valid user'
        def user = FakeDataGenerator.getUser(AUTHOR)

        when: 'set invalid email from the parameter and validate invalid user'
        user.setEmail(email)
        def violations = validator.validate(user)

        then: 'check that validation constraints was created'
        !violations.isEmpty()

        where: 'list of invalid emails'
        email << ['invalid@email', '@invalid.email', 'invalid.email', null, '']
    }
}
