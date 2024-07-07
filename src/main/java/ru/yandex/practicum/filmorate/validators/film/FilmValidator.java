package ru.yandex.practicum.filmorate.validators.film;

import ru.yandex.practicum.filmorate.dto.FilmDto;

public interface FilmValidator {
    boolean checkFilmValidation(FilmDto filmDto);
}
