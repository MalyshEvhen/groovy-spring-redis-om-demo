package com.example.repositories

import com.example.domain.models.Event
import com.redis.om.spring.repository.RedisDocumentRepository
import org.springframework.data.repository.query.Param

import java.time.LocalDateTime

interface EventRepository extends RedisDocumentRepository<Event, String> {

    Iterable<Event> findByBeginDateBetween(LocalDateTime begin, LocalDateTime end)

    Iterable<Event> findByTags(@Param("tags") String[] tags)

    Iterable<Event> search(String text);

}
