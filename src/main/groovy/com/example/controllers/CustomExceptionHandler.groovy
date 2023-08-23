package com.example.controllers


import jakarta.validation.ConstraintViolationException
import jakarta.xml.bind.ValidationException
import org.springframework.http.HttpHeaders
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.ExceptionHandler
import org.springframework.web.bind.annotation.RestControllerAdvice
import org.springframework.web.context.request.WebRequest
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler

@RestControllerAdvice
class CustomExceptionHandler extends ResponseEntityExceptionHandler {

    @ExceptionHandler([ConstraintViolationException, ValidationException, IllegalArgumentException])
    protected final ResponseEntity handleBadRequest(final RuntimeException ex, final WebRequest request) {
        return handleExceptionInternal(ex, ex.message, new HttpHeaders(), HttpStatus.BAD_REQUEST, request)
    }

    @ExceptionHandler([NoSuchElementException])
    protected final ResponseEntity handleNotFound(final RuntimeException ex, final WebRequest request) {
        return handleExceptionInternal(ex, ex.message, new HttpHeaders(), HttpStatus.NOT_FOUND, request)
    }

}
