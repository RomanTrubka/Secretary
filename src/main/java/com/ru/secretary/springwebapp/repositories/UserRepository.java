package com.ru.secretary.springwebapp.repositories;

import com.ru.secretary.springwebapp.domain.User;
import org.springframework.data.repository.CrudRepository;

public interface UserRepository extends CrudRepository<User, Long> {
    User findByName(String name);
    User findByPhoneNumber(String phoneNumber);
}

