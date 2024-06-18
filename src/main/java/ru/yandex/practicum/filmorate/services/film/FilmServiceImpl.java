package ru.yandex.practicum.filmorate.services.film;

import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import ru.yandex.practicum.filmorate.exceptions.ConditionsNotMetException;
import ru.yandex.practicum.filmorate.exceptions.NotFoundException;
import ru.yandex.practicum.filmorate.model.film.Film;
import ru.yandex.practicum.filmorate.storages.film.FilmStorage;
import ru.yandex.practicum.filmorate.storages.user.UserStorage;

import java.util.Comparator;
import java.util.List;
import java.util.Map;

@Service
@RequiredArgsConstructor
public class FilmServiceImpl implements FilmService {
    private static final Logger log = LoggerFactory.getLogger(FilmServiceImpl.class);
    private final FilmStorage filmStorage;
    private final UserStorage userStorage;

    @Override
    public Film addLike(long filmId, long userId) {
        if (checkUserExists(userId)) {
            Film film = filmStorage.getFilmById(filmId);
            film.getLikeUserIds().add(userId);
            return film;
        }
        return null;
    }

    @Override
    public Film removeLike(long filmId, long userId) {
        if (checkUserExists(userId)) {
            Film film = filmStorage.getFilmById(filmId);
            film.getLikeUserIds().remove(userId);
            return film;
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

        Map<Long, Film> films = filmStorage.findAllFilms();
        return films.values().stream()
                .sorted(Comparator.comparingInt(film -> -1 * film.getLikeUserIds().size()))
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
