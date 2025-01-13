package ru.otus.hw.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

@Data
@Accessors(chain = true)
@NoArgsConstructor
@AllArgsConstructor
public class BookCreateDto implements Serializable {

    private long id = 0;

    @NotBlank
    private String title;

    @NotNull
    private long authorId;

    @NotNull
    private List<Long> genreIds = new ArrayList<>();

}
