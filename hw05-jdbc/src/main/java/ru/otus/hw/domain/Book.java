package ru.otus.hw.domain;

import lombok.Data;
import lombok.experimental.Accessors;

@Data
@Accessors(fluent = true)
public class Book {

    private Long id;

    private String name;

    private Author author;
}
