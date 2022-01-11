package com.ru.secretary.springwebapp.util;

import com.ru.secretary.springwebapp.domain.User;
import com.ru.secretary.springwebapp.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;

@Service
public class UserVerificator {

    private final UserRepository userRepository;

    private UserVerificator(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public void VerifyUserPhoneNumber(String phoneNumber, Long chatId) throws IllegalArgumentException {

        var formattedPhoneNumber = PhoneNumberParser.getInstance().ParsePhoneNumber(phoneNumber);

        User user = userRepository.findByPhoneNumber(formattedPhoneNumber);

        if (user != null) {
            if (user.hasVerifiedPhoneNumber()) throw new VerifyError("This phone number is already used in Secretary");
            user.setVerifiedPhoneNumber(true);
            user.setTelegramChatId(chatId);
            userRepository.save(user);
        } else throw new VerifyError("User with such phone number not found in Secretary database");
    }
}
