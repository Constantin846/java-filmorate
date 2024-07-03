package ru.yandex.practicum.filmorate.storages;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import ru.yandex.practicum.filmorate.exceptions.ValidationException;
import ru.yandex.practicum.filmorate.model.film.Film;
import ru.yandex.practicum.filmorate.storages.film.FilmStorage;
import ru.yandex.practicum.filmorate.storages.film.InMemoryFilmStorage;
import ru.yandex.practicum.filmorate.validators.film.FilmValidator;
import ru.yandex.practicum.filmorate.validators.film.FilmValidatorImpl;

import java.time.Duration;
import java.time.LocalDate;

@SpringBootTest
public class FilmStorageTest {
    private FilmValidator filmValidator = new FilmValidatorImpl();
    private FilmStorage filmStorage;
    private Film film;

    @BeforeEach
    void beforeEach() {
        filmStorage = new InMemoryFilmStorage(filmValidator);
        film = new Film();
        film.setName("name");
        film.setDescription("description");
        film.setReleaseDate(LocalDate.of(1895,12,28));
        film.setDuration(Duration.ofMinutes(1));
    }

    @Test
    void create_shouldAddValidFilm() {
        Film actualFilm = filmStorage.create(film);

        Assertions.assertEquals(film, actualFilm);
        Assertions.assertEquals(film.getName(), actualFilm.getName());
        Assertions.assertEquals(film.getDescription(), actualFilm.getDescription());
        Assertions.assertEquals(film.getReleaseDate(), actualFilm.getReleaseDate());
        Assertions.assertEquals(film.getDuration(), actualFilm.getDuration());
    }

    @Test
    void create_filmNameShouldNotBeNull() {
        film.setName(null);

        Assertions.assertThrows(ValidationException.class, () -> filmStorage.create(film));
    }

    @Test
    void create_filmNameShouldNotBeBlank() {
        film.setName("");

        Assertions.assertThrows(ValidationException.class, () -> filmStorage.create(film));
    }

    @Test
    void create_filmDescriptionShouldNotBeLonger_200_symbols() {
        film.setDescription("a".repeat(201));

        Assertions.assertThrows(ValidationException.class, () -> filmStorage.create(film));
    }

    @Test
    void create_shouldAddFilmWithDescriptionLessThan_200_symbols() {
        film.setDescription("a".repeat(200));

        Film actualFilm = filmStorage.create(film);

        Assertions.assertEquals(film, actualFilm);
        Assertions.assertEquals(film.getName(), actualFilm.getName());
        Assertions.assertEquals(film.getDescription(), actualFilm.getDescription());
        Assertions.assertEquals(film.getReleaseDate(), actualFilm.getReleaseDate());
        Assertions.assertEquals(film.getDuration(), actualFilm.getDuration());
    }

    @Test
    void create_filmReleaseDateShouldNotBeBefore_28_12_1895() {
        film.setReleaseDate(LocalDate.of(1895, 12, 27));

        Assertions.assertThrows(ValidationException.class, () -> filmStorage.create(film));
    }

    @Test
    void create_filmDurationShouldNotBeNegative() {
        film.setDuration(Duration.ofMinutes(-1));

        Assertions.assertThrows(ValidationException.class, () -> filmStorage.create(film));
    }
}
