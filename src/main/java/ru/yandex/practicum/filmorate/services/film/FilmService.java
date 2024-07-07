package ru.yandex.practicum.filmorate.services.film;

import ru.yandex.practicum.filmorate.dto.FilmDto;

import java.util.List;
import java.util.Map;

public interface FilmService {
    FilmDto getFilmById(long filmId);

    Map<Long, FilmDto> findAllFilms();

    FilmDto create(FilmDto filmDto);

    FilmDto update(FilmDto filmDto);

    FilmDto addLike(long filmId, long userId);

    FilmDto removeLike(long filmId, long userId);

    List<FilmDto> findPopularFilms(int count);
}
