package ru.yandex.practicum.filmorate.storages.dao;

import lombok.extern.slf4j.Slf4j;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;
import ru.yandex.practicum.filmorate.exceptions.NotFoundException;
import ru.yandex.practicum.filmorate.model.film.FilmGenre;
import ru.yandex.practicum.filmorate.storages.FilmGenreStorage;
import ru.yandex.practicum.filmorate.storages.dao.mappers.FilmGenreRowMapper;

import java.util.List;
import java.util.Optional;

@Slf4j
@Repository
public class FilmGenreDbRepository extends BaseDbRepository<FilmGenre> implements FilmGenreStorage {
    private static final String FIND_BY_ID_QUERY = "SELECT * FROM genres WHERE id = ?";
    private static final String FIND_ALL_QUERY = "SELECT * FROM genres";
    private static final String SELECT_FILM_GENRE_ID_QUERY = "SELECT id FROM genres WHERE genre = ?";

    public FilmGenreDbRepository(JdbcTemplate jdbc, FilmGenreRowMapper mapper) {
        super(jdbc, mapper);
    }

    @Override
    public FilmGenre getFilmGenreById(int genreId) {
        Optional<FilmGenre> filmGenreOp = super.findOne(FIND_BY_ID_QUERY, genreId);
        if (filmGenreOp.isPresent()) {
            return filmGenreOp.get();
        } else {
            String message = String.format("Failed to search a film genre by id: %d", genreId);
            log.warn(message);
            throw new NotFoundException(message);
        }
    }

    @Override
    public List<FilmGenre> findAllFilmGenre() {
        return super.findAll(FIND_ALL_QUERY);
    }

    @Override
    public int getFilmGenreIdByName(String name) {
        Integer result = jdbc.queryForObject(SELECT_FILM_GENRE_ID_QUERY, Integer.class, name);

        if (result == null) {
            String message = String.format("The film genre was not found: %s", name);
            log.warn(message);
            throw new NotFoundException(message);
        }
        return result;
    }
}
