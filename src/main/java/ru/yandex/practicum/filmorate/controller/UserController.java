package ru.yandex.practicum.filmorate.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.*;
import ru.yandex.practicum.filmorate.exceptions.NotFoundException;
import ru.yandex.practicum.filmorate.exceptions.ValidationException;
import ru.yandex.practicum.filmorate.model.User;

import java.time.LocalDate;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/users")
public class UserController {
    private static final Logger log = LoggerFactory.getLogger(UserController.class);
    private final Map<Long, User> users = new HashMap<>();

    @GetMapping
    public Collection<User> getUsers() {
        return users.values();
    }

    @PostMapping
    public User create(@RequestBody User user) {
        if (checkUserValidation(user)) {
            user.setId(generateId());
            users.put(user.getId(), user);
            return user;
        }
        log.warn("Invalid data of the {}", user);
        throw new ValidationException("Invalid data of the user");
    }

    @PutMapping
    public User update(@RequestBody User user) {
        if (user.getId() == null) {
            log.warn("The user's id is null: {}", user);
            throw new ValidationException("Id must be specified");
        }

        if (users.containsKey(user.getId())) {
            if (checkUserValidation(user)) {
                User oldUser = users.get(user.getId());

                oldUser.setEmail(user.getEmail());
                oldUser.setLogin(user.getLogin());
                oldUser.setName(user.getName());

                if (user.getBirthday() != null) {
                    oldUser.setBirthday(user.getBirthday());
                }
                return oldUser;

            } else {
                log.warn("Invalid data of the {}", user);
                throw new ValidationException("Invalid data of the user");
            }
        } else {
            log.warn("The user has not been found with id = {}", user.getId());
            throw new NotFoundException("The user has not been found with id = " + user.getId());
        }
    }

    private static final String EMAIL_REGEX = "^.+@.+$";
    private static final String SPACE = " ";

    private boolean checkUserValidation(User user) {
        if (user.getEmail() == null || user.getEmail().isBlank()) {
            log.warn("The user's email must not be empty: {}", user);
            throw new ValidationException("The user's email must not be empty");
        } else if (!user.getEmail().matches(EMAIL_REGEX) || user.getEmail().contains(SPACE)) {
            log.warn("Invalid email structure: {}", user);
            throw new ValidationException("Invalid email structure");
        }

        if (user.getLogin() == null || user.getLogin().isBlank()) {
            log.warn("The user's login must not be empty: {}", user);
            throw new ValidationException("The user's login must not be empty");
        } else if (user.getLogin().contains(SPACE)) {
            log.warn("The user's login must not contain whitespace: {}", user);
            throw new ValidationException("The user's login must not contain whitespace");
        }

        if (user.getName() == null || user.getName().isBlank()) {
            log.info("The user's name is empty: {}", user);
            user.setName(user.getLogin());
        }

        if (user.getBirthday() != null && user.getBirthday().isAfter(LocalDate.now())) {
            log.warn("The user's birthday must not be in the future: {}", user);
            throw new ValidationException("The user's birthday must not be in the future");
        }

        return true;
    }

    private long generateId() {
        return users.keySet().stream()
                .mapToLong(id -> id).max().orElse(1L);
    }
}
