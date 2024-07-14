package ru.yandex.practicum.filmorate.controllers;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import ru.yandex.practicum.filmorate.dto.FilmGenreDto;
import ru.yandex.practicum.filmorate.services.film.FilmGenreService;

import java.util.Collection;
import java.util.Comparator;
import java.util.TreeSet;

@RestController
@RequestMapping("/genres")
@RequiredArgsConstructor
public class FilmGenreController {
    private final FilmGenreService filmGenreService;

    @GetMapping("/{genre-id}")
    public FilmGenreDto getById(@PathVariable("genre-id") int genreId) {
        return filmGenreService.getFilmGenreDtoById(genreId);
    }

    @GetMapping
    public Collection<FilmGenreDto> findAll() {
        TreeSet<FilmGenreDto> sortedFilmGenreDto = new TreeSet<>(Comparator.comparingInt(FilmGenreDto::getId));
        sortedFilmGenreDto.addAll(filmGenreService.findAllFilmGenreDto());
        return sortedFilmGenreDto;
    }
}
