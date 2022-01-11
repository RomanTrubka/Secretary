package com.ru.secretary.springwebapp.bootstrap;

import com.ru.secretary.springwebapp.domain.Task;
import com.ru.secretary.springwebapp.domain.TaskPriority;
import com.ru.secretary.springwebapp.domain.User;
import com.ru.secretary.springwebapp.repositories.TaskRepository;
import com.ru.secretary.springwebapp.repositories.UserRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

import java.text.SimpleDateFormat;
import java.util.*;

@Component
public class BootStrapData implements CommandLineRunner {

    private final UserRepository userRepository;
    private final TaskRepository taskRepository;
    private final PasswordEncoder passwordEncoder;

    public BootStrapData(UserRepository userRepository, TaskRepository taskRepository, PasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.taskRepository = taskRepository;
        this.passwordEncoder = passwordEncoder;
    }

    @Override
    public void run(String... args) throws Exception {

        User testUser = new User("test", "test");

        SimpleDateFormat dateTimeFormatter = new SimpleDateFormat("MMM dd yyyy HH:mm");
        SimpleDateFormat shortDateTimeFormatter = new SimpleDateFormat("MMM dd yyyy");
        var date = shortDateTimeFormatter.format(new Date());

        List<Task> taskList = new ArrayList<>();

        taskList.add(new Task(testUser,
                "Cinema",
                "Spider-man",
                dateTimeFormatter.parse(date + " 20:00"),
                dateTimeFormatter.parse(date + " 22:30"),
                TaskPriority.MIDDLE));

        taskList.add(new Task(testUser, "Dentist", "Go to the dentist, room 207, Rikova Olga Nikolaevna",
                dateTimeFormatter.parse(date + " 14:30"), false));

        taskList.add(new Task(testUser, "Brother`s birthday", "Don`t forget to buy his favourite Becherovka",
                dateTimeFormatter.parse(date + " 18:00"), true));


        taskList.add(new Task(testUser, "Car service",
                "Don`t forget spare parts and oil",
                dateTimeFormatter.parse(date + " 12:00"),
                dateTimeFormatter.parse(date + " 14:00"),
                TaskPriority.HIGH));

        testUser.setPassword(passwordEncoder.encode(testUser.getPassword()));
        userRepository.save(testUser);

        for (Task task : taskList) {
            testUser.getTasks().add(task);

            task.setUser(testUser);
            taskRepository.save(task);
        }

        System.out.println("Number of users " + userRepository.count());
    }
}
