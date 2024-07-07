package ru.yandex.practicum.filmorate.storages.memory;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import ru.yandex.practicum.filmorate.exceptions.NotFoundException;
import ru.yandex.practicum.filmorate.model.film.Film;
import ru.yandex.practicum.filmorate.storages.FilmStorage;

import java.util.HashMap;
import java.util.Map;
import java.util.Set;

@Slf4j
@Component("inMemoryFilmStorage")
@RequiredArgsConstructor
public class InMemoryFilmStorage implements FilmStorage {
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
        film.setId(generateId());
        films.put(film.getId(), film);
        return film;
    }

    @Override
    public Film update(Film film) {
        if (films.containsKey(film.getId())) {

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
            String errorMessage = String.format("The film has not been found with id = %d", film.getId());
            log.warn(errorMessage);
            throw new NotFoundException(errorMessage);
        }
    }

    @Override
    public void remove(long id) {
        films.remove(id);
    }

    @Override
    public void addLike(Film film, long userId) {
        film.getLikeUserIds().add(userId);
    }

    @Override
    public void removeLike(Film film, long userId) {
        film.getLikeUserIds().remove(userId);
    }

    @Override
    public void addFilmGenreIds(Film film, Set<Integer> genreIds) {
    }

    private long generateId() {
        long currentMaxId = films.keySet().stream()
                .mapToLong(id -> id).max().orElse(0L);
        return ++currentMaxId;
    }
}
