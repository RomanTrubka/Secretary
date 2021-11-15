package com.ru.secretary.springwebapp.bootstrap;

import com.ru.secretary.springwebapp.domain.Task;
import com.ru.secretary.springwebapp.domain.TaskPriority;
import com.ru.secretary.springwebapp.domain.User;
import com.ru.secretary.springwebapp.repositories.TaskRepository;
import com.ru.secretary.springwebapp.repositories.UserRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

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

        User biba = new User("Biba Chelemyakin", "1234",  "+79105226617");
        Task read = new Task(biba, "Read", "Read an important article", new Date(), TaskPriority.MIDDLE);

        User testUser = new User("test", "test", "544104");

        biba.getTasks().add(read);
        read.setUser(biba);
        userRepository.save(biba);
        userRepository.save(testUser);
        taskRepository.save(read);

        System.out.println("Number of users " + userRepository.count());
    }
}
