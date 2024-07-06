package ru.yandex.practicum.filmorate.dto;

import lombok.AccessLevel;
import lombok.Data;
import lombok.experimental.FieldDefaults;
import ru.yandex.practicum.filmorate.model.film.FilmGenre;

@Data
@FieldDefaults(level = AccessLevel.PRIVATE)
public class FilmGenreDto {
    int id;
    FilmGenre filmGenre;
}
