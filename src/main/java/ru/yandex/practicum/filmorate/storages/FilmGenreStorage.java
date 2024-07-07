package ru.yandex.practicum.filmorate.storages;

import ru.yandex.practicum.filmorate.dto.FilmGenreDto;
import ru.yandex.practicum.filmorate.model.film.FilmGenre;

import java.util.List;

public interface FilmGenreStorage {
    FilmGenreDto getFilmGenreDtoById(int genreId);

    List<FilmGenreDto> findAllFilmGenreDto();

    int getFilmGenreIdByName(String name);

    FilmGenre getFilmGenreById(int genreId);
}
