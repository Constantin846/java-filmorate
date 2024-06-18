package ru.yandex.practicum.filmorate.model.user;

import lombok.AccessLevel;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.FieldDefaults;

import java.time.LocalDate;
import java.util.Set;

/**
 * User.
 */

@Data
@EqualsAndHashCode(of = "id")
@FieldDefaults(level = AccessLevel.PRIVATE)
public class User {
    //todo validation
    Long id;
    String email;
    String login;
    String name;
    LocalDate birthday;
    Set<Long> friends;
}
