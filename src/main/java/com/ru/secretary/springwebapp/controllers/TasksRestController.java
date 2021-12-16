package com.ru.secretary.springwebapp.controllers;

import com.ru.secretary.springwebapp.domain.Task;
import com.ru.secretary.springwebapp.domain.User;
import com.ru.secretary.springwebapp.repositories.TaskRepository;
import com.ru.secretary.springwebapp.repositories.UserRepository;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.util.NoSuchElementException;
import java.util.Set;

@RestController
public class TasksRestController {
    private final UserRepository userRepository;
    private final TaskRepository taskRepository;


    public TasksRestController(UserRepository userRepository, TaskRepository taskRepository) {
        this.userRepository = userRepository;
        this.taskRepository = taskRepository;
    }

    @GetMapping("/api/tasks")
    public Set<Task> getTasks(@AuthenticationPrincipal User currentUser) {
        return taskRepository.findAllByUser(currentUser);
    }
    @GetMapping("/api/tasks/{id}")
    public Task getTask(@PathVariable Long id) {
        return taskRepository.findById(id)
                .orElseThrow(() -> new NoSuchElementException());
    }
    @PostMapping("/api/tasks")
    public Task addTask(@RequestBody Task newTask, @AuthenticationPrincipal User currentUser) {
        newTask.setUser(currentUser);
        return taskRepository.save(newTask);
    }
    @PutMapping("/api/tasks/{id}")
    public Task updateTask(@RequestBody Task newTask, @PathVariable Long id) {
        return taskRepository.findById(id)
                .map(task -> {
                    task.setAllDay(newTask.isAllDay());
                    task.setDescription(newTask.getDescription());
                    task.setTitle(newTask.getTitle());
                    task.setStartDate(newTask.getStartDate());
                    task.setEndDate(newTask.getEndDate());
                    task.setPriority(newTask.getPriority());
                    return taskRepository.save(task);
                })
                .orElseGet(() -> {
                    newTask.setId(id);
                    return taskRepository.save(newTask);
                });
    }
    @DeleteMapping("/api/tasks/{id}")
    void deleteTask(@PathVariable Long id) {
        taskRepository.deleteById(id);
    }
}
