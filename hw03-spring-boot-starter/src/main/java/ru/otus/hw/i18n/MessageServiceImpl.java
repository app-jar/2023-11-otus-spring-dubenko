package ru.otus.hw.i18n;

import lombok.RequiredArgsConstructor;
import org.springframework.context.MessageSource;
import org.springframework.stereotype.Service;
import ru.otus.hw.config.MessageConfig;

import java.util.Locale;

@Service
@RequiredArgsConstructor
public class MessageServiceImpl implements MessageService {

    private final MessageSource messageSource;

    private final MessageConfig config;

    @Override
    public String get(String key, Object... params) {
        return messageSource.getMessage(key, params, new Locale(config.getLocale()));
    }

    @Override
    public String get(String key) {
        return get(key, new Object[]{});
    }
}
