package ru.otus.hw.service.io.i18n;

public interface MessageIOService {

    void printMessage(String key, Object params);

    void printMessage(String key);

}
