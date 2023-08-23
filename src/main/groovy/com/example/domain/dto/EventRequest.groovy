package com.example.domain.dto

import com.example.domain.models.Event
import com.example.domain.models.User
import jakarta.validation.constraints.Future
import jakarta.validation.constraints.NotBlank
import jakarta.validation.constraints.NotNull
import jakarta.validation.constraints.Size

import java.time.LocalDateTime

import static com.example.constraints.SharedConstraints.*

record EventRequest(
        @NotNull @NotBlank @Size(min = MIN_TITLE_LENGTH, max = MAX_TITLE_LENGTH) String title,
        @NotNull @NotBlank @Size(min = MIN_CONTENT_LENGTH, max = MAX_CONTENT_LENGTH) String content,
        Set<String> tags,
        Set<String> artistIds,
        @NotNull @Future LocalDateTime begin,
        @NotNull @Future LocalDateTime end
) {
    def toEvent(Collection<User> guests) { Event.of(this.title(), this.content(), guests, this.begin(), this.end()) }
}
