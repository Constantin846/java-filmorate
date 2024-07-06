package ru.yandex.practicum.filmorate.storages.memory;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import ru.yandex.practicum.filmorate.exceptions.NotFoundException;
import ru.yandex.practicum.filmorate.model.user.User;
import ru.yandex.practicum.filmorate.storages.UserStorage;

import java.util.HashMap;
import java.util.Map;

@Slf4j
@Component("inMemoryUserStorage")
@RequiredArgsConstructor
//@NoArgsConstructor(force = true)
public class InMemoryUserStorage implements UserStorage {
    private final Map<Long, User> users = new HashMap<>();

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
    public User create(User user) {
        user.setId(generateId());
        users.put(user.getId(), user);
        return user;
    }

    @Override
    public User update(User user) {
        if (users.containsKey(user.getId())) {

            User oldUser = users.get(user.getId());
            oldUser.setEmail(user.getEmail());
            oldUser.setLogin(user.getLogin());
            oldUser.setName(user.getName());

            if (user.getBirthday() != null) {
                oldUser.setBirthday(user.getBirthday());
            }
            return oldUser;

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

    @Override
    public void addFriendToUser(User user, User friend) {
        user.getFriends().add(friend.getId());
    }

    @Override
    public void removeFriendFromUser(User user, User friend) {
        user.getFriends().remove(friend.getId());
    }

    private long generateId() {
        long currentMaxId = users.keySet().stream()
                .mapToLong(id -> id).max().orElse(0L);
        return  ++currentMaxId;
    }
}
