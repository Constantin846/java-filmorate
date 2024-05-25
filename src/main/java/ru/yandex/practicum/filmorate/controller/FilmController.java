package ru.yandex.practicum.filmorate.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.*;
import ru.yandex.practicum.filmorate.exceptions.NotFoundException;
import ru.yandex.practicum.filmorate.exceptions.ValidationException;
import ru.yandex.practicum.filmorate.model.Film;

import java.time.LocalDate;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/films")
public class FilmController {
    private final static Logger log = LoggerFactory.getLogger(FilmController.class);
    private final Map<Long, Film> films = new HashMap<>();

    @GetMapping
    public Collection<Film> getFilms() {
        return films.values();
    }

    @PostMapping
    public Film create(@RequestBody Film film) {
        if (checkFilmValidation(film)) {
            film.setId(generateId());
            films.put(film.getId(), film);
            return film;
        }
        log.warn("Invalid data of the {}", film);
        throw new ValidationException("Invalid data of the film");
    }

    @PutMapping
    public Film update(@RequestBody Film film) {
        if (film.getId() == null) {
            log.warn("The film's id is null: {}", film);
            throw new ValidationException("Id must be specified");
        }

        if (films.containsKey(film.getId())) {
            if (checkFilmValidation(film)) {
                Film oldFilm = films.get(film.getId());
                
                oldFilm.setName(film.getName());
                
                if (film.getDescription() != null) {
                    oldFilm.setDescription(film.getDescription());
                }
                if (film.getReleaseDate() != null) {
                    oldFilm.setReleaseDate(film.getReleaseDate());
                }
                if (film.getDuration() != 0) {
                    oldFilm.setDuration(film.getDuration());
                }
                return oldFilm;

            } else {
                log.warn("Invalid data of the {}", film);
                throw new ValidationException("Invalid data of the film");
            }
        } else {
            StringBuilder errorMessage = new StringBuilder("The film has not been found with id = ");
            errorMessage.append(film.getId());
            log.warn(errorMessage.toString());
            throw new NotFoundException(errorMessage.toString());
        }
    }

    private static final int MAX_LENGTH_DESCRIPTION = 200;
    private static final LocalDate MOVIE_BIRTHDAY = LocalDate.of(1895,12,28);

    private boolean checkFilmValidation(Film film) {
        if (film.getName() == null || film.getName().isBlank()) {
            log.warn("The film's name must not be empty: {}", film);
            throw new ValidationException("The film's name must not be empty");
        }

        if (film.getDescription() != null && film.getDescription().length() > MAX_LENGTH_DESCRIPTION) {
            StringBuilder errorMessage = new StringBuilder("Max length of film description is ");
            errorMessage.append(MAX_LENGTH_DESCRIPTION);
            errorMessage.append(" symbols.\n");
            errorMessage.append(film.getDescription().length());
            errorMessage.append(" symbols is entered");

            log.warn(errorMessage.toString());
            throw new ValidationException(errorMessage.toString());
        }

        if (film.getReleaseDate() != null && film.getReleaseDate().isBefore(MOVIE_BIRTHDAY)) {
            log.warn("The film's release date must be after 28.12.1895: {}", film);
            throw new ValidationException("The film's release date must be after 28.12.1895");
        }

        if (film.getDuration() < 0) {
            log.warn("The film's duration must be positive: {}", film);
            throw new ValidationException("The film's duration must be positive");
        }

        return true;
    }

    private long generateId() {
        return films.keySet().stream()
                .mapToLong(id -> id).max().orElse(1L);
    }
}
