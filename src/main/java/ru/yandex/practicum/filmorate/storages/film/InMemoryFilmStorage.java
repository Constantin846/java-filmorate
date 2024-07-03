package ru.yandex.practicum.filmorate.storages.film;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import ru.yandex.practicum.filmorate.exceptions.NotFoundException;
import ru.yandex.practicum.filmorate.exceptions.ValidationException;
import ru.yandex.practicum.filmorate.model.film.Film;
import ru.yandex.practicum.filmorate.validators.film.FilmValidator;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;

@Slf4j
@Component
@RequiredArgsConstructor
public class InMemoryFilmStorage implements FilmStorage {
    private final Map<Long, Film> films = new HashMap<>();
    private final FilmValidator filmValidator;

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
        if (filmValidator.checkFilmValidation(film)) {
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
            if (filmValidator.checkFilmValidation(film)) {
                Film oldFilm = films.get(film.getId());

                oldFilm.setName(film.getName());

                if (film.getDescription() != null) {
                    oldFilm.setDescription(film.getDescription());
                }
                if (film.getReleaseDate() != null) {
                    oldFilm.setReleaseDate(film.getReleaseDate());
                }
                if (film.getDuration() != null) {
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

    private long generateId() {
        long currentMaxId = films.keySet().stream()
                .mapToLong(id -> id).max().orElse(0L);
        return ++currentMaxId;
    }
}
