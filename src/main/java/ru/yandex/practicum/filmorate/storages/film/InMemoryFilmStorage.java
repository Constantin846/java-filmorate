package ru.yandex.practicum.filmorate.storages.film;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;
import ru.yandex.practicum.filmorate.controllers.FilmController;
import ru.yandex.practicum.filmorate.exceptions.NotFoundException;
import ru.yandex.practicum.filmorate.exceptions.ValidationException;
import ru.yandex.practicum.filmorate.model.film.Film;

import java.time.LocalDate;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;

@Component
public class InMemoryFilmStorage implements FilmStorage {
    private static final Logger log = LoggerFactory.getLogger(FilmController.class);
    private final Map<Long, Film> films = new HashMap<>();

    @Override
    public Film getFilmById(long filmId) {
        if (films.containsKey(filmId)) {
            return films.get(filmId);
        }
        String errorMessage = String.format("An user was not found by id: %s", filmId);
        log.warn(errorMessage);
        throw new NotFoundException(errorMessage);
    }

    @Override
    public Map<Long, Film> findAllFilms() {
        return films;
    }

    @Override
    public Film create(Film film) {
        if (checkFilmValidation(film)) {
            film.setId(generateId());
            film.setLikeUserIds(new HashSet<>());
            films.put(film.getId(), film);
            return film;
        }
        String errorMessage = String.format("Invalid data of the %s", film);
        log.warn(errorMessage);
        throw new ValidationException(errorMessage);
    }

    @Override
    public Film update(Film film) {
        if (film.getId() == null) {
            String errorMessage = String.format("The film's id is null: %s", film);
            log.warn(errorMessage);
            throw new ValidationException(errorMessage);
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
                String errorMessage = String.format("Invalid data of the %s", film);
                log.warn(errorMessage);
                throw new ValidationException(errorMessage);
            }
        } else {
            String errorMessage = String.format("The film has not been found with id = %d", film.getId());
            log.warn(errorMessage);
            throw new NotFoundException(errorMessage);
        }
    }

    @Override
    public void remove(long id) {
        films.remove(id);
    }

    private static final int MAX_LENGTH_OF_DESCRIPTION = 200;
    private static final LocalDate MOVIE_BIRTHDAY = LocalDate.of(1895,12,28);

    private boolean checkFilmValidation(Film film) {
        if (film.getName() == null || film.getName().isBlank()) {
            String errorMessage = String.format("The film's name must not be empty: %s", film);
            log.warn(errorMessage);
            throw new ValidationException(errorMessage);
        }

        if (film.getDescription() != null && film.getDescription().length() > MAX_LENGTH_OF_DESCRIPTION) {
            StringBuilder errorMessage = new StringBuilder("Max length of film description is ");
            errorMessage.append(MAX_LENGTH_OF_DESCRIPTION);
            errorMessage.append(" symbols.\n");
            errorMessage.append(film.getDescription().length());
            errorMessage.append(" symbols is entered");

            log.warn(errorMessage.toString());
            throw new ValidationException(errorMessage.toString());
        }

        if (film.getReleaseDate() != null && film.getReleaseDate().isBefore(MOVIE_BIRTHDAY)) {
            String errorMessage = String.format("The film's release date must be after 28.12.1895: %s", film);
            log.warn(errorMessage);
            throw new ValidationException(errorMessage);
        }

        if (film.getDuration() < 0) {
            String errorMessage = String.format("The film's duration must be positive: %s", film);
            log.warn(errorMessage);
            throw new ValidationException(errorMessage);
        }

        return true;
    }

    private long generateId() {
        long currentMaxId = films.keySet().stream()
                .mapToLong(id -> id).max().orElse(0L);
        return ++currentMaxId;
    }
}
