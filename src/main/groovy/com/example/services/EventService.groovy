package com.example.services

import com.example.domain.dto.EventRequest
import com.example.domain.models.Event
import com.example.domain.models.User
import com.example.repositories.EventRepository
import com.example.repositories.UserRepository
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Service

import java.time.LocalDateTime

@Service
class EventService {

    private final EventRepository eventRepository
    private final UserRepository userRepository

    @Autowired
    EventService(EventRepository eventRepository, UserRepository userRepository) {
        this.eventRepository = eventRepository
        this.userRepository = userRepository
    }

    Iterable<Event> searchByBeginDateBetween(LocalDateTime start, LocalDateTime end) {
        return eventRepository.findByBeginDateBetween(start, end)
    }

    Iterable<Event> search(String text) {
        return eventRepository.search(text)
    }

    Iterable<Event> findByTags(String[] tags) {
        return eventRepository.findByTags(tags)
    }

    Event save(EventRequest eventRequest) {
        def guests = getGuestIds.andThen(retrieveUsers)(eventRequest)
        return eventRepository.save(eventRequest.toEvent(guests))
    }

    private Closure<String[]> getGuestIds = { EventRequest req -> req.artistIds().toArray(String[]::new) }

    private Closure<List<User>> retrieveUsers = { String[] ids -> userRepository.findAllById { ids } }
}
