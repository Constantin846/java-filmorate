package ru.yandex.practicum.filmorate.services.user;

import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import ru.yandex.practicum.filmorate.model.User;
import ru.yandex.practicum.filmorate.storages.user.UserStorage;

import java.util.Set;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {
    private static final Logger log = LoggerFactory.getLogger(UserServiceImpl.class);
    private final UserStorage userStorage;

    @Override
    public User addFriendToUser(long userId, long friendId) {
        /*if (userId == friendId) {
            String message = "An user must not be added as a friend to itself";
            log.warn(message);
            throw new ConditionsNotMetException(message);
        }*/

        User user = userStorage.getUserById(userId);
        userStorage.getUserById(friendId).getFriends().add(userId);
        user.getFriends().add(friendId);
        return user;
    }

    @Override
    public User removeFriendFromUser(long userId, long friendId) {
        User user = userStorage.getUserById(userId);
        userStorage.getUserById(friendId).getFriends().remove(userId);
        user.getFriends().remove(friendId);
        return user;
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
