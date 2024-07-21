package ru.yandex.practicum.filmorate.dto.mappers;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import ru.yandex.practicum.filmorate.dto.FilmGenreDto;
import ru.yandex.practicum.filmorate.model.film.FilmGenre;

@Component
@RequiredArgsConstructor
public class FilmGenreDtoMapper {
    public FilmGenreDto filmGenreToDto(FilmGenre filmGenre) {
        FilmGenreDto filmGenreDto = new FilmGenreDto();
        filmGenreDto.setId(filmGenre.getId());
        filmGenreDto.setName(filmGenre.getName());
        return filmGenreDto;
    }

    public FilmGenre filmGenreFromDto(FilmGenreDto filmGenreDto) {
        FilmGenre filmGenre = new FilmGenre();
        filmGenre.setId(filmGenreDto.getId());
        filmGenre.setName(filmGenreDto.getName());
        return filmGenre;
    }
}
