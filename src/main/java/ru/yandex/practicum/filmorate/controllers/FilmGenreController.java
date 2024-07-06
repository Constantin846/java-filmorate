package ru.yandex.practicum.filmorate.controllers;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import ru.yandex.practicum.filmorate.dto.FilmGenreDto;
import ru.yandex.practicum.filmorate.services.film.FilmGenreService;

import java.util.Collection;

@RestController
@RequestMapping("/genres")
@RequiredArgsConstructor
public class FilmGenreController {
    private final FilmGenreService filmGenreService;

    @GetMapping("{genreId}")
    public FilmGenreDto getFilmById(@PathVariable int genreId) {
        return filmGenreService.getFilmGenreById(genreId);
    }

    @GetMapping
    public Collection<FilmGenreDto> findAllFilmGenres() {
        return filmGenreService.findAllFilmGenres();
    }
}
