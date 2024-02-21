package ru.otus.hw.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;
import ru.otus.hw.models.Author;

@Data
@Accessors(fluent = true)
@NoArgsConstructor
@AllArgsConstructor
public class AuthorDto {

    private long id;

    private String fullName;

    public AuthorDto(Author author) {
        this(author.getId(), author.getFullName());
    }
}
