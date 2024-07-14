package ru.yandex.practicum.filmorate.model.film;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import lombok.AccessLevel;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.FieldDefaults;
import ru.yandex.practicum.filmorate.adapters.DurationDeserializer;
import ru.yandex.practicum.filmorate.adapters.DurationSerializer;

import java.time.Duration;
import java.time.LocalDate;
import java.util.Set;

/**
 * Film.
 */

@Data
@EqualsAndHashCode(of = "id")
@FieldDefaults(level = AccessLevel.PRIVATE)
public class Film {
    Long id;
    String name;
    String description;
    @JsonFormat(pattern = "yyyy-MM-dd")
    LocalDate releaseDate;
    @JsonSerialize(using = DurationSerializer.class)
    @JsonDeserialize(using = DurationDeserializer.class)
    Duration duration;
    AgeRating ageRating;
    Set<FilmGenre> filmGenres;
    Set<Long> likeUserIds;
}
