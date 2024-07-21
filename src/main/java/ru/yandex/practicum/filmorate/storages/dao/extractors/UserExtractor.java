package ru.yandex.practicum.filmorate.storages.dao.extractors;

import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.ResultSetExtractor;
import org.springframework.stereotype.Component;
import ru.yandex.practicum.filmorate.model.user.User;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;

@Component
public class UserExtractor implements ResultSetExtractor<List<User>> {
    @Override
    public List<User> extractData(ResultSet rs) throws SQLException, DataAccessException {
        Map<Long, User> users = new HashMap<>();

        while (rs.next()) {
            long id = rs.getLong("id");

            if (users.containsKey(id)) {
                User user = users.get(id);
                addFriend(rs, user);
            } else {
                User user = new User();
                user.setId(id);
                user.setEmail(rs.getString("email"));
                user.setLogin(rs.getString("login"));
                user.setName(rs.getString("name"));
                user.setBirthday(rs.getDate("birthday").toLocalDate());

                user.setFriends(new HashSet<>());
                addFriend(rs, user);

                users.put(id, user);
            }
        }
        return new ArrayList<>(users.values());
    }

    private void addFriend(ResultSet rs, User user) throws SQLException {
        long friendId = rs.getLong("friend_id");
        if (friendId != 0) {
            user.getFriends().add(friendId);
        }
    }
}
