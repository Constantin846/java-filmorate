package ru.yandex.practicum.filmorate.services.user;

import ru.yandex.practicum.filmorate.model.user.User;

import java.util.Map;
import java.util.Set;

public interface UserService {
    User getUserById(long userId);

    Map<Long, User> findAllUsers();

    User create(User user);

    User update(User user);

    User addFriendToUser(long userId, long friendId);

    User removeFriendFromUser(long userId, long friendId);

    Set<User> findUserFriends(long userId);

    Set<User> findCommonFriend(long firstUserId, long secondUserId);
}
