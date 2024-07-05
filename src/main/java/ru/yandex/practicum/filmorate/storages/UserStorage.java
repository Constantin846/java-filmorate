package ru.yandex.practicum.filmorate.storages;

import ru.yandex.practicum.filmorate.model.user.User;

import java.util.Map;

public interface UserStorage {
    boolean checkUserExists(long userId);

    User getUserById(long userId);

    Map<Long, User> findAllUsers();

    User create(User user);

    User update(User user);

    void remove(long userId);
}
