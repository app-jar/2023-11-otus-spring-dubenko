package ru.otus.hw.i18n;

public interface MessageService {
    String get(String key, Object... params);

    String get(String key);
}
