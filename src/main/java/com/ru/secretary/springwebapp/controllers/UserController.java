package com.ru.secretary.springwebapp.controllers;

import com.ru.secretary.springwebapp.domain.User;
import com.ru.secretary.springwebapp.repositories.TaskRepository;
import com.ru.secretary.springwebapp.repositories.UserRepository;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

@Controller
public class UserController {

    private final UserRepository userRepository;
    private final TaskRepository taskRepository;

    public UserController(UserRepository userRepository, TaskRepository taskRepository) {
        this.userRepository = userRepository;
        this.taskRepository = taskRepository;
    }

    @PostMapping("/profile")
    public String addPhoneNumber(String phoneNumber,
                                 @AuthenticationPrincipal User currentUser) {

        currentUser.setPhoneNumber(phoneNumber);
        userRepository.save(currentUser);

        return "profile";
    }

    @GetMapping("/profile")
    public String profile() {
        return "profile";
    }
}
