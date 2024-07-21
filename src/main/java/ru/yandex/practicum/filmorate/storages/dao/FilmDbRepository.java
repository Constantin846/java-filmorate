package ru.yandex.practicum.filmorate.storages.dao;

import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Primary;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;
import ru.yandex.practicum.filmorate.exceptions.NotFoundException;
import ru.yandex.practicum.filmorate.model.film.Film;
import ru.yandex.practicum.filmorate.storages.FilmStorage;
import ru.yandex.practicum.filmorate.storages.dao.extractors.FilmExtractor;
import ru.yandex.practicum.filmorate.storages.dao.mappers.FilmRowMapper;

import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

@Slf4j
@Repository("filmDbRepository")
@Primary
public class FilmDbRepository extends BaseDbExtractorRepository<Film> implements FilmStorage {
    private static final String FIND_BY_ID_QUERY = """
            SELECT *
            FROM films f
            LEFT JOIN age_ratings ar ON f.age_rating_id = ar.id
            LEFT JOIN liked_by_users l ON f.id = l.film_id
            LEFT JOIN genres_of_film gf ON f.id = gf.film_id
            LEFT JOIN genres g ON g.id = gf.genre_id
            WHERE f.id = ?
            """;
    private static final String FIND_ALL_QUERY = """
            SELECT *
            FROM films f
            LEFT JOIN age_ratings ar ON f.age_rating_id = ar.id
            LEFT JOIN liked_by_users l ON f.id = l.film_id
            LEFT JOIN genres_of_film gf ON f.id = gf.film_id
            LEFT JOIN genres g ON g.id = gf.genre_id
            """;
    private static final String INSERT_QUERY = """
            INSERT INTO films(name, description, release_date, duration, age_rating_id)
            VALUES (?, ?, ?, ?, ?)
            """;
    private static final String UPDATE_QUERY = """
            UPDATE films SET name = ?, description = ?, release_date = ?, duration = ?, age_rating_id = ?
            WHERE id = ?
            """;
    private static final String FIND_POPULAR_QUERY = """
            SELECT *
            FROM films f
            LEFT JOIN age_ratings ar ON f.age_rating_id = ar.id
            LEFT JOIN liked_by_users l ON f.id = l.film_id
            LEFT JOIN genres_of_film gf ON f.id = gf.film_id
            LEFT JOIN genres g ON g.id = gf.genre_id
            LEFT JOIN (SELECT COUNT(user_id) as like_count,
                        film_id
                        FROM liked_by_users
                        GROUP BY film_id) AS lc ON f.id = lc.film_id
            ORDER BY lc.like_count DESC
            """;
    private static final String DELETE_QUERY = "DELETE FROM films WHERE id = ?";
    private static final String INSERT_LIKE_QUERY = "INSERT INTO liked_by_users(film_id, user_id) VALUES (?, ?)";
    private static final String DELETE_LIKE_QUERY = "DELETE FROM liked_by_users WHERE film_id = ? AND user_id = ?";
    private static final String INSERT_GENRE_ID_QUERY = "INSERT INTO genres_of_film(film_id, genre_id) VALUES (?, ?)";

    public FilmDbRepository(JdbcTemplate jdbc, FilmRowMapper mapper, FilmExtractor extractor) {
        super(jdbc, mapper, extractor);
    }

    @Override
    public Film getFilmById(long filmId) {
        Optional<Film> filmOp = super.findOneWithExtractor(FIND_BY_ID_QUERY, filmId);
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
        List<Film> films = super.findAllWithExtractor(FIND_ALL_QUERY);
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
                film.getAgeRating().getId()
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
                film.getAgeRating().getId(),
                film.getId()
        );
        return film;
    }

    @Override
    public void remove(long filmId) {
        super.delete(DELETE_QUERY, filmId);
    }

    @Override
    public void addLike(Film film, long userId) {
        super.insert(
                INSERT_LIKE_QUERY,
                film.getId(),
                userId
        );
    }

    @Override
    public void removeLike(Film film, long userId) {
        super.delete(
                DELETE_LIKE_QUERY,
                film.getId(),
                userId
        );
    }

    @Override
    public void addFilmGenreIds(Film film, Set<Integer> genreIds) {
        for (Integer genreId : genreIds) {
            super.insert(
                    INSERT_GENRE_ID_QUERY,
                    film.getId(),
                    genreId
            );
        }
    }

    @Override
    public List<Film> findPopularFilms(int count) {
        return super.findAllWithExtractor(FIND_POPULAR_QUERY);
    }
}
