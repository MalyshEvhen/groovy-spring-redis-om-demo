package com.example.repositories

import com.example.domain.models.User
import com.redis.om.spring.repository.RedisDocumentRepository

interface UserRepository extends RedisDocumentRepository<User, String>{

    Iterable<User> search(String text);

}
