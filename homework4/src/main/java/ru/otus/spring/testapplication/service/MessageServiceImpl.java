package ru.otus.spring.testapplication.service;

import lombok.RequiredArgsConstructor;
import org.springframework.context.MessageSource;
import org.springframework.stereotype.Service;
import ru.otus.spring.testapplication.config.LocaleProvider;

@Service
@RequiredArgsConstructor
public class MessageServiceImpl implements MessageService {

    private final LocaleProvider localeProvider;

    private final MessageSource messageSource;

    @Override
    public String getMessage(String code) {
        return getMessage(code, new String[]{});
    }

    @Override
    public String getMessage(String code, String[] args) {
        return messageSource.getMessage(code, args, localeProvider.getCurrentLocale());
    }

}
