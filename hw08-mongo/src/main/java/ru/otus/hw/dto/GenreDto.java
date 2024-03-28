package ru.otus.hw.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;

@Data
@Accessors(fluent = true)
@NoArgsConstructor
@AllArgsConstructor
public class GenreDto {
    private long id;

    private String name;

}
