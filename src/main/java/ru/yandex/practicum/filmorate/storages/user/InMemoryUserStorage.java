package ru.yandex.practicum.filmorate.storages.user;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.RequestBody;
import ru.yandex.practicum.filmorate.exceptions.NotFoundException;
import ru.yandex.practicum.filmorate.exceptions.ValidationException;
import ru.yandex.practicum.filmorate.model.user.User;
import ru.yandex.practicum.filmorate.validators.user.UserValidator;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;

@Slf4j
@Component
@RequiredArgsConstructor
//@NoArgsConstructor(force = true)
public class InMemoryUserStorage implements UserStorage {
    private final Map<Long, User> users = new HashMap<>();
    private final UserValidator userValidator;

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
        if (userValidator.checkUserValidation(user)) {
            user.setId(generateId());
            user.setFriends(new HashSet<>());
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
            if (userValidator.checkUserValidation(user)) {
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

    private long generateId() {
        long currentMaxId = users.keySet().stream()
                .mapToLong(id -> id).max().orElse(0L);
        return  ++currentMaxId;
    }
}
