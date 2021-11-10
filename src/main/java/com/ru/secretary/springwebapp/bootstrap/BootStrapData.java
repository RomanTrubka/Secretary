package com.ru.secretary.springwebapp.bootstrap;

import com.ru.secretary.springwebapp.domain.Task;
import com.ru.secretary.springwebapp.domain.TaskPriority;
import com.ru.secretary.springwebapp.domain.User;
import com.ru.secretary.springwebapp.repositories.TaskRepository;
import com.ru.secretary.springwebapp.repositories.UserRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import java.time.LocalDate;
import java.util.Date;

@Component
public class BootStrapData implements CommandLineRunner {

    private final UserRepository userRepository;
    private final TaskRepository taskRepository;

    public BootStrapData(UserRepository userRepository, TaskRepository taskRepository) {
        this.userRepository = userRepository;
        this.taskRepository = taskRepository;
    }

    @Override
    public void run(String... args) throws Exception {

        User biba = new User("Biba Chelemyakin", "+79105226617");
        Task read = new Task(biba, "Read", "Read an important article", new Date(), TaskPriority.MIDDLE);

        biba.getTasks().add(read);
        read.setUser(biba);
        userRepository.save(biba);
        taskRepository.save(read);

        System.out.println("Number of users " + userRepository.count());
    }
}
