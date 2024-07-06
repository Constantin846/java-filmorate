package ru.yandex.practicum.filmorate.storages;

import ru.yandex.practicum.filmorate.dto.FilmGenreDto;

import java.util.List;

public interface FilmGenreStorage {
    FilmGenreDto getFilmGenreById(int genreId);

    List<FilmGenreDto> findAllFilmGenres();
}
