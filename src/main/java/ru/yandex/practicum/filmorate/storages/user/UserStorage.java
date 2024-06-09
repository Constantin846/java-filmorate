package ru.yandex.practicum.filmorate.storages.user;

import ru.yandex.practicum.filmorate.model.User;

import java.util.Map;

public interface UserStorage {
    boolean checkUserExists(long userId);

    User getUserById(long id);

    Map<Long, User> findAllUsers();

    User create(User user);

    User update(User user);

    void remove(long id);
}
