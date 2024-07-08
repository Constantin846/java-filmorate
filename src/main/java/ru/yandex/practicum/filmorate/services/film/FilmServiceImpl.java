package ru.yandex.practicum.filmorate.services.film;

import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import ru.yandex.practicum.filmorate.dto.FilmDto;
import ru.yandex.practicum.filmorate.dto.FilmGenreDto;
import ru.yandex.practicum.filmorate.dto.mappers.FilmDtoMapper;
import ru.yandex.practicum.filmorate.exceptions.ConditionsNotMetException;
import ru.yandex.practicum.filmorate.exceptions.NotFoundException;
import ru.yandex.practicum.filmorate.model.film.Film;
import ru.yandex.practicum.filmorate.storages.FilmStorage;
import ru.yandex.practicum.filmorate.storages.UserStorage;

import java.util.Comparator;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class FilmServiceImpl implements FilmService {
    private static final Logger log = LoggerFactory.getLogger(FilmServiceImpl.class);
    private final FilmStorage filmStorage;
    private final UserStorage userStorage;
    private final FilmDtoMapper filmDtoMapper;

    @Override
    public FilmDto getFilmById(long filmId) {
        Film film = filmStorage.getFilmById(filmId);
        return filmDtoMapper.filmToDto(film);
    }

    @Override
    public Map<Long, FilmDto> findAllFilms() {
        Map<Long, Film> films = filmStorage.findAllFilms();
        return films.values().stream()
                .collect(Collectors.toMap(Film::getId, filmDtoMapper::filmToDto));
    }

    @Override
    public FilmDto create(FilmDto filmDto) {
        Film film = filmDtoMapper.filmFromDto(filmDto);
        film.setLikeUserIds(new HashSet<>());

        if (film.getFilmGenres() == null) {
            film.setFilmGenres(new HashSet<>());
        }
        Film newFilm = filmStorage.create(film);

        if (filmDto.getGenres() != null) {
            Set<Integer> genreIds = filmDto.getGenres().stream()
                    .map(FilmGenreDto::getId)
                    .collect(Collectors.toSet());
            filmStorage.addFilmGenreIds(newFilm, genreIds);
        }

        return filmDtoMapper.filmToDto(newFilm);
    }

    @Override
    public FilmDto update(FilmDto filmDto) {
        Film film = filmDtoMapper.filmFromDto(filmDto);
        Film updatedFilm = filmStorage.update(film);
        return filmDtoMapper.filmToDto(updatedFilm);
    }

    @Override
    public FilmDto addLike(long filmId, long userId) {
        if (checkUserExists(userId)) {
            Film film = filmStorage.getFilmById(filmId);
            filmStorage.addLike(film, userId);
            Film updatedFilm = filmStorage.getFilmById(filmId);
            return filmDtoMapper.filmToDto(updatedFilm);
        }
        return null;
    }

    @Override
    public FilmDto removeLike(long filmId, long userId) {
        if (checkUserExists(userId)) {
            Film film = filmStorage.getFilmById(filmId);
            filmStorage.removeLike(film, userId);
            Film updatedFilm = filmStorage.getFilmById(filmId);
            return filmDtoMapper.filmToDto(updatedFilm);
        }
        return null;
    }

    @Override
    public List<FilmDto> findPopularFilms(int count) {
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
                .map(filmDtoMapper::filmToDto)
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
