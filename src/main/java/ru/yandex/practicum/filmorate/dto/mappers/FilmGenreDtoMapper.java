package ru.yandex.practicum.filmorate.dto.mappers;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import ru.yandex.practicum.filmorate.dto.FilmGenreDto;
import ru.yandex.practicum.filmorate.model.film.FilmGenre;
import ru.yandex.practicum.filmorate.storages.FilmGenreStorage;

@Component
@RequiredArgsConstructor
public class FilmGenreDtoMapper {
    private final FilmGenreStorage filmGenreStorage;

    public FilmGenreDto filmGenreToDto(FilmGenre filmGenre) {
        FilmGenreDto filmGenreDto = new FilmGenreDto();
        filmGenreDto.setId(filmGenreStorage.getFilmGenreIdByName(filmGenre.name()));
        filmGenreDto.setName(filmGenre.name());

        return filmGenreDto;
    }

    public FilmGenre filmGenreFromDto(FilmGenreDto filmGenreDto) {
        return filmGenreStorage.getFilmGenreById(filmGenreDto.getId());
    }
}
