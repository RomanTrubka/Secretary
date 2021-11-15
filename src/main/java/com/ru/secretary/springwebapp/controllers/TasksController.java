package com.ru.secretary.springwebapp.controllers;

import com.ru.secretary.springwebapp.domain.User;
import com.ru.secretary.springwebapp.repositories.TaskRepository;
import com.ru.secretary.springwebapp.repositories.UserRepository;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class TasksController {

    private final UserRepository userRepository;
    private final TaskRepository taskRepository;


    public TasksController(UserRepository userRepository, TaskRepository taskRepository) {
        this.userRepository = userRepository;
        this.taskRepository = taskRepository;
    }

    @RequestMapping("/tasks")
    public String getUserTasks(Model model, User user) {

        String userName = SecurityContextHolder.getContext().getAuthentication().getName();

        model.addAttribute("tasks", taskRepository.findAllByUser(userRepository.findByName(userName)));

        return "taskList";
    }
}
