package ru.otus.hw.models;

import lombok.Getter;
import lombok.Setter;
import lombok.experimental.Accessors;

@Getter
@Setter
@Accessors(chain = true)
public class Genre {

    private long id;

    private String name;
}
