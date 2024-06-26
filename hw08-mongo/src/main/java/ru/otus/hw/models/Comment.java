package ru.otus.hw.models;


import lombok.Getter;
import lombok.Setter;
import lombok.experimental.Accessors;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.DBRef;
import org.springframework.data.mongodb.core.mapping.Document;

@Getter
@Setter
@Accessors(chain = true)
@Document
public class Comment {

    @Id
    private String id;

    @DBRef(lazy = true)
    private Book book;

    private String text;
}
