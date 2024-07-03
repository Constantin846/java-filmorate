package ru.yandex.practicum.filmorate.controllers;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import ru.yandex.practicum.filmorate.model.user.User;
import ru.yandex.practicum.filmorate.services.user.UserService;

import java.util.Collection;

@RestController
@RequestMapping("/users")
@RequiredArgsConstructor
public class UserController {
    private static final String USER_ID_FRIENDS_FRIEND_ID = "/{userId}/friends/{friendId}";
    private final UserService userService;

    @GetMapping("/{userId}")
    public User getUserById(@PathVariable long userId) {
        return userService.getUserById(userId);
    }

    @GetMapping
    public Collection<User> findAllUsers() {
        return userService.findAllUsers().values();
    }

    @PostMapping
    public User create(@Valid @RequestBody User user) {
       return userService.create(user);
    }

    @PutMapping
    public User update(@Valid @RequestBody User user) {
       return userService.update(user);
    }

    @PutMapping(USER_ID_FRIENDS_FRIEND_ID)
    public User addFriendToUser(@PathVariable long userId, @PathVariable long friendId) {
        return userService.addFriendToUser(userId, friendId);
    }

    @DeleteMapping(USER_ID_FRIENDS_FRIEND_ID)
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
