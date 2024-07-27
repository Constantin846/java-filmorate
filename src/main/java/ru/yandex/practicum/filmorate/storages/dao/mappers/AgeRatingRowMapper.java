package ru.yandex.practicum.filmorate.storages.dao.mappers;

import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Component;
import ru.yandex.practicum.filmorate.model.film.AgeRating;

import java.sql.ResultSet;
import java.sql.SQLException;

@Component
public class AgeRatingRowMapper implements RowMapper<AgeRating> {
    @Override
    public AgeRating mapRow(ResultSet rs, int rowNum) throws SQLException {
        AgeRating ageRating = new AgeRating();
        ageRating.setId(rs.getInt("id"));
        ageRating.setName(rs.getString("age_rating"));
        return ageRating;
    }
}
