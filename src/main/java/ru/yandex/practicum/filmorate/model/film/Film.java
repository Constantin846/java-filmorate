package ru.yandex.practicum.filmorate.model.film;

import lombok.AccessLevel;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.FieldDefaults;

import java.time.LocalDate;
import java.util.Set;

/**
 * Film.
 */

@Data
@EqualsAndHashCode(of = "id")
@FieldDefaults(level = AccessLevel.PRIVATE)
public class Film {
    //todo validation
    Long id;
    String name;
    String description;
    LocalDate releaseDate;
    int duration;
    AgeRating ageRating;
    Set<FilmGenre> filmGenres;
    Set<Long> likeUserIds;
}
