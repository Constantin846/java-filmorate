package ru.yandex.practicum.filmorate.model.film;

import lombok.AccessLevel;
import lombok.Data;
import lombok.experimental.FieldDefaults;

@Data
@FieldDefaults(level = AccessLevel.PRIVATE)
public class FilmGenre {
    Integer id;
    String name;
}
