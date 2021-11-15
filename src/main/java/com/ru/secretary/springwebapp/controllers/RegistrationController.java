package com.ru.secretary.springwebapp.controllers;

import com.ru.secretary.springwebapp.domain.User;
import com.ru.secretary.springwebapp.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

@Controller
public class RegistrationController {

    private final UserRepository userRepository;

    public RegistrationController(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @GetMapping("/registration")
    public String registration(){
        return "registration";
    }

    @PostMapping("/registration")
    public String registerUser(User user, Model model) {

        User userFromDb = userRepository.findByName(user.getName());

        if (userFromDb != null) {
            model.addAttribute("message", "User already exists!");
            return "registration";
        }

        userRepository.save(user);

        return "redirect:/login";
    }
}
