package com.example.services

import com.example.domain.dto.UserRegistration
import com.example.domain.models.User
import com.example.repositories.UserRepository
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.data.domain.Page
import org.springframework.data.domain.Pageable
import org.springframework.stereotype.Service

@Service
class UserService {

    private final UserRepository userRepository

    @Autowired
    UserService(UserRepository userRepository) {
        this.userRepository = userRepository
    }

    Page<User> findAll(Pageable pageable) {
        return userRepository.findAll(pageable)
    }

    User findById(String id) {
        return userRepository.findById(id).orElseThrow()
    }

    Iterable<User> search(String query) {
        return userRepository.search(query)
    }

    User save(UserRegistration userRegistration) {
        return userRepository.save(userRegistration.toUser())
    }

    void deleteById(String id) {
        userRepository.delete(findById(id))
    }
}
