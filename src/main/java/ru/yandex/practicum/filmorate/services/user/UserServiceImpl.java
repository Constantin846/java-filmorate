package ru.yandex.practicum.filmorate.services.user;

import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;
import ru.yandex.practicum.filmorate.exceptions.ConditionsNotMetException;
import ru.yandex.practicum.filmorate.model.user.User;
import ru.yandex.practicum.filmorate.storages.UserStorage;

import java.util.HashSet;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {
    private static final Logger log = LoggerFactory.getLogger(UserServiceImpl.class);
    @Qualifier("userDbRepository")
    private final UserStorage userStorage;

    @Override
    public User getUserById(long userId) {
        return userStorage.getUserById(userId);
    }

    @Override
    public Map<Long, User> findAllUsers() {
        return userStorage.findAllUsers();
    }

    @Override
    public User create(User user) {
        user.setFriends(new HashSet<>());
        return userStorage.create(user);
    }

    @Override
    public User update(User user) {
        return userStorage.update(user);
    }

    @Override
    public User addFriendToUser(long userId, long friendId) {
        if (userId == friendId) {
            String message = "An user must not be added as a friend to itself";
            log.warn(message);
            throw new ConditionsNotMetException(message);
        }

        User user = userStorage.getUserById(userId);
        User friend = userStorage.getUserById(friendId);
        userStorage.addFriendToUser(user, friend);
        return userStorage.getUserById(userId);
    }

    @Override
    public User removeFriendFromUser(long userId, long friendId) {
        User user = userStorage.getUserById(userId);
        User friend = userStorage.getUserById(friendId);
        userStorage.removeFriendFromUser(user, friend);
        return userStorage.getUserById(userId);
    }

    @Override
    public Set<User> findUserFriends(long userId) {
        Set<Long> friendIds = userStorage.getUserById(userId).getFriends();
        return friendIds.stream()
                .map(userStorage::getUserById)
                .collect(Collectors.toSet());
    }

    @Override
    public Set<User> findCommonFriend(long firstUserId, long secondUserId) {
        Set<Long> firstFriendIds = userStorage.getUserById(firstUserId).getFriends();
        Set<Long> secondFriendIds = userStorage.getUserById(secondUserId).getFriends();

        return firstFriendIds.stream()
                .filter(secondFriendIds::contains)
                .map(userStorage::getUserById)
                .collect(Collectors.toSet());
    }
}
