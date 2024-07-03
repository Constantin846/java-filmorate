package ru.yandex.practicum.filmorate.services.film;

import ru.yandex.practicum.filmorate.model.film.Film;

import java.util.List;
import java.util.Map;

public interface FilmService {
    Film getFilmById(long filmId);

    Map<Long, Film> findAllFilms();

    Film create(Film film);

    Film update(Film film);

    Film addLike(long filmId, long userId);

    Film removeLike(long filmId, long userId);

    List<Film> findPopularFilms(int count);
}
