package ru.yandex.practicum.filmorate.storages.user;

import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.RequestBody;
import ru.yandex.practicum.filmorate.controllers.UserController;
import ru.yandex.practicum.filmorate.exceptions.NotFoundException;
import ru.yandex.practicum.filmorate.exceptions.ValidationException;
import ru.yandex.practicum.filmorate.model.User;

import java.time.LocalDate;
import java.util.HashMap;
import java.util.Map;

@Component
@RequiredArgsConstructor
public class InMemoryUserStorage implements UserStorage {
    private static final Logger log = LoggerFactory.getLogger(UserController.class);
    private final Map<Long, User> users = new HashMap<>();

    @Override
    public boolean checkUserExists(long userId) {
        return users.containsKey(userId);
    }

    @Override
    public User getUserById(long userId) {
        if (users.containsKey(userId)) {
            return users.get(userId);
        }
        String errorMessage = String.format("An user was not found by id: %s", userId);
        log.warn(errorMessage);
        throw new NotFoundException(errorMessage);
    }

    @Override
    public Map<Long, User> findAllUsers() {
        return users;
    }

    @Override
    public User create(@RequestBody User user) {
        if (checkUserValidation(user)) {
            user.setId(generateId());
            users.put(user.getId(), user);
            return user;
        }
        String errorMessage = String.format("Invalid data of the %s", user);
        log.warn(errorMessage);
        throw new ValidationException(errorMessage);
    }

    @Override
    public User update(@RequestBody User user) {
        if (user.getId() == null) {
            String errorMessage = String.format("The user's id is null: %s", user);
            log.warn(errorMessage);
            throw new ValidationException(errorMessage);
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
                String errorMessage = String.format("Invalid data of the %s", user);
                log.warn(errorMessage);
                throw new ValidationException(errorMessage);
            }
        } else {
            String errorMessage = String.format("The user has not been found with id = %d", user.getId());
            log.warn(errorMessage);
            throw new NotFoundException(errorMessage);
        }
    }

    @Override
    public void remove(long id) {
        users.remove(id);
    }

    private static final String EMAIL_REGEX = "^.+@.+$";
    private static final String SPACE = " ";

    private boolean checkUserValidation(User user) {
        if (user.getEmail() == null || user.getEmail().isBlank()) {
            String errorMessage = String.format("The user's email must not be empty: %s", user);
            log.warn(errorMessage);
            throw new ValidationException(errorMessage);
        } else if (!user.getEmail().matches(EMAIL_REGEX) || user.getEmail().contains(SPACE)) {
            String errorMessage = String.format("Invalid email structure: %s", user);
            log.warn(errorMessage);
            throw new ValidationException(errorMessage);
        }

        if (user.getLogin() == null || user.getLogin().isBlank()) {
            String errorMessage = String.format("The user's login must not be empty: %s", user);
            log.warn(errorMessage);
            throw new ValidationException(errorMessage);
        } else if (user.getLogin().contains(SPACE)) {
            String errorMessage = String.format("The user's login must not contain whitespace: %s", user);
            log.warn(errorMessage);
            throw new ValidationException(errorMessage);
        }

        if (user.getName() == null || user.getName().isBlank()) {
            log.info("The user's name is empty: {}", user);
            user.setName(user.getLogin());
        }

        if (user.getBirthday() != null && user.getBirthday().isAfter(LocalDate.now())) {
            String errorMessage = String.format("The user's birthday must not be in the future: %s", user);
            log.warn(errorMessage);
            throw new ValidationException(errorMessage);
        }

        return true;
    }

    private long generateId() {
        long currentMaxId = users.keySet().stream()
                .mapToLong(id -> id).max().orElse(0L);
        return  ++currentMaxId;
    }
}
