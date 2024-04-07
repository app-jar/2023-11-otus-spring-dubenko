package ru.otus.hw.models;

import lombok.Getter;
import lombok.Setter;
import lombok.experimental.Accessors;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.DBRef;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.List;

@Getter
@Setter
@Accessors(chain = true)
@Document
public class Author {

    @Id
    private long id;

    private String fullName;

    @DBRef(lazy = true)
    private List<Book> books;
}
