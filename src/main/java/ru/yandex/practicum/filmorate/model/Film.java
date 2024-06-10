package ru.yandex.practicum.filmorate.model;

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
    //todo valid
    Long id;
    String name;
    String description;
    LocalDate releaseDate;
    int duration;
    Set<Long> likeUserIds;
}
