package com.ru.secretary.springwebapp.controllers;

import com.ru.secretary.springwebapp.domain.Task;
import com.ru.secretary.springwebapp.domain.User;
import com.ru.secretary.springwebapp.repositories.TaskRepository;
import com.ru.secretary.springwebapp.repositories.UserRepository;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Set;

@RestController
public class TasksRestController {
    private final UserRepository userRepository;
    private final TaskRepository taskRepository;


    public TasksRestController(UserRepository userRepository, TaskRepository taskRepository) {
        this.userRepository = userRepository;
        this.taskRepository = taskRepository;
    }

    @GetMapping("/getTasks")
    public Set<Task> getTasks(@AuthenticationPrincipal User currentUser) {
        return taskRepository.findAllByUser(currentUser);
    }
}
