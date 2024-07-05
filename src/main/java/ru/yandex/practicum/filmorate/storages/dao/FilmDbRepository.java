package ru.yandex.practicum.filmorate.storages.dao;

import lombok.extern.slf4j.Slf4j;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;
import ru.yandex.practicum.filmorate.exceptions.NotFoundException;
import ru.yandex.practicum.filmorate.model.film.AgeRating;
import ru.yandex.practicum.filmorate.model.film.Film;
import ru.yandex.practicum.filmorate.storages.FilmStorage;
import ru.yandex.practicum.filmorate.storages.dao.mappers.FilmRowMapper;

import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

@Slf4j
@Repository("filmDbRepository")
public class FilmDbRepository extends BaseDbRepository<Film> implements FilmStorage {
    private static final String FIND_BY_ID_QUERY = "SELECT * FROM films f JOIN age_ratings ar " +
            "ON f.age_rating_id=ar.id WHERE f.id = ?";
    private static final String FIND_ALL_QUERY = "SELECT * FROM films f JOIN age_ratings ar " +
            "ON f.age_rating_id=ar.id";
    private static final String INSERT_QUERY = "INSERT INTO films(name, description, release_date, " +
            "duration, age_rating_id) VALUES (?, ?, ?, ?, ?) returning id";
    private static final String UPDATE_QUERY = "UPDATE films SET name = ?, description = ?, release_date = ?, " +
            "duration = ?, age_rating_id = ? WHERE id = ?";
    private static final String DELETE_QUERY = "DELETE FROM films WHERE id = ?";
    private static final String SELECT_AGE_RATING_ID_QUERY = "SELECT id FROM age_ratings WHERE age_rating = ?";

    public FilmDbRepository(JdbcTemplate jdbc, FilmRowMapper mapper) {
        super(jdbc, mapper);
    }

    @Override
    public Film getFilmById(long filmId) {
        Optional<Film> filmOp = super.findOne(FIND_BY_ID_QUERY, filmId);
        if (filmOp.isPresent()) {
            return filmOp.get();
        } else {
            String message = String.format("Failed to search a film by id: %d", filmId);
            log.warn(message);
            throw new NotFoundException(message);
        }
    }

    @Override
    public Map<Long, Film> findAllFilms() {
        List<Film> films = super.findAll(FIND_ALL_QUERY);
        return films.stream()
                .collect(Collectors.toMap(Film::getId, film -> film));
    }

    @Override
    public Film create(Film film) {
        long id = super.insert(
                INSERT_QUERY,
                film.getName(),
                film.getDescription(),
                film.getReleaseDate(),
                film.getDuration().toMinutes(),
                getAgeRatingId(film.getAgeRating())
        );
        film.setId(id);
        return film;
    }

    @Override
    public Film update(Film film) {
        super.update(
                UPDATE_QUERY,
                film.getName(),
                film.getDescription(),
                film.getReleaseDate(),
                film.getDuration().toMinutes(),
                getAgeRatingId(film.getAgeRating()),
                film.getId()
        );
        return film;
    }

    @Override
    public void remove(long filmId) {
        super.delete(DELETE_QUERY, filmId);
    }

    private int getAgeRatingId(AgeRating ageRating) {
        Integer result = jdbc.queryForObject(SELECT_AGE_RATING_ID_QUERY, Integer.class, ageRating.name());

        if (result == null) {
            String message = String.format("The age rating was not found: %s", ageRating.name());
            log.warn(message);
            throw new NotFoundException(message);
        }
        return result;
    }
}
