package ru.yandex.practicum.filmorate.services.film;

import ru.yandex.practicum.filmorate.model.film.Film;

import java.util.List;

public interface FilmService {
    Film addLike(long filmId, long userId);

    Film removeLike(long filmId, long userId);

    List<Film> findPopularFilms(int count);
}
