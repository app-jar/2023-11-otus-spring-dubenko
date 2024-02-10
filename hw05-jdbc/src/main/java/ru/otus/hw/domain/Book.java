package ru.otus.hw.domain;

import lombok.Data;
import lombok.experimental.Accessors;

import java.util.List;

@Data
@Accessors(fluent = true)
public class Book {

    private Long id;

    private String name;

    private Author author;

    private List<Category> categories;
}