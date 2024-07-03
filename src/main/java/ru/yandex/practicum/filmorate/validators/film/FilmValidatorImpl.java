package ru.yandex.practicum.filmorate.validators.film;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import ru.yandex.practicum.filmorate.exceptions.ValidationException;
import ru.yandex.practicum.filmorate.model.film.Film;

import java.time.LocalDate;

@Slf4j
@Component
public class FilmValidatorImpl implements FilmValidator {
    private static final int MAX_LENGTH_OF_DESCRIPTION = 200;
    private static final LocalDate MOVIE_BIRTHDAY = LocalDate.of(1895,12,28);

    @Override
    public boolean checkFilmValidation(Film film) {
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

        if (film.getDuration() != null && film.getDuration().isNegative()) {
            String errorMessage = String.format("The film's duration must be positive: %s", film);
            log.warn(errorMessage);
            throw new ValidationException(errorMessage);
        }

        return true;
    }
}
