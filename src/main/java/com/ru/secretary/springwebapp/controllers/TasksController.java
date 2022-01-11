package com.ru.secretary.springwebapp.controllers;

import com.ru.secretary.springwebapp.domain.User;
import com.ru.secretary.springwebapp.repositories.TaskRepository;
import com.ru.secretary.springwebapp.repositories.UserRepository;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

@Controller
public class TasksController {
    private final UserRepository userRepository;
    private final TaskRepository taskRepository;

    public TasksController(UserRepository userRepository, TaskRepository taskRepository) {
        this.userRepository = userRepository;
        this.taskRepository = taskRepository;
    }

    @GetMapping("/tasks")
    public String getUserTasks(Model model, @AuthenticationPrincipal User currentUser) {
        model.addAttribute("tasks", taskRepository.findAllByUser(currentUser));
        return "/tasks";
    }

    @PostMapping("/tasks")
    public String getTasks() {
        return "redirect:/tasks";
    }

    @GetMapping("/scheduler")
    public String showScheduler() {
        return "/scheduler";
    }
}
