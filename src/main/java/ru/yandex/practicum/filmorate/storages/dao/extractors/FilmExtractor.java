package ru.yandex.practicum.filmorate.storages.dao.extractors;

import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.ResultSetExtractor;
import org.springframework.stereotype.Component;
import ru.yandex.practicum.filmorate.model.film.AgeRating;
import ru.yandex.practicum.filmorate.model.film.Film;
import ru.yandex.practicum.filmorate.model.film.FilmGenre;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.Duration;
import java.util.ArrayList;
import java.util.GregorianCalendar;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;

@Component
public class FilmExtractor implements ResultSetExtractor<List<Film>> {
    @Override
    public List<Film> extractData(ResultSet rs) throws SQLException, DataAccessException {
        Map<Long, Film> films = new HashMap<>();

        while (rs.next()) {
            long id = rs.getLong("id");

            if (films.containsKey(id)) {
                Film film = films.get(id);
                addLike(rs, film);
                addGenre(rs, film);
            } else {
                Film film = new Film();
                film.setId(id);
                film.setName(rs.getString("name"));
                film.setDescription(rs.getString("description"));
                film.setReleaseDate(rs.getDate("release_date", new GregorianCalendar()).toLocalDate());
                film.setDuration(Duration.ofMinutes(rs.getLong("duration")));

                AgeRating ageRating = new AgeRating();
                ageRating.setId(rs.getInt("age_rating_id"));
                ageRating.setName(rs.getString("age_rating"));
                film.setAgeRating(ageRating);

                film.setLikeUserIds(new HashSet<>());
                film.setFilmGenres(new HashSet<>());

                addLike(rs, film);
                addGenre(rs, film);
                films.put(id, film);
            }
        }
        return new ArrayList<>(films.values());
    }

    private void addLike(ResultSet rs, Film film) throws SQLException {
        long userId = rs.getLong("user_id");
        if (userId != 0) {
            film.getLikeUserIds().add(userId);
        }
    }

    private void addGenre(ResultSet rs, Film film) throws SQLException {
        int genreId = rs.getInt("genre_id");
        if (genreId != 0) {
            FilmGenre filmGenre = new FilmGenre();
            filmGenre.setId(genreId);
            filmGenre.setName(rs.getString("genre"));
            film.getFilmGenres().add(filmGenre);
        }
    }
}
