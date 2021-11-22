package com.ru.secretary.springwebapp.controllers;

import com.ru.secretary.springwebapp.domain.Task;
import com.ru.secretary.springwebapp.domain.TaskPriority;
import com.ru.secretary.springwebapp.domain.User;
import com.ru.secretary.springwebapp.repositories.TaskRepository;
import com.ru.secretary.springwebapp.repositories.UserRepository;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.Date;

@Controller
public class TasksController {

    private final UserRepository userRepository;
    private final TaskRepository taskRepository;


    public TasksController(UserRepository userRepository, TaskRepository taskRepository) {
        this.userRepository = userRepository;
        this.taskRepository = taskRepository;
    }

    //TODO avoid searching for user by name
    //TODO make user or username global in this class

    @RequestMapping("/tasks")
    public String getUserTasks(Model model, User user) {

        String userName = SecurityContextHolder.getContext().getAuthentication().getName();

        model.addAttribute("tasks", taskRepository.findAllByUser(userRepository.findByName(userName)));

        return "tasks";
    }

    @GetMapping("/tasks/add")
    public String addTask(){
        return "addTask";
    }

    @PostMapping("/tasks/add") //TODO add all fields
    public String addTask(Model model, Task task) {

        if ((task.getTitle() == null) || (task.getTitle()=="")) {
            model.addAttribute("resultMessage", "Title can`t be empty!");
            return "addTask";
        }

        String userName = SecurityContextHolder.getContext().getAuthentication().getName();
        var user = userRepository.findByName(userName);
        task.setUser(user);
        user.getTasks().add(task);
        taskRepository.save(task);
        model.addAttribute("resultMessage", "New task added.");

        return "redirect:/tasks";
    }
}
