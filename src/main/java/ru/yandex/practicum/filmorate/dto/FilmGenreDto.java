package ru.yandex.practicum.filmorate.dto;

import lombok.AccessLevel;
import lombok.Data;
import lombok.experimental.FieldDefaults;
import ru.yandex.practicum.filmorate.model.film.FilmGenre;

@Data
@FieldDefaults(level = AccessLevel.PRIVATE)
public class FilmGenreDto {
    int id;
    String name;

    public void setName(String name) {
        switch (FilmGenre.valueOf(name)) {
            case DRAMA -> this.name = "Драма";
            case COMEDY -> this.name = "Комедия";
            case CARTOON -> this.name = "Мультфильм";
            case THRILLER -> this.name = "Триллер";
            case DOCUMENTARY -> this.name = "Документальный";
            case ACTION -> this.name = "Боевик";
            default -> this.name = name;
        }
    }
}
