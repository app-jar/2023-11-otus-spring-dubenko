package ru.otus.hw.service.io.i18n;

public interface MessageService {
    String get(String key, Object... params);

    String get(String key);
}
