package ru.yandex.practicum.filmorate.storages.dao;

import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Primary;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;
import ru.yandex.practicum.filmorate.exceptions.NotFoundException;
import ru.yandex.practicum.filmorate.model.user.User;
import ru.yandex.practicum.filmorate.storages.UserStorage;
import ru.yandex.practicum.filmorate.storages.dao.mappers.UserRowMapper;

import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

@Slf4j
@Repository("userDbRepository")
@Primary
public class UserDbRepository extends BaseDbRepository<User> implements UserStorage {
    private static final String FIND_BY_ID_QUERY = "SELECT * FROM users WHERE id = ?";
    private static final String FIND_ALL_QUERY = "SELECT * FROM users";
    private static final String INSERT_QUERY = "INSERT INTO users(email, login, name, birthday) " +
            "VALUES (?, ?, ?, ?) returning id";
    private static final String UPDATE_QUERY = "UPDATE users SET email = ?, login = ?, name = ?, birthday = ? " +
            "WHERE id = ?";
    private static final String DELETE_QUERY = "DELETE FROM users WHERE id = ?";
    private static final String FIND_FRIEND_IDS_QUERY = "SELECT user_id FROM users WHERE id = ?";

    public UserDbRepository(JdbcTemplate jdbc, UserRowMapper mapper) {
        super(jdbc, mapper);
    }

    @Override
    public boolean checkUserExists(long userId) {
        return super.findOne(FIND_BY_ID_QUERY, userId).isPresent();
    }

    @Override
    public User getUserById(long userId) {
        Optional<User> userOp = super.findOne(FIND_BY_ID_QUERY, userId);
        if (userOp.isPresent()) {
            return userOp.get();
        } else {
            String message = String.format("Failed to search a user by id: %d", userId);
            log.warn(message);
            throw new NotFoundException(message);
        }
    }

    @Override
    public Map<Long, User> findAllUsers() {
        List<User> users = super.findAll(FIND_ALL_QUERY);
        return users.stream()
                .collect(Collectors.toMap(User::getId, user -> user));
    }

    @Override
    public User create(User user) {
        long id = super.insert(
                INSERT_QUERY,
                user.getEmail(),
                user.getLogin(),
                user.getName(),
                user.getBirthday()
        );
        user.setId(id);
        return user;
    }

    @Override
    public User update(User user) {
        super.update(
                UPDATE_QUERY,
                user.getEmail(),
                user.getLogin(),
                user.getName(),
                user.getBirthday(),
                user.getId()
        );
        return user;
    }

    @Override
    public void remove(long userId) {
        super.delete(DELETE_QUERY, userId);
    }

    private Set<Long> getFriendIds() {

        return null;
    }
}
