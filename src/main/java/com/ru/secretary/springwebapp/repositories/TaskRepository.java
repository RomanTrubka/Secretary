package com.ru.secretary.springwebapp.repositories;

import com.ru.secretary.springwebapp.domain.Task;
import org.springframework.data.repository.CrudRepository;

public interface TaskRepository extends CrudRepository<Task, Long> {
}
