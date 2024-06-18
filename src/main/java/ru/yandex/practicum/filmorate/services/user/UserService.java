package ru.yandex.practicum.filmorate.services.user;

import ru.yandex.practicum.filmorate.model.user.User;

import java.util.Set;

public interface UserService {
    User addFriendToUser(long userId, long friendId);

    User removeFriendFromUser(long userId, long friendId);

    Set<User> findUserFriends(long userId);

    Set<User> findCommonFriend(long firstUserId, long secondUserId);
}
