package ru.yandex.practicum.filmorate.storages;

import ru.yandex.practicum.filmorate.model.film.FilmGenre;

import java.util.List;

public interface FilmGenreStorage {
    FilmGenre getFilmGenreById(int genreId);

    List<FilmGenre> findAllFilmGenre();

    int getFilmGenreIdByName(String name);
}
