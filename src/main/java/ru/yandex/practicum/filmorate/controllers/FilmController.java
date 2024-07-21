package ru.yandex.practicum.filmorate.controllers;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import ru.yandex.practicum.filmorate.dto.FilmDto;
import ru.yandex.practicum.filmorate.exceptions.ValidationException;
import ru.yandex.practicum.filmorate.services.film.FilmService;
import ru.yandex.practicum.filmorate.validators.film.FilmValidator;

import java.util.Collection;
import java.util.List;

@Slf4j
@RestController
@RequestMapping("/films")
@RequiredArgsConstructor
public class FilmController {
    private static final String FILM_ID_LIKE_USER_ID = "/{film-id}/like/{user-id}";
    private final FilmService filmService;
    private final FilmValidator filmValidator;

    @GetMapping("/{film-id}")
    public FilmDto getById(@PathVariable("film-id") long filmId) {
        return filmService.getFilmById(filmId);
    }

    @GetMapping
    public Collection<FilmDto> findAll() {
        return filmService.findAllFilms().values();
    }

    @PostMapping
    public FilmDto create(@Valid @RequestBody FilmDto filmDto) {
        if (filmValidator.checkFilmValidation(filmDto)) {
            return filmService.create(filmDto);
        }
        String errorMessage = String.format("Invalid data of the %s", filmDto);
        log.warn(errorMessage);
        throw new ValidationException(errorMessage);
    }

    @PutMapping
    public FilmDto update(@Valid @RequestBody FilmDto filmDto) {
        if (filmDto.getId() == null) {
            String errorMessage = String.format("The film's id is null: %s", filmDto);
            log.warn(errorMessage);
            throw new ValidationException(errorMessage);

        } else if (!filmValidator.checkFilmValidation(filmDto)) {
            String errorMessage = String.format("Invalid data of the %s", filmDto);
            log.warn(errorMessage);
            throw new ValidationException(errorMessage);
        }
        return filmService.update(filmDto);
    }

    @PutMapping(FILM_ID_LIKE_USER_ID)
    public FilmDto addLike(@PathVariable("film-id") long filmId, @PathVariable("user-id") long userId) {
        return filmService.addLike(filmId, userId);
    }

    @DeleteMapping(FILM_ID_LIKE_USER_ID)
    public FilmDto removeLike(@PathVariable("film-id") long filmId, @PathVariable("user-id") long userId) {
        return filmService.removeLike(filmId, userId);
    }

    @GetMapping("/popular")
    public List<FilmDto> findPopularFilms(@RequestParam(defaultValue = "10") int count) {
        return filmService.findPopularFilms(count);
    }
}
