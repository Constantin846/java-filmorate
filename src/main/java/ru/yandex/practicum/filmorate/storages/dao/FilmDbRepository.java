package ru.yandex.practicum.filmorate.storages.dao;

import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Primary;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;
import ru.yandex.practicum.filmorate.exceptions.NotFoundException;
import ru.yandex.practicum.filmorate.model.film.AgeRating;
import ru.yandex.practicum.filmorate.model.film.Film;
import ru.yandex.practicum.filmorate.model.film.FilmGenre;
import ru.yandex.practicum.filmorate.storages.FilmStorage;
import ru.yandex.practicum.filmorate.storages.dao.mappers.FilmRowMapper;

import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

@Slf4j
@Repository("filmDbRepository")
@Primary
public class FilmDbRepository extends BaseDbRepository<Film> implements FilmStorage {
    private static final String FIND_BY_ID_QUERY = "SELECT * FROM films f LEFT JOIN age_ratings ar " +
            "ON f.age_rating_id=ar.id WHERE f.id = ?";
    private static final String FIND_ALL_QUERY = "SELECT * FROM films f LEFT JOIN age_ratings ar " +
            "ON f.age_rating_id=ar.id";
    private static final String INSERT_QUERY = "INSERT INTO films(name, description, release_date, " +
            "duration, age_rating_id) VALUES (?, ?, ?, ?, ?)";
    private static final String UPDATE_QUERY = "UPDATE films SET name = ?, description = ?, release_date = ?, " +
            "duration = ?, age_rating_id = ? WHERE id = ?";
    private static final String DELETE_QUERY = "DELETE FROM films WHERE id = ?";
    private static final String SELECT_AGE_RATING_ID_QUERY = "SELECT id FROM age_ratings WHERE age_rating = ?";
    private static final String SELECT_LIKE_USER_IDS_QUERY = "SELECT user_id FROM liked_by_users WHERE film_id = ?";
    private static final String SELECT_FILM_GENRE_IDS_QUERY = "SELECT genre FROM genres_of_film gf " +
            "JOIN genres g ON gf.genre_id=g.id WHERE film_id = ?";
    private static final String INSERT_LIKE_QUERY = "INSERT INTO liked_by_users(film_id, user_id) " +
            " VALUES (?, ?)";
    private static final String DELETE_LIKE_QUERY = "DELETE FROM liked_by_users " +
            "WHERE film_id = ? AND user_id = ?";
    private static final String INSERT_GENRE_ID_QUERY = "INSERT INTO genres_of_film(film_id, genre_id) " +
            " VALUES (?, ?)";

    public FilmDbRepository(JdbcTemplate jdbc, FilmRowMapper mapper) {
        super(jdbc, mapper);
    }

    @Override
    public Film getFilmById(long filmId) {
        Optional<Film> filmOp = super.findOne(FIND_BY_ID_QUERY, filmId);
        if (filmOp.isPresent()) {
            Film film = filmOp.get();
            film.setFilmGenres(findFilmGenres(film.getId()));
            film.setLikeUserIds(findLikeUserIds(film.getId()));
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
                .map(film -> {
                    film.setFilmGenres(findFilmGenres(film.getId()));
                    film.setLikeUserIds(findLikeUserIds(film.getId()));
                    return film;
                })
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

    private Integer getAgeRatingId(AgeRating ageRating) {
        if (ageRating == null) {
            return null;
        }

        Integer result = jdbc.queryForObject(SELECT_AGE_RATING_ID_QUERY, Integer.class, ageRating.name());

        if (result == null) {
            String message = String.format("The age rating was not found: %s", ageRating.name());
            log.warn(message);
            throw new NotFoundException(message);
        }
        return result;
    }

    private Set<FilmGenre> findFilmGenres(long filmId) {
        List<String> filmGenreIds = jdbc.queryForList(SELECT_FILM_GENRE_IDS_QUERY, String.class, filmId);
        return filmGenreIds.stream()
                .map(FilmGenre::valueOf)
                .collect(Collectors.toSet());
    }

    private Set<Long> findLikeUserIds(long filmId) {
        List<Long> likeUserIds = jdbc.queryForList(SELECT_LIKE_USER_IDS_QUERY, Long.class, filmId);
        return new HashSet<>(likeUserIds);
    }
}
