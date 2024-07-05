package ru.yandex.practicum.filmorate.storages.dao.mappers;

import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Component;
import ru.yandex.practicum.filmorate.model.film.AgeRating;
import ru.yandex.practicum.filmorate.model.film.Film;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.Duration;
import java.util.GregorianCalendar;
import java.util.HashSet;

@Component
public class FilmRowMapper implements RowMapper<Film> {
    @Override
    public Film mapRow(ResultSet rs, int rowNum) throws SQLException {
        Film film = new Film();
        film.setId(rs.getLong("id"));
        film.setName(rs.getString("name"));
        film.setDescription(rs.getString("description"));
        film.setReleaseDate(rs.getDate("release_date", new GregorianCalendar()).toLocalDate());
        film.setDuration(Duration.ofMinutes(rs.getLong("duration")));
        film.setAgeRating(AgeRating.valueOf(rs.getString("age_rating")));

        //todo
        film.setLikeUserIds(new HashSet<>());
        film.setFilmGenres(new HashSet<>());

        return film;
    }
}
