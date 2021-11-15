package com.ru.secretary.springwebapp.repositories;

import com.ru.secretary.springwebapp.domain.Task;
import com.ru.secretary.springwebapp.domain.User;
import org.springframework.data.repository.CrudRepository;

import java.util.Set;

public interface TaskRepository extends CrudRepository<Task, Long> {
    Set<Task> findAllByUser(User user);
}
