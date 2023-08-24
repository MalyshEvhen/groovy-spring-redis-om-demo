package com.example.webapp

import com.example.services.UserService
import org.springframework.data.domain.Pageable
import org.springframework.stereotype.Controller
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.servlet.ModelAndView

@Controller
class WebController {
    private final UserService userService

    WebController(UserService userService) {
        this.userService = userService
    }

    @GetMapping("/")
    def home() {
        new ModelAndView("views/home")
    }

    @GetMapping("/list")
    def userList() {
        new ModelAndView("views/user/list", [users: userService.findAll(Pageable.ofSize(30).withPage(0))])
    }

    @GetMapping("/{id}")
    def view(@PathVariable('id') String id) {
        new ModelAndView('views/user/edit', 'user', userService.findById(id))
    }

}
