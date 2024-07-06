package ru.yandex.practicum.filmorate.services.film;

import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;
import ru.yandex.practicum.filmorate.exceptions.ConditionsNotMetException;
import ru.yandex.practicum.filmorate.exceptions.NotFoundException;
import ru.yandex.practicum.filmorate.model.film.Film;
import ru.yandex.practicum.filmorate.storages.FilmStorage;
import ru.yandex.practicum.filmorate.storages.UserStorage;

import java.util.Comparator;
import java.util.HashSet;
import java.util.List;
import java.util.Map;

@Service
@RequiredArgsConstructor
public class FilmServiceImpl implements FilmService {
    private static final Logger log = LoggerFactory.getLogger(FilmServiceImpl.class);
    @Qualifier("filmDbRepository")
    private final FilmStorage filmStorage;
    @Qualifier("userDbRepository")
    private final UserStorage userStorage;

    @Override
    public Film getFilmById(long filmId) {
        return filmStorage.getFilmById(filmId);
    }

    @Override
    public Map<Long, Film> findAllFilms() {
        return filmStorage.findAllFilms();
    }

    @Override
    public Film create(Film film) {
        film.setLikeUserIds(new HashSet<>());
        film.setFilmGenres(new HashSet<>());
        return filmStorage.create(film);
    }

    @Override
    public Film update(Film film) {
        return filmStorage.update(film);
    }

    @Override
    public Film addLike(long filmId, long userId) {
        if (checkUserExists(userId)) {
            Film film = filmStorage.getFilmById(filmId);
            filmStorage.addLike(film, userId);
            return filmStorage.getFilmById(filmId);
        }
        return null;
    }

    @Override
    public Film removeLike(long filmId, long userId) {
        if (checkUserExists(userId)) {
            Film film = filmStorage.getFilmById(filmId);
            filmStorage.removeLike(film, userId);
            return filmStorage.getFilmById(filmId);
        }
        return null;
    }

    @Override
    public List<Film> findPopularFilms(int count) {
        if (count < 1) {
            String message = String.format("The count must not be less 1: %d", count);
            log.warn(message);
            throw new ConditionsNotMetException(message);
        }

        int reverseSorted = -1;

        Map<Long, Film> films = filmStorage.findAllFilms();
        return films.values().stream()
                .sorted(Comparator.comparingInt(film -> reverseSorted * film.getLikeUserIds().size()))
                .limit(count)
                .toList();
    }

    private boolean checkUserExists(long userId) {
        if (userStorage.checkUserExists(userId)) {
            return true;
        }
        String errorMessage = String.format("An user was not found by id: %s", userId);
        log.warn(errorMessage);
        throw new NotFoundException(errorMessage);
    }
}
