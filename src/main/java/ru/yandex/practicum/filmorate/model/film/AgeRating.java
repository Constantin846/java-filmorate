package ru.yandex.practicum.filmorate.model.film;

import lombok.AccessLevel;
import lombok.Data;
import lombok.experimental.FieldDefaults;

/**
 * AgeRating corresponds to Motion Picture Association (МРА)
 * and has the following meanings:
 * G - the film has no age restrictions,
 * PG - children are recommended to watch the film with their parents,
 * PG-13 - viewing is not recommended for children under 13 years of age,
 * R - persons under 17 years of age can watch the film only in the presence of an adult,
 * NC-17 - viewing is prohibited for persons under 18 years of age.
 */

@Data
@FieldDefaults(level = AccessLevel.PRIVATE)
public class AgeRating {
    Integer id;
    String name;
}
