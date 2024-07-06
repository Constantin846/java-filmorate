package ru.yandex.practicum.filmorate.storages.dao;

import lombok.extern.slf4j.Slf4j;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;
import ru.yandex.practicum.filmorate.dto.FilmGenreDto;
import ru.yandex.practicum.filmorate.exceptions.NotFoundException;
import ru.yandex.practicum.filmorate.storages.FilmGenreStorage;
import ru.yandex.practicum.filmorate.storages.dao.mappers.FilmGenreDtoRowMapper;

import java.util.List;
import java.util.Optional;

@Slf4j
@Repository
public class FilmGenreDbRepository extends BaseDbRepository<FilmGenreDto> implements FilmGenreStorage {
    private static final String FIND_BY_ID_QUERY = "SELECT * FROM genres WHERE id = ?";
    private static final String FIND_ALL_QUERY = "SELECT * FROM genres";

    public FilmGenreDbRepository(JdbcTemplate jdbc, FilmGenreDtoRowMapper mapper) {
        super(jdbc, mapper);
    }

    @Override
    public FilmGenreDto getFilmGenreById(int genreId) {
        Optional<FilmGenreDto> filmGenreOp = super.findOne(FIND_BY_ID_QUERY, genreId);
        if (filmGenreOp.isPresent()) {
            return filmGenreOp.get();
        } else {
            String message = String.format("Failed to search a film genre by id: %d", genreId);
            log.warn(message);
            throw new NotFoundException(message);
        }
    }

    @Override
    public List<FilmGenreDto> findAllFilmGenres() {
        return super.findAll(FIND_ALL_QUERY);
    }
}
