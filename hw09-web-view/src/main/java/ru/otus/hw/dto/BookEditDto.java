package ru.otus.hw.dto;

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
public class BookEditDto implements Serializable {

    private long id = 0;

    private String title;

    private long authorId;

    private List<Long> genreIds = new ArrayList<>();

}
