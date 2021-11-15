package com.ru.secretary.springwebapp.repositories;

import com.ru.secretary.springwebapp.domain.Task;
import com.ru.secretary.springwebapp.domain.User;
import org.springframework.data.repository.CrudRepository;

public interface TaskRepository extends CrudRepository<Task, Long> {
    Task findAllByUser(User user);
}
