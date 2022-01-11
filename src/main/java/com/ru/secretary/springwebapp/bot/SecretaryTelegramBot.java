package com.ru.secretary.springwebapp.bot;

import com.ru.secretary.springwebapp.util.UserVerificator;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.bots.TelegramLongPollingBot;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.ReplyKeyboardMarkup;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.KeyboardButton;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.KeyboardRow;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;

import java.util.ArrayList;
import java.util.List;

@Component
public class SecretaryTelegramBot extends TelegramLongPollingBot {

    @Value("${bot.username}")
    private String username;

    @Value("${bot.token}")
    private String token;

    private static String REGION = "+7";

    @Override
    public void onUpdateReceived(Update update) {

        if (update.hasMessage()) {
            Long chatId = update.getMessage().getChatId();
            String message;
            SendMessage messageToSend = new SendMessage();

            if (update.getMessage().getContact() != null) {
                String phoneNumber = update.getMessage().getContact().getPhoneNumber();
                if (phoneNumber.startsWith("7") || phoneNumber.startsWith("8"))
                    phoneNumber = REGION + phoneNumber.substring(1, phoneNumber.length());
                try {
                    UserVerificator.VerifyUserPhoneNumber(phoneNumber, chatId);
                    message = "We have set " + phoneNumber + " as your Secretary phone number";
                } catch (VerifyError e) {
                    message = e.getMessage();
                }
            } else {
                message = "Hello! Share your phone number to use it in Secretary.";
                addPhoneSharingButton(messageToSend, "Set my phone as Secretary phone number");
            }

            messageToSend.setChatId(chatId.toString());
            messageToSend.setText(message);

            try {
                execute(messageToSend);
            } catch (TelegramApiException e) {
                //TODO add logging to the project. log4j
                e.printStackTrace();
            }
        }
    }

    public void addPhoneSharingButton(SendMessage message, String btnText) {

        ReplyKeyboardMarkup replyKeyboardMarkup = new ReplyKeyboardMarkup();
        KeyboardButton keyboardButton = new KeyboardButton();
        keyboardButton.setRequestContact(true);
        keyboardButton.setText(btnText);

        KeyboardRow keyboardRow = new KeyboardRow();
        keyboardRow.add(keyboardButton);
        List<KeyboardRow> rowList = new ArrayList<>();
        rowList.add(keyboardRow);

        replyKeyboardMarkup.setKeyboard(rowList);

        message.setReplyMarkup(replyKeyboardMarkup);
    }

    @Override
    public String getBotToken() {
        return token;
    }

    @Override
    public String getBotUsername() {
        return username;
    }
}
