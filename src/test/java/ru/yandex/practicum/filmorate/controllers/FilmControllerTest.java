package ru.yandex.practicum.filmorate.controllers;


import lombok.RequiredArgsConstructor;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.jdbc.JdbcTest;
import org.springframework.test.context.ContextConfiguration;
import ru.yandex.practicum.filmorate.dto.AgeRatingDto;
import ru.yandex.practicum.filmorate.dto.FilmDto;
import ru.yandex.practicum.filmorate.dto.mappers.AgeRatingDtoMapper;
import ru.yandex.practicum.filmorate.dto.mappers.FilmDtoMapper;
import ru.yandex.practicum.filmorate.dto.mappers.FilmGenreDtoMapper;
import ru.yandex.practicum.filmorate.exceptions.ValidationException;
import ru.yandex.practicum.filmorate.services.film.FilmServiceImpl;
import ru.yandex.practicum.filmorate.storages.dao.AgeRatingDbRepository;
import ru.yandex.practicum.filmorate.storages.dao.FilmDbRepository;
import ru.yandex.practicum.filmorate.storages.dao.FilmGenreDbRepository;
import ru.yandex.practicum.filmorate.storages.dao.UserDbRepository;
import ru.yandex.practicum.filmorate.storages.dao.extractors.FilmExtractor;
import ru.yandex.practicum.filmorate.storages.dao.extractors.UserExtractor;
import ru.yandex.practicum.filmorate.storages.dao.mappers.AgeRatingRowMapper;
import ru.yandex.practicum.filmorate.storages.dao.mappers.FilmGenreRowMapper;
import ru.yandex.practicum.filmorate.storages.dao.mappers.FilmRowMapper;
import ru.yandex.practicum.filmorate.storages.dao.mappers.UserRowMapper;
import ru.yandex.practicum.filmorate.validators.film.FilmValidatorImpl;

import java.time.Duration;
import java.time.LocalDate;

@JdbcTest
@AutoConfigureTestDatabase
@RequiredArgsConstructor(onConstructor_ = @Autowired)
@ContextConfiguration(classes = {FilmDbRepository.class, FilmRowMapper.class, FilmServiceImpl.class,
        FilmController.class, FilmValidatorImpl.class, FilmDto.class,
        UserDbRepository.class, UserRowMapper.class,
        FilmGenreDbRepository.class, FilmGenreDtoMapper.class, FilmGenreRowMapper.class,
        AgeRatingDbRepository.class, AgeRatingDtoMapper.class, AgeRatingRowMapper.class,
        FilmDtoMapper.class, FilmExtractor.class, UserExtractor.class})
public class FilmControllerTest {
    private final FilmController filmController;
    private final FilmDto film;

    @BeforeEach
    void beforeEach() {
        film.setName("name");
        film.setDescription("description");
        film.setReleaseDate(LocalDate.of(1895,12,28));
        film.setDuration(Duration.ofMinutes(1));
        AgeRatingDto mpa = new AgeRatingDto();
        mpa.setId(2);
        film.setMpa(mpa);
    }

    @Test
    void create_shouldAddValidFilm() {
        FilmDto actualFilm = filmController.create(film);

        Assertions.assertEquals(film.getName(), actualFilm.getName());
        Assertions.assertEquals(film.getDescription(), actualFilm.getDescription());
        Assertions.assertEquals(film.getReleaseDate(), actualFilm.getReleaseDate());
        Assertions.assertEquals(film.getDuration(), actualFilm.getDuration());
        Assertions.assertEquals(film.getMpa().getId(), actualFilm.getMpa().getId());
    }

    @Test
    void create_filmNameShouldNotBeNull() {
        film.setName(null);

        Assertions.assertThrows(ValidationException.class, () -> filmController.create(film));
    }

    @Test
    void create_filmNameShouldNotBeBlank() {
        film.setName("");

        Assertions.assertThrows(ValidationException.class, () -> filmController.create(film));
    }

    @Test
    void create_filmDescriptionShouldNotBeLonger_200_symbols() {
        film.setDescription("a".repeat(201));

        Assertions.assertThrows(ValidationException.class, () -> filmController.create(film));
    }

    @Test
    void create_shouldAddFilmWithDescriptionLessThan_200_symbols() {
        film.setDescription("a".repeat(200));

        FilmDto actualFilm = filmController.create(film);

        Assertions.assertEquals(film.getName(), actualFilm.getName());
        Assertions.assertEquals(film.getDescription(), actualFilm.getDescription());
        Assertions.assertEquals(film.getReleaseDate(), actualFilm.getReleaseDate());
        Assertions.assertEquals(film.getDuration(), actualFilm.getDuration());
        Assertions.assertEquals(film.getMpa().getId(), actualFilm.getMpa().getId());
    }

    @Test
    void create_filmReleaseDateShouldNotBeBefore_28_12_1895() {
        film.setReleaseDate(LocalDate.of(1895, 12, 27));

        Assertions.assertThrows(ValidationException.class, () -> filmController.create(film));
    }

    @Test
    void create_filmDurationShouldNotBeNegative() {
        film.setDuration(Duration.ofMinutes(-1));

        Assertions.assertThrows(ValidationException.class, () -> filmController.create(film));
    }
}

