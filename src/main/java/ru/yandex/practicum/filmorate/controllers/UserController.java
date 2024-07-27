package ru.yandex.practicum.filmorate.controllers;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import ru.yandex.practicum.filmorate.exceptions.ValidationException;
import ru.yandex.practicum.filmorate.model.user.User;
import ru.yandex.practicum.filmorate.services.user.UserService;
import ru.yandex.practicum.filmorate.validators.user.UserValidator;

import java.util.Collection;

@Slf4j
@RestController
@RequestMapping("/users")
@RequiredArgsConstructor
public class UserController {
    private static final String USER_ID_FRIENDS_FRIEND_ID = "/{user-id}/friends/{friend-id}";
    private final UserService userService;
    private final UserValidator userValidator;

    @GetMapping("/{user-id}")
    public User getById(@PathVariable("user-id") long userId) {
        return userService.getUserById(userId);
    }

    @GetMapping
    public Collection<User> findAll() {
        return userService.findAllUsers().values();
    }

    @PostMapping
    public User create(@Valid @RequestBody User user) {
        if (userValidator.checkUserValidation(user)) {
            if (user.getName() == null || user.getName().isBlank()) {
                log.info("The user's name is empty: {}", user);
                user.setName(user.getLogin());
            }
            return userService.create(user);
        }
        String errorMessage = String.format("Invalid data of the %s", user);
        log.warn(errorMessage);
        throw new ValidationException(errorMessage);
    }

    @PutMapping
    public User update(@Valid @RequestBody User user) {
        if (user.getId() == null) {
            String errorMessage = String.format("The user's id is null: %s", user);
            log.warn(errorMessage);
            throw new ValidationException(errorMessage);

        } else if (!userValidator.checkUserValidation(user)) {
            String errorMessage = String.format("Invalid data of the %s", user);
            log.warn(errorMessage);
            throw new ValidationException(errorMessage);
        }
        return userService.update(user);
    }

    @PutMapping(USER_ID_FRIENDS_FRIEND_ID)
    public User addFriendToUser(@PathVariable("user-id") long userId, @PathVariable("friend-id") long friendId) {
        return userService.addFriendToUser(userId, friendId);
    }

    @DeleteMapping(USER_ID_FRIENDS_FRIEND_ID)
    public User removeFriendFromUser(@PathVariable("user-id") long userId, @PathVariable("friend-id") long friendId) {
        return userService.removeFriendFromUser(userId, friendId);
    }

    @GetMapping("/{user-id}/friends")
    public Collection<User> findUserFriends(@PathVariable("user-id") long userId) {
        return userService.findUserFriends(userId);
    }

    @GetMapping("/{user-id}/friends/common/{other-id}")
    public Collection<User> findCommonFriend(@PathVariable("user-id") long userId,
                                             @PathVariable("other-id") long otherId) {
        return userService.findCommonFriend(userId, otherId);
    }
}
