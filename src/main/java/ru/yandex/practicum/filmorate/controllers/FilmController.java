package ru.yandex.practicum.filmorate.controllers;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import ru.yandex.practicum.filmorate.model.film.Film;
import ru.yandex.practicum.filmorate.services.film.FilmService;
import ru.yandex.practicum.filmorate.storages.film.FilmStorage;

import java.util.Collection;

@RestController
@RequestMapping("/films")
@RequiredArgsConstructor
public class FilmController {
    private static final String FILM_ID_LIKE_USER_ID = "/{filmId}/like/{userId}";
    private final FilmStorage filmStorage;
    private final FilmService filmService;

    @GetMapping("{filmId}")
    public Film getFilmById(@PathVariable long filmId) {
        return filmStorage.getFilmById(filmId);
    }

    @GetMapping
    public Collection<Film> findAllFilms() {
        return filmStorage.findAllFilms().values();
    }

    @PostMapping
    public Film create(@RequestBody Film film) {
        return filmStorage.create(film);
    }

    @PutMapping
    public Film update(@RequestBody Film film) {
        return filmStorage.update(film);
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
