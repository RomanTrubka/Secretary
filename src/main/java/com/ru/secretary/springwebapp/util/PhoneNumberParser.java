package com.ru.secretary.springwebapp.util;

import com.google.i18n.phonenumbers.PhoneNumberUtil;

//Singleton
public class PhoneNumberParser {

    private static PhoneNumberParser instance;
    private final String DEFAULT_REGION = "+7";

    private PhoneNumberParser() {}

    public static PhoneNumberParser getInstance() {
        if (instance == null) {
            instance = new PhoneNumberParser();
        }
        return instance;
    }

    public String ParsePhoneNumber(String text) {
        var phoneNumberUtil = PhoneNumberUtil.getInstance();
        var phoneNumberMatches = phoneNumberUtil.findNumbers(text, DEFAULT_REGION);
        return phoneNumberMatches.iterator().hasNext() ? phoneNumberMatches.iterator().next().number().toString() : "";
    }
}
