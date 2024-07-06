package ru.yandex.practicum.filmorate.services.film;

import ru.yandex.practicum.filmorate.dto.FilmGenreDto;

import java.util.List;

public interface FilmGenreService {
    FilmGenreDto getFilmGenreById(int genreId);

    List<FilmGenreDto> findAllFilmGenres();
}
