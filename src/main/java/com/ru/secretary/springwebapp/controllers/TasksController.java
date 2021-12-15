package com.ru.secretary.springwebapp.controllers;

import com.ru.secretary.springwebapp.domain.Task;
import com.ru.secretary.springwebapp.domain.User;
import com.ru.secretary.springwebapp.repositories.TaskRepository;
import com.ru.secretary.springwebapp.repositories.UserRepository;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class TasksController {

    private final UserRepository userRepository;
    private final TaskRepository taskRepository;


    public TasksController(UserRepository userRepository, TaskRepository taskRepository) {
        this.userRepository = userRepository;
        this.taskRepository = taskRepository;
    }

    @GetMapping("/tasks")
    public String getUserTasks(Model model,
                               @AuthenticationPrincipal User currentUser) {

        //String userName = SecurityContextHolder.getContext().getAuthentication().getName();

        model.addAttribute("tasks", taskRepository.findAllByUser(currentUser));

        return "/tasks";
    }

    @PostMapping("/tasks")
    public String getTasks() {
        return "redirect:/tasks";
    }

    @GetMapping("/tasks/add")
    public String addTask(){
        return "addTask";
    }

    @PostMapping("/tasks/add") //TODO add all fields
    public String addTask(Model model, Task task,
                          @AuthenticationPrincipal User currentUser) {

        if ((task.getTitle() == null) || (task.getTitle().equals(""))) {
            model.addAttribute("resultMessage", "Title can`t be empty!");
            return "addTask";
        }
        //I cant user @authprincipal here because of hibernate`s lazy load
        String userName = SecurityContextHolder.getContext().getAuthentication().getName();
        var user = userRepository.findByName(userName);
        task.setUser(user);
        user.getTasks().add(task);
        taskRepository.save(task);
        model.addAttribute("resultMessage", "New task added.");

        return "redirect:/tasks";
    }

    @GetMapping("/scheduler")
    public String showScheduler() {
        return "/scheduler";
    }
}
