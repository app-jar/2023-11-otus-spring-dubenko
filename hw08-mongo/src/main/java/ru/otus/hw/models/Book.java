package ru.otus.hw.models;

import lombok.Getter;
import lombok.Setter;
import lombok.experimental.Accessors;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
@Accessors(chain = true)
@Document
public class Book {

    @Id
    private String id;

    private String title;

    private Author author;

    private List<Genre> genres = new ArrayList<>();
}
