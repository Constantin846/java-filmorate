package ru.yandex.practicum.filmorate.validators.film;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import ru.yandex.practicum.filmorate.dto.FilmDto;
import ru.yandex.practicum.filmorate.dto.FilmGenreDto;
import ru.yandex.practicum.filmorate.dto.mappers.FilmDtoMapper;
import ru.yandex.practicum.filmorate.exceptions.ValidationException;

import java.time.LocalDate;
import java.util.Set;

@Slf4j
@Component
@RequiredArgsConstructor
public class FilmValidatorImpl implements FilmValidator {
    private final FilmDtoMapper filmDtoMapper;
    private static final int MAX_LENGTH_OF_DESCRIPTION = 200;
    private static final LocalDate MOVIE_BIRTHDAY = LocalDate.of(1895,12,28);

    @Override
    public boolean checkFilmValidation(FilmDto film) {

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

        if (film.getMpa().getId() < 0 || film.getMpa().getId() > 5) {
            String errorMessage = String.format("The film's mpa id must be between 1 and 5: %s", film);
            log.warn(errorMessage);
            throw new ValidationException(errorMessage);
        }

        if (film.getGenres() != null) {
            Set<FilmGenreDto> genres = film.getGenres();

            for (FilmGenreDto genre : genres) {
                if (genre.getId() < 0 || genre.getId() > 6) {
                    String errorMessage = String.format("The film's genre id must be between 1 and 6: %s", film);
                    log.warn(errorMessage);
                    throw new ValidationException(errorMessage);
                }
            }
        }

        return true;
    }
}
