package ru.yandex.practicum.filmorate.storages.film;

import ru.yandex.practicum.filmorate.model.film.Film;

import java.util.Map;

public interface FilmStorage {
    Film getFilmById(long filmId);

    Map<Long, Film> findAllFilms();

    Film create(Film film);

    Film update(Film film);

    void remove(long id);
}
