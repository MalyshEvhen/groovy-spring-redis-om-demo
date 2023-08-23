package com.example.controllers

import com.example.domain.dto.EventRequest
import com.example.domain.models.Event
import com.example.services.EventService
import io.swagger.v3.oas.annotations.Operation
import io.swagger.v3.oas.annotations.media.Content
import io.swagger.v3.oas.annotations.media.Schema
import io.swagger.v3.oas.annotations.responses.ApiResponse
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.format.annotation.DateTimeFormat
import org.springframework.http.HttpStatus
import org.springframework.web.bind.annotation.*

import java.time.LocalDateTime

@RestController
@RequestMapping('/api/events')
class EventController {

    private final EventService eventService

    @Autowired
    EventController(EventService eventService) {
        this.eventService = eventService
    }

    @Operation(summary = 'Find event in between begin and end dates')
    @ApiResponse(
            responseCode = '200',
            description = 'Successfully retrieved the collection of events',
            content = @Content(
                    mediaType = 'application/json',
                    schema = @Schema(implementation = Event)))
    @GetMapping('/between')
    Iterable<Event> byStartAndEndOf(@RequestParam('start')
                                @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) LocalDateTime start,
                                @RequestParam('end')
                                @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) LocalDateTime end) {
        return eventService.searchByBeginDateBetween(start, end)
    }

    @Operation(summary = 'Full text search by part of event title')
    @ApiResponse(
            responseCode = '200',
            description = 'Successfully retrieved the collection of events',
            content = @Content(
                    mediaType = 'application/json',
                    schema = @Schema(implementation = Event)))
    @GetMapping('/search/{q}')
    Iterable<Event> search(@PathVariable('q') String q) {
        return eventService.search(q)
    }

    @Operation(summary = 'Find events by array of tags')
    @ApiResponse(
            responseCode = '200',
            description = 'Successfully retrieved the collection of events',
            content = @Content(
                    mediaType = 'application/json',
                    schema = @Schema(implementation = Event)))
    @GetMapping('/tags')
    Iterable<Event> allByTags(@RequestParam('tags') String... tags) {
        return eventService.findByTags(tags)
    }

    @Operation(summary = 'Create new event')
    @ApiResponse(
            responseCode = '201',
            description = 'Event successfully created',
            content = @Content(
                    mediaType = 'application/json',
                    schema = @Schema(implementation = Event)))
    @PostMapping('/save')
    @ResponseStatus(HttpStatus.CREATED)
    Event save(@RequestBody EventRequest event) {
        return eventService.save(event)
    }

}
