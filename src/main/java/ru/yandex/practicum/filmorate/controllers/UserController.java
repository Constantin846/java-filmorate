package ru.yandex.practicum.filmorate.controllers;

import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.web.bind.annotation.*;
import ru.yandex.practicum.filmorate.model.User;
import ru.yandex.practicum.filmorate.services.user.UserService;
import ru.yandex.practicum.filmorate.storages.user.UserStorage;

import java.util.Collection;

@RestController
@RequestMapping("/users")
@RequiredArgsConstructor
public class UserController {
    @Qualifier("InMemoryUserStorage")
    private final UserStorage userStorage;
    private final UserService userService;

    @GetMapping("/{userId}")
    public User getUserById(@PathVariable long userId) {
        return userStorage.getUserById(userId);
    }

    @GetMapping
    public Collection<User> findAllUsers() {
        return userStorage.findAllUsers().values();
    }

    @PostMapping
    public User create(@RequestBody User user) {
       return userStorage.create(user);
    }

    @PutMapping
    public User update(@RequestBody User user) {
       return userStorage.update(user);
    }

    @PutMapping("/{userId}/friends/{friendId}")
    public User addFriendToUser(@PathVariable long userId, @PathVariable long friendId) {
        return userService.addFriendToUser(userId, friendId);
    }

    @DeleteMapping("/{userId}/friends/{friendId}")
    public User removeFriendFromUser(@PathVariable long userId, @PathVariable long friendId) {
        return userService.removeFriendFromUser(userId, friendId);
    }

    @GetMapping("/{userId}/friends")
    public Collection<User> findUserFriends(@PathVariable long userId) {
        return userService.findUserFriends(userId);
    }

    @GetMapping("/{userId}/friends/common/{otherId}")
    public Collection<User> findCommonFriend(@PathVariable long userId, @PathVariable long otherId) {
        return userService.findCommonFriend(userId, otherId);
    }
}
