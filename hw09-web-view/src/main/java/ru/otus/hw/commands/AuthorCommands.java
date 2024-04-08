package ru.otus.hw.commands;

import lombok.RequiredArgsConstructor;
import ru.otus.hw.converters.AuthorConverter;
import ru.otus.hw.services.AuthorService;

import java.util.stream.Collectors;

@RequiredArgsConstructor

public class AuthorCommands {

    private final AuthorService authorService;

    private final AuthorConverter authorConverter;


    public String findAllAuthors() {
        return authorService.findAll().stream()
                .map(authorConverter::authorToString)
                .collect(Collectors.joining("," + System.lineSeparator()));
    }
}
