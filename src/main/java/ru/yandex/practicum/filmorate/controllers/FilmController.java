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
import ru.yandex.practicum.filmorate.exceptions.ValidationException;
import ru.yandex.practicum.filmorate.model.film.Film;
import ru.yandex.practicum.filmorate.services.film.FilmService;
import ru.yandex.practicum.filmorate.validators.film.FilmValidator;

import java.util.Collection;

@Slf4j
@RestController
@RequestMapping("/films")
@RequiredArgsConstructor
public class FilmController {
    private static final String FILM_ID_LIKE_USER_ID = "/{filmId}/like/{userId}";
    private final FilmService filmService;
    private final FilmValidator filmValidator;

    @GetMapping("{filmId}")
    public Film getFilmById(@PathVariable long filmId) {
        return filmService.getFilmById(filmId);
    }

    @GetMapping
    public Collection<Film> findAllFilms() {
        return filmService.findAllFilms().values();
    }

    @PostMapping
    public Film create(@Valid @RequestBody Film film) {
        if (filmValidator.checkFilmValidation(film)) {
            return filmService.create(film);
        }
        String errorMessage = String.format("Invalid data of the %s", film);
        log.warn(errorMessage);
        throw new ValidationException(errorMessage);
    }

    @PutMapping
    public Film update(@Valid @RequestBody Film film) {
        if (film.getId() == null) {
            String errorMessage = String.format("The film's id is null: %s", film);
            log.warn(errorMessage);
            throw new ValidationException(errorMessage);

        } else if (!filmValidator.checkFilmValidation(film)) {
            String errorMessage = String.format("Invalid data of the %s", film);
            log.warn(errorMessage);
            throw new ValidationException(errorMessage);
        }
        return filmService.update(film);
    }

    @PutMapping(FILM_ID_LIKE_USER_ID)
    public Film addLike(@PathVariable long filmId, @PathVariable long userId) {
        return filmService.addLike(filmId, userId);
    }

    @DeleteMapping(FILM_ID_LIKE_USER_ID)
    public Film removeLike(@PathVariable long filmId, @PathVariable long userId) {
        return filmService.removeLike(filmId, userId);
    }

    @GetMapping("/popular")
    public Collection<Film> findPopularFilms(@RequestParam(defaultValue = "10") int count) {
        return filmService.findPopularFilms(count);
    }
}
