package ru.yandex.practicum.filmorate.storages.dao;

import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Primary;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;
import ru.yandex.practicum.filmorate.exceptions.NotFoundException;
import ru.yandex.practicum.filmorate.model.user.User;
import ru.yandex.practicum.filmorate.storages.UserStorage;
import ru.yandex.practicum.filmorate.storages.dao.extractors.UserExtractor;
import ru.yandex.practicum.filmorate.storages.dao.mappers.UserRowMapper;

import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

@Slf4j
@Repository("userDbRepository")
@Primary
public class UserDbRepository extends BaseDbExtractorRepository<User> implements UserStorage {
    private static final String FIND_BY_ID_QUERY = """
            SELECT *
            FROM users u
            LEFT JOIN friends f ON u.id = f.user_id
            WHERE u.id = ?
            """;
    private static final String FIND_ALL_QUERY = """
            SELECT *
            FROM users u
            LEFT JOIN friends f ON u.id = f.user_id
            """;
    private static final String INSERT_QUERY = """
            INSERT INTO users(email, login, name, birthday)
            VALUES (?, ?, ?, ?)
            """;
    private static final String UPDATE_QUERY = """
            UPDATE users SET email = ?, login = ?, name = ?, birthday = ?
            WHERE id = ?
            """;
    private static final String DELETE_QUERY = "DELETE FROM users WHERE id = ?";
    //private static final String FIND_FRIEND_IDS_QUERY = "SELECT friend_id FROM friends WHERE user_id = ?";
    private static final String INSERT_FRIEND_QUERY = """
            INSERT INTO friends(user_id, friend_id, friend_status_id)
            VALUES (?, ?, ?)
            """;
    private static final String DELETE_FRIEND_QUERY = "DELETE FROM friends WHERE user_id = ? AND friend_id = ?";

    public UserDbRepository(JdbcTemplate jdbc, UserRowMapper mapper, UserExtractor extractor) {
        super(jdbc, mapper, extractor);
    }

    @Override
    public boolean checkUserExists(long userId) {
        return super.findOne(FIND_BY_ID_QUERY, userId).isPresent();
    }

    @Override
    public User getUserById(long userId) {
        Optional<User> userOp = super.findOneWithExtractor(FIND_BY_ID_QUERY, userId);
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
        List<User> users = super.findAllWithExtractor(FIND_ALL_QUERY);
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

    @Override
    public void addFriendToUser(User user, User friend) {
        super.insert(
                INSERT_FRIEND_QUERY,
                user.getId(),
                friend.getId(),
                null
        );
    }

    @Override
    public void removeFriendFromUser(User user, User friend) {
        super.delete(
                DELETE_FRIEND_QUERY,
                user.getId(),
                friend.getId()
        );
    }
}
