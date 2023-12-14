package ru.otus.hw.service.io;

public interface OutputService {

    void printLine(String s);

    void printFormattedLine(String s, Object ...args);
}
