package ru.otus.hw.domain;

import lombok.Data;
import lombok.experimental.Accessors;

@Data
@Accessors(fluent = true)
public class BookSearch {

    private String nameQuery = null;

    private String authorNameQuery = null;
}
